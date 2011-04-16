/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.view.admin;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.elf.biz.StructureBiz;
import com.elf.entities.Structure;
import com.elf.enumlations.StructureType;
import com.elf.excptions.DataTryToDeleteNotExistException;
import com.elf.excptions.StructureHasExistException;
import com.elf.utils.Struts2Util;
import com.elf.view.ElfBaseAction;

/**
 * 
 * @author laichendong
 */
public class StructureAction extends ElfBaseAction {

    private static final long serialVersionUID = -7218331831184547639L;
    StructureBiz structureBiz;
    List<Map<String, Object>> categoryTableData;// 类别管理页面列表数据
    List<Map<String, Object>> tagTableData; // 标签管理页面列表数据
    Structure structure;

    /**
     * 转到类别管理页
     * 
     * @return
     */
    public String categoryManager() {
        categoryTableData = structureBiz.getCategoryTableData();
        return SUCCESS;
    }

    /**
     * 转到标签管理页
     * 
     * @return
     */
    public String tagManager() {
        tagTableData = structureBiz.getTagTableData();
        return SUCCESS;
    }

    /**
     * ajax删除类别
     */
    public void delCategoryAjax() {
        int categoryId = Integer.parseInt(Struts2Util.getRequest().getParameter("categoryId"));
        try {
            structureBiz.delCategory(categoryId);
            Struts2Util.renderJson("{\"status\":\"deleted\"}");
        } catch (DataTryToDeleteNotExistException e) {
            Struts2Util.renderJson(createErrorJsonFromException(e));
        }
    }

    /**
     * 添加类别
     * 
     * @return
     */
    public String addCategory() {
        if (!verifyAddOrEditCategory(false)) {
            // 获取列表数据，以初始化页面
            categoryTableData = structureBiz.getCategoryTableData();
            return INPUT;
        }
        structure.setType(StructureType.CATEGORY);
        try {
            structureBiz.addStructure(structure);
        } catch (StructureHasExistException e) {
            // 获取列表数据，以初始化页面
            categoryTableData = structureBiz.getCategoryTableData();
            // 设置错误提示
            errorDescription.clear();
            errorDescription.add("类别：<em style='color:red;'>" + structure.getName() + "</em>已经存在");
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 验证添加类别数据
     * 
     * @return
     */
    private boolean verifyAddOrEditCategory(boolean isEdit) {
        errorDescription.clear();
        if (structure == null) {
            errorDescription.add("提交的数据格式错误。");
            return false;
        }
        // 如果是编辑且没有提供类别id，提示数据格式错误
        if (isEdit && structure.getId() <= 0) {
            errorDescription.add("提交的数据格式错误。");
            return false;
        }
        if (!structure.getName().matches("^[\\w\\u4e00-\\u9fa5]{1,15}$")) {
            errorDescription.add("类别名称应该由1到15个中文或单词字符组成。");
        }
        if (!structure.getAlias().matches("^\\w{0,20}$")) {
            errorDescription.add("类别别名不能超过20个字符，且只能由英文字母组成。");
        }
        if (structure.getParentId() < 0) {
            errorDescription.add("请选择父级类别。");
        }
        if (!structure.getDescription().matches("^[\\w\\u4e00-\\u9fa5]{0,500}$")) {
            errorDescription.add("类别描述不能超过500个字。");
        }
        return errorDescription.isEmpty();
    }

    /**
     * ajax批量删除类别
     */
    public void batchDelelteCategories() {
        String categoryIds = Struts2Util.getRequest().getParameter("categoryIds");
        if (!verifyBatchDeleteCategories(categoryIds)) {
            ajaxErrorDescription.put(KEY_ERROR, errorDescription);
            Struts2Util.renderJson(JSONObject.fromObject(ajaxErrorDescription).toString());
            return;
        }
        try {
            structureBiz.batchDeleteCategories(categoryIds);
            Struts2Util.renderJson("{}");
        } catch (DataTryToDeleteNotExistException e) {
            Struts2Util.renderJson(createErrorJsonFromException(e));
        }
    }

    /**
     * 验证批量删除参数
     * 
     * @return
     */
    private boolean verifyBatchDeleteCategories(String categoryIds) {
        errorDescription.clear();
        if (categoryIds == null || categoryIds.isEmpty()) {
            errorDescription.add("请选择您要删除的类别。");
        } else {
            if (!categoryIds.matches("[\\d+,?]+")) {
                errorDescription.add("参数格式错误。");
            }
        }
        return errorDescription.isEmpty();
    }

    /**
     * 转到编辑类别页面
     * 
     * @return
     */
    public String editCategory() {
        int categoryId = 0;
        try {
            categoryId = Integer.parseInt(Struts2Util.getRequest().getParameter("c"));
        } catch (NumberFormatException e) {
            errorDescription.add("参数格式错误。");
            return SUCCESS;
        }
        structure = structureBiz.getStructureById(categoryId);
        if (structure == null || !structure.getType().equals(StructureType.CATEGORY)) {
            errorDescription.add("不存在该类别。");
        }
        return SUCCESS;
    }

    /**
     * 执行编辑类别
     * 
     * @return
     */
    public String doEditCategory() {
        if (!verifyAddOrEditCategory(true)) {
            return INPUT;
        }
        structureBiz.editCategory(structure);
        return SUCCESS;
    }
    
    /**
     * 执行添加标签
     * @return
     */
    public String addTag(){
        structure.setType(StructureType.TAG);
        try {
            structureBiz.addStructure(structure);
            return SUCCESS;
        } catch (StructureHasExistException e) {
            e.printStackTrace();
            return INPUT;
        }
        
    }

    public StructureBiz getStructureBiz() {
        return structureBiz;
    }

    public void setStructureBiz(StructureBiz structureBiz) {
        this.structureBiz = structureBiz;
    }

    public List<Map<String, Object>> getCategoryTableData() {
        return categoryTableData;
    }

    public void setCategoryTableData(List<Map<String, Object>> categoryTableData) {
        this.categoryTableData = categoryTableData;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public List<Map<String, Object>> getTagTableData() {
        return tagTableData;
    }

    public void setTagTableData(List<Map<String, Object>> tagTableData) {
        this.tagTableData = tagTableData;
    }

}
