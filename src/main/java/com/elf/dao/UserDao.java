/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.dao;

import com.elf.entities.User;

/**
 * 
 * @author laichendong
 */
public interface UserDao {

    /**
     * 查询一个登录名在表中的个数
     * @param loginName
     * @return
     */
    int selectLoginNameCount(String loginName);

    /**
     * 向users表插入用户数据
     * @param user
     */
    void insert(User user);

    /**
     * 查询登录用户——通过登录名,密码组合查询用户数据
     * @param user
     * @return
     */
    User selectSingleUser(User user);

}
