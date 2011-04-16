/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.vo;

import java.io.Serializable;

import com.elf.enumlations.ArticlePublishStatus;

/**
 * 文章列表过滤条件值对象
 * @author laichendong
 */
public class ArticleListFilter implements Serializable {
    private static final long serialVersionUID = -1675511655709532084L;
    private String keyword = "";
    private ArticlePublishStatus publishStatus;
    private int categoryId = 0;
    private String beginDate = "";
    private String endDate = "";
    private int pageSize = 15;
    private int pageNo = 0;
    private int startIndex = pageNo * pageSize;
    private int authorId = 0;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ArticlePublishStatus getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(ArticlePublishStatus publishStatus) {
        this.publishStatus = publishStatus;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getStartIndex() {
        this.startIndex = this.pageNo * this.pageSize;
        return this.startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    
}
