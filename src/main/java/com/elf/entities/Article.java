/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.entities;

import java.io.Serializable;
import java.util.Date;

import com.elf.enumlations.ArticlePublishLevel;
import com.elf.enumlations.ArticlePublishStatus;
import com.elf.enumlations.CommentStatus;

/**
 * 文章实体类
 * 
 * @author laichendong
 */
public class Article implements Serializable {

    private static final long serialVersionUID = 7346812023111167755L;

    private int id;
    private String title;
    private String content;
    private String excerpt;
    private ArticlePublishStatus publishStatus;
    private int authorId;
    private int categoryId;
    private CommentStatus commentStatus;
    private boolean commentNeedAudit;
    private boolean top;
    private ArticlePublishLevel publishLevel;
    private String accessPassword;
    private Date createTime;
    private Date updateTime;
    private Date offLineTime;
    private int pageView;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public ArticlePublishStatus getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(ArticlePublishStatus publishStatus) {
        this.publishStatus = publishStatus;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public ArticlePublishLevel getPublishLevel() {
        return publishLevel;
    }

    public void setPublishLevel(ArticlePublishLevel publishLevel) {
        this.publishLevel = publishLevel;
    }

    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOffLineTime() {
        return offLineTime;
    }

    public void setOffLineTime(Date offLineTime) {
        this.offLineTime = offLineTime;
    }

    public int getPageView() {
        return pageView;
    }

    public void setPageView(int pageView) {
        this.pageView = pageView;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean isCommentNeedAudit() {
        return commentNeedAudit;
    }

    public void setCommentNeedAudit(boolean commentNeedAudit) {
        this.commentNeedAudit = commentNeedAudit;
    }
}
