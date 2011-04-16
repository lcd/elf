/**
 * Specl.com Inc.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.enumlations;

/**
 * 可评论的状态，可以对blog和post设置。但post的设置回覆盖blog上的设置。
 * 
 * @author laichendong
 */
public enum CommentStatus {

    ALL("允许所有人评论"), USERS("只允许注册用户评论"), NONE("禁止评论");

    private String title;

    CommentStatus() {
    }

    CommentStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
