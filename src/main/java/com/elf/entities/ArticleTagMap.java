/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.entities;

import java.io.Serializable;

/**
 * 
 * @author laichendong
 */
public class ArticleTagMap implements Serializable {
    
    private static final long serialVersionUID = 2865884571464537043L;
    
    private int id;
    private int articleId;
    private int tagId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
