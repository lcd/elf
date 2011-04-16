/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.enumlations;

/**
 * 文章发布状态
 * 
 * @author laichendong
 */
public enum ArticlePublishStatus {
    NORMAL("已发布"), DRAFT("草稿"),OFF_LINE("已下线"), DELETED("已删除");
    private String title;

    ArticlePublishStatus() {
    }

    ArticlePublishStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
