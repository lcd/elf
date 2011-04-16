package com.elf.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elf.biz.StructureBiz;
import com.elf.dao.StructureDao;
import com.elf.entities.Structure;
import com.elf.enumlations.StructureType;
import com.elf.excptions.DataTryToDeleteNotExistException;
import com.elf.excptions.StructureHasExistException;
import com.elf.utils.ElfTools;

/**
 * 
 * @author laichendong
 */
@Service("structureBiz")
public class StructureBizImpl implements StructureBiz {

    @Resource
    private StructureDao structureDao;

    @Override
    public List<Structure> getAllCategory() {
        return structureDao.getStructuresByType(StructureType.CATEGORY);
    }

    @Override
    public Structure addStructure(Structure structure) throws StructureHasExistException {
        Structure s = structureDao.getStructureByTypeAndName(structure);
        if(s != null && s.getId() > 0){
            switch(structure.getType()){
            case CATEGORY : {
                throw new StructureHasExistException("类别：<em style='color:red;'>"+structure.getName()+"</em>已经存在");
            }
            case TAG :{
                throw new StructureHasExistException("标签：<em style='color:red;'>"+structure.getName()+"</em>已经存在");
            }
            default : {
                throw new StructureHasExistException(structure.getType()+"：<em style='color:red;'>"+structure.getName()+"</em>已经存在");
            }
            }
            
        }
        int id = structureDao.addStructure(structure);
        return structureDao.getStructureById(id);
    }

    @Override
    public List<Structure> addTagsForString(String newTagNames) {
        // 查询已有的tag
        List<Structure> tagList = structureDao.getTagsByNames(ElfTools.formatCSVForSQL(newTagNames));
        String[] nameArray = newTagNames.split(",|，");
        // 数组去重
        nameArray = ElfTools.uniqueArrayItem(nameArray);
        // 删除nameArray在数据库中已有的tagName
        for (Structure tag : tagList) {
            String name = tag.getName();
            for (int i = 0; i < nameArray.length; i++) {
                if ((nameArray[i] != null) && (nameArray[i].equals(name))) {
                    ElfTools.deleteArrayItem(nameArray, i);
                }
            }
        }
        // 插入数据库
        for (String newTagName : nameArray) {
            if (newTagName != null) {
                Structure tag = new Structure();
                if (!newTagName.isEmpty()) {
                    tag.setType(StructureType.TAG);
                    tag.setName(newTagName);
                }
                int tagId = structureDao.addStructure(tag);
                tag = structureDao.getStructureById(tagId);
                tagList.add(tag);
            }
        }
        return tagList;
    }

    @Override
    public List<Structure> getAllTags() {
        return structureDao.getStructuresByType(StructureType.TAG);
    }

    @Override
    public List<Map<String, Object>> getCategoryTableData() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        List<Structure> allCategory = structureDao.getStructuresByType(StructureType.CATEGORY);
        for (Structure category : allCategory) {
            Map<String, Object> map = ElfTools.objectToMap(category);
            // 获取类别下的文章数
            int articleCount = structureDao.getArticleCountInCategory(category.getId());
            map.put("articleCount", articleCount);
            // 获取父级类别
            Structure parent = structureDao.getStructureById(category.getParentId());
            map.put("parent", parent);

            data.add(map);
        }
        return data;
    }

    @Override
    public void delCategory(int categoryId) throws DataTryToDeleteNotExistException {
        // 判断是否存在对应类别
        Structure category = structureDao.getStructureById(categoryId);
        if (category == null) {
            throw new DataTryToDeleteNotExistException("尝试删除的类别（categoryId="+categoryId+"）不存在。");
        }
        // 更新类别下的文章到“未分类”
        structureDao.updateArticleCategoryToDefault(categoryId);
        // 更新子类别的父类别Id指向该类别的父类别
        structureDao.updateCategoryToParent(categoryId, category.getParentId());
        // 删除类别数据
        structureDao.deleteStructrue(categoryId);
    }

    @Override
    public void batchDeleteCategories(String categoryIds) throws DataTryToDeleteNotExistException {
        for(String idStr : ElfTools.splitCSV(categoryIds)){
            int id = Integer.parseInt(idStr);
            delCategory(id);
        }
        
    }

    @Override
    public Structure getStructureById(int id) {
        return structureDao.getStructureById(id);
    }

    @Override
    public void editCategory(Structure category) {
        structureDao.updateStructure(category);
    }

    @Override
    public List<Map<String, Object>> getTagTableData() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        List<Structure> tags = structureDao.getStructuresByType(StructureType.TAG);
        for (Structure tag : tags) {
            Map<String, Object> map = ElfTools.objectToMap(tag);
            // 获取文章数
            int articleCount = structureDao.getArticlesCountInTag(tag.getId());
            map.put("articleCount", articleCount);

            data.add(map);
        }
        return data;
    }

}
