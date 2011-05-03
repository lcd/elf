/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.view.www;

import javax.security.auth.login.LoginException;

import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import com.elf.biz.UserBiz;
import com.elf.entities.User;
import com.elf.utils.ElfTools;
import com.elf.utils.Struts2Util;
import com.elf.view.ElfBaseAction;
import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author laichendong
 */
public class UserAction extends ElfBaseAction {

    private static final long serialVersionUID = 7399910052889808743L;
    private static final String TARGET_AFTER_LOGIN = "targetAfterLogin";// 登录后有目的地的跳转的result Name
    private Logger logger = Logger.getLogger(UserAction.class);
    private User user;
    private UserBiz userBiz;
    private String u; // 登录后跳转到的页面
    private boolean rememberMe; // 标记是否保存cookie

    /**
     * 转到注册页面
     * 
     * @return
     */
    public String register() {
        return SUCCESS;
    }

    /**
     * 执行注册操作
     * 
     * @return
     */
    public String doRegister() {
        // 表单验证
        if (!validateRegisterForm(user)) {
            return INPUT;
        }
        // 注册
        try {
            userBiz.register(user);
        } catch (EmailException e) {
            logger.error("注册邮件发送失败", e);
            errorDescription.add("注册邮件发送失败，请稍后再试。");
            return ERROR;
        }
        return SUCCESS;
    }

    /**
     * 转到登录页面
     * 
     * @return
     */
    public String login() {
        return SUCCESS;
    }

    /**
     * 执行登录操作
     * 
     * @return
     */
    public String doLogin() {
        // 表单验证
        if (!validateLoginForm(user)) {
            return INPUT;
        }
        // 判断用户名密码匹配
        try {
            user = userBiz.login(user, false);
        } catch (LoginException e) {
            errorDescription.add(e.getLocalizedMessage());
            return INPUT;
        }
        //加密密码，保证session和cookie里的密码都是加密过的
        user.setPassword(ElfTools.md5(user.getPassword()));
        // 保存登录session
        Struts2Util.getSession().setAttribute(LOGIN_SESSION_NAME, user);
        // 如果勾选了“记住我”就保存cookie
        if (rememberMe) {
            setLoginedCookie(user, REMEMBER_ME_EXPIRY, Struts2Util.getResponse());
        }

        // 处理登录后的跳转
        if (u != null) {
            u = new String(new Base64().decode(u.getBytes()));
            logger.info(u);
            // 如果有目的地址且解码成功，跳转到目的地址
            return TARGET_AFTER_LOGIN;
        }
        return SUCCESS;
    }
    
    /**
     * 转到不支持IE浏览器的提示页面
     * @return
     */
    public String noIE(){
        return SUCCESS;
    }
    
    public String logout(){
        // 清空session
        Struts2Util.getSession().removeAttribute(LOGIN_SESSION_NAME);
        return SUCCESS;
    }

    /**
     * 验证登录表单
     * 
     * @param user
     * @return
     */
    private boolean validateLoginForm(User user) {
        errorDescription.clear();
        if (user == null) {
            errorDescription.add("误操作？攻击我？别啊。");
            return false;
        }
        if (!user.getLoginName().matches("^\\w{2,32}$")) {
            errorDescription.add("非法的用户名！用户名由2-32位单词字母组成。");
        }
        if (!user.getPassword().matches("^[\\w\\u0021-\\u002f]{6,32}$")) {
            errorDescription.add("非法的密码输入！请检查密码中是否有不常用的特殊字符。");
        }
        return errorDescription.isEmpty();
    }

    /**
     * 验证注册表单
     * 
     * @param user
     * @return
     */
    private boolean validateRegisterForm(User user) {
        errorDescription.clear();
        if (user == null) {
            errorDescription.add("误操作？攻击我？别啊。");
            return false;
        }
        if (!user.getLoginName().matches("^\\w{2,32}$")) {
            errorDescription.add("取一个合理的用户名吧！他由2-32位的字母，数字和下划线组合而成。");
        }
        if (!user.getEmail().matches("^\\w+@\\w+.\\w+$")) {
            errorDescription.add("邮箱地址很重要，写一个你常用的吧。");
        }
        if (userBiz.isRegisted(user.getLoginName())) {
            errorDescription.add("这个名字已经被人抢注了！再取个有创意的吧。");
        }
        return errorDescription.isEmpty();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserBiz getUserBiz() {
        return userBiz;
    }

    public void setUserBiz(UserBiz userBiz) {
        this.userBiz = userBiz;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
