/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.view.admin;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.elf.biz.ArticleBiz;
import com.elf.biz.StructureBiz;
import com.elf.entities.Article;
import com.elf.entities.Structure;
import com.elf.enumlations.StructureType;
import com.elf.excptions.StructureHasExistException;
import com.elf.utils.Struts2Util;
import com.elf.view.ElfBaseAction;
import com.elf.vo.ArticleListFilter;

/**
 * 
 * @author laichendong
 */
public class ArticleAction extends ElfBaseAction {

    private static final long serialVersionUID = -3420073673812280777L;
    
    private StructureBiz structureBiz;
    private ArticleBiz articleBiz;
    private Article article;
    private String tagIds;//文章对应的标签ID csv
    private List<Map<String, Object>> articleTableData; //文章列表
    private ArticleListFilter filter = new ArticleListFilter();
    
    /**
     * 转到添加文章页面
     * @return
     */
    public String newArticle(){
        return SUCCESS;
    }
    
    /**
     * 执行添加文章操作
     * @return
     */
    public String addArticle(){
        articleBiz.addArticle(article, tagIds, vu.sessionUser(Struts2Util.getSession()));
        return SUCCESS;
    }
    
    /**
     * ajax返回新文章页中文章类别选取部分的类别树html代码
     */
    public void getAllCategoryAjax(){
        List<Structure> allCategories = structureBiz.getAllCategory();
        Struts2Util.renderJson(JSONArray.fromObject(allCategories).toString());
    }
    
    /**
     * ajax添加新类别
     */
    public void addNewCategoryAjax(){
        String categoryName = null;
        int parentId = 0;
        if(!verifyNewCategoryData()){
            ajaxErrorDescription.put(KEY_ERROR, errorDescription);
            Struts2Util.renderJson(JSONObject.fromObject(ajaxErrorDescription).toString());
            return;
        }else{
            categoryName = Struts2Util.getRequest().getParameter("categoryName");
            parentId = Integer.parseInt(Struts2Util.getRequest().getParameter("parentId"));
        }
        
        Structure category = new Structure();
        category.setName(categoryName);
        category.setType(StructureType.CATEGORY);
        category.setParentId(parentId);
        try {
            category = structureBiz.addStructure(category);
        } catch (StructureHasExistException e) {
            Struts2Util.renderJson(createErrorJsonFromException(e));
            return;
        }
        Struts2Util.renderJson(JSONObject.fromObject(category).toString());
    }
    
    /**
     * ajax以逗号分割串为参数批量添加标签
     */
    public void addNewTagsAjax(){
        //取得和验证数据
        String newTagNames = null;
        if(!verifyNewTagsData()){
            ajaxErrorDescription.put(KEY_ERROR, errorDescription);
            Struts2Util.renderJson(JSONObject.fromObject(ajaxErrorDescription).toString());
            return;
        }else{
            newTagNames = Struts2Util.getRequest().getParameter("newTagNames");
        }
        
        //执行添加
        List<Structure> tags = structureBiz.addTagsForString(newTagNames);
        Struts2Util.renderJson(JSONArray.fromObject(tags).toString());
    }
    
    /**
     * ajax获取所有标签
     */
    public void getAllTagsAjax(){
        List<Structure> tags = structureBiz.getAllTags();
        Struts2Util.renderJson(JSONArray.fromObject(tags).toString());
    }

    /**
     * 验证添加新标签数据
     * @return
     */
    private boolean verifyNewTagsData() {
        errorDescription.clear();
        String newTagNames = Struts2Util.getRequest().getParameter("newTagNames");
        if (!newTagNames.matches("^[\\w\\u4e00-\\u9fa5,，]{1,100}$")) {
            errorDescription.add("类型名称应该由1-30个“单词字符”组成");
        }
        return errorDescription.isEmpty();
    }

    /**
     * 验证添加新类别的数据
     * @return
     */
    private boolean verifyNewCategoryData() {
        errorDescription.clear();
        String categoryName = Struts2Util.getRequest().getParameter("categoryName");
        String parentId = Struts2Util.getRequest().getParameter("parentId");
        if (!categoryName.matches("^[\\w\\u4e00-\\u9fa5]{1,30}$")) {
            errorDescription.add("类型名称应该由1-30个“单词字符”组成");
        }
        try{
            Integer.parseInt(parentId);
        }catch(NumberFormatException e){
            errorDescription.add("父类别id不是数字！");
        }
        return errorDescription.isEmpty();
    }
    
    /**
     * 转到文章列表页面
     * @return
     */
    public String articleList(){
        if(filter!=null && !verifyFilter(filter)){
            return SUCCESS;
        }
        articleTableData = articleBiz.getArticleListData(filter);
        return SUCCESS;
    }
    
    /**
     * 验证过滤器数据合法性
     * @param filter2
     * @return
     */
    private boolean verifyFilter(ArticleListFilter filter) {
        errorDescription.clear();
        if(filter.getKeyword().length() > 50){
            errorDescription.add("关键字太长了。");
        }
        return errorDescription.isEmpty();
    }

    public StructureBiz getStructureBiz() {
        return structureBiz;
    }

    public void setStructureBiz(StructureBiz structureBiz) {
        this.structureBiz = structureBiz;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public ArticleBiz getArticleBiz() {
        return articleBiz;
    }

    public void setArticleBiz(ArticleBiz articleBiz) {
        this.articleBiz = articleBiz;
    }

    public ArticleListFilter getFilter() {
        return filter;
    }

    public void setFilter(ArticleListFilter filter) {
        this.filter = filter;
    }

    public List<Map<String, Object>> getArticleTableData() {
        return articleTableData;
    }

    public void setArticleTableData(List<Map<String, Object>> articleTableData) {
        this.articleTableData = articleTableData;
    }


}
