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
     * @return 
     * @throws LoginException 
     */
    User login(User user) throws LoginException;

}
