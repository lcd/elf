/**
 * Specl.com Inc.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.elf.dao.StructureDao;
import com.elf.entities.Structure;
import com.elf.enumlations.StructureType;

/**
 * 
 * @author laichendong
 */
@Repository("structureDao")
public class StructureDaoImpl extends SqlMapClientDaoSupport implements StructureDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<Structure> getStructuresByType(StructureType type) {
        return getSqlMapClientTemplate().queryForList("structures.getStructureByType", type);
    }

    @Override
    public int addStructure(Structure category) {
        return (Integer) getSqlMapClientTemplate().insert("structures.addStructure", category);
    }

    @Override
    public Structure getStructureById(int id) {
        return (Structure) getSqlMapClientTemplate().queryForObject("structures.getStructureById", id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Structure> getTagsByNames(String newTagNames) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", StructureType.TAG);
        paramMap.put("newTagNames", newTagNames);
        return getSqlMapClientTemplate().queryForList("structures.getTagsByNames", paramMap);
    }

    @Override
    public int getArticleCountInCategory(int id) {
        return (Integer) getSqlMapClientTemplate().queryForObject("structures.getArticleInCategoryCount",id);
    }

    @Override
    public void updateArticleCategoryToDefault(int categoryId) {
        getSqlMapClientTemplate().update("structures.updateArticleCategoryToDefault",categoryId);
    }

    @Override
    public void deleteStructrue(int structrueId) {
        getSqlMapClientTemplate().delete("structures.deleteStructrue",structrueId);
    }

    @Override
    public void updateCategoryToParent(int categoryId, int parentsId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("categoryId", categoryId);
        map.put("parentId", parentsId);
        getSqlMapClientTemplate().update("structures.updateCategoryToParent",map);
    }

    @Override
    public Structure getStructureByTypeAndName(Structure structure) {
        return (Structure) getSqlMapClientTemplate().queryForObject("structures.getStructureByTypeAndName", structure);
    }

    @Override
    public void updateStructure(Structure category) {
        getSqlMapClientTemplate().update("structures.updateStructure",category);
    }

    @Override
    public int getArticlesCountInTag(int tagId) {
        return (Integer) getSqlMapClientTemplate().queryForObject("structures.getArticlesCountInTag", tagId);
    }
    
}
