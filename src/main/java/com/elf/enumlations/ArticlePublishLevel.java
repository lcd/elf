/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.enumlations;

/**
 * 文章公开程度
 * 
 * @author laichendong
 */
public enum ArticlePublishLevel {
    
    PUBLISH("发布"), PROTECT("凭密码访问"), PRIVATE("私有的");

    private String title;

    ArticlePublishLevel() {
    }

    ArticlePublishLevel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
