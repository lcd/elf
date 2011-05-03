/**
 * Specl.com Inc.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.biz;

import javax.security.auth.login.LoginException;

import org.apache.commons.mail.EmailException;

import com.elf.entities.User;

/**
 * 
 * @author laichendong
 */
public interface UserBiz {

    /**
     * 判断用户名是否已注册
     * 
     * @param loginName
     * @return
     */
    boolean isRegisted(String loginName);

    /**
     * 注册用户，向数据库里写入用户数据。
     * 
     * @param user 调用此方法是要保证user对象中有且仅有loginName，password和email属性
     * @throws EmailException 
     */
    void register(User user) throws EmailException;

    /**
     * 登录
     * @param user
     * @param pwdMd5ed 表示密码是否被MD5加密过
     * @return 
     * @throws LoginException 
     */
    User login(User user, boolean pwdMd5ed) throws LoginException;

	/**
	 * 修改用户密码
	 * 如果用户是第一次修改密码,将把用户状态从"ORIGINAL_PASSWORD"更新到"ACTIVE"
	 * @param user 要修改密码的用户
	 * @param newPwd 新密码
	 * @return 更新后的对象
	 */
    User modifyPassword(User user, String newPwd);

}
