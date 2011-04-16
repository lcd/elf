package com.elf.biz;

import java.util.List;
import java.util.Map;

import com.elf.entities.Structure;
import com.elf.excptions.DataTryToDeleteNotExistException;
import com.elf.excptions.StructureHasExistException;

public interface StructureBiz {
    List<Structure> getAllCategory();

    Structure addStructure(Structure structure) throws StructureHasExistException;

    /**
     * 根据逗号分割的字符串添加标签，有同名的情况下，不再次添加
     * @param newTagNames
     * @return 逗号分割串中代表的标签列表，包括这次添加的和重名情况已有的
     */
    List<Structure> addTagsForString(String newTagNames);

    List<Structure> getAllTags();

    List<Map<String, Object>> getCategoryTableData();

    /**
     * 删除类别，类别删除后原属于这个类别的文章会转到“未分类”下,子类别的父类别ID会指向该类别的父类别id
     * @param categoryId
     * @return
     * @throws DataTryToDeleteNotExistException 
     */
    void delCategory(int categoryId) throws DataTryToDeleteNotExistException;

    void batchDeleteCategories(String categoryIds) throws DataTryToDeleteNotExistException;

    Structure getStructureById(int id);

    void editCategory(Structure structure);

    List<Map<String, Object>> getTagTableData();

}
