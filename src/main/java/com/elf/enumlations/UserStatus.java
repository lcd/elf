/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.enumlations;

/**
 * 
 * @author laichendong
 */
public enum UserStatus {
	DEACTIVE("未激活"), // 未激活，只是注册了，还没有登陆过
	ORIGINAL_PASSWORD("原始密码"), // 登录过，但依然使用自动生成的密码
	ACTIVE("已激活"), // 已激活，正常的状态
	DISABLED("禁用"); // 禁用，这种状态不允许登录

	private String title;

	UserStatus(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
