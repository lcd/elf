/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.dao;

import java.util.List;

import com.elf.entities.Structure;
import com.elf.enumlations.StructureType;

/**
 * structure表dao
 * @author laichendong
 */
public interface StructureDao {

    List<Structure> getStructuresByType(StructureType structureType);

    int addStructure(Structure structure);

    Structure getStructureById(int id);

    List<Structure> getTagsByNames(String newTagNames);

    /**
     * 统计类别下有多少文章
     * @param id
     * @return
     */
    int getArticleCountInCategory(int id);

    /**
     * 把该类别下的文章都更新到“未分类”
     * @param categoryId
     */
    void updateArticleCategoryToDefault(int categoryId);

    void deleteStructrue(int structrueId);

    /**
     * 更新子类别的父类别Id指向该类别的父类别
     * @param categoryId
     */
    void updateCategoryToParent(int categoryId, int parentsId);

    /**
     * 根据类别和名称获取唯一的一个类别或标签
     * @param structure
     * @return
     */
    Structure getStructureByTypeAndName(Structure structure);

    void updateStructure(Structure category);

    int getArticlesCountInTag(int id);

}
