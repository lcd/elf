/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.elf.entities.User;
import com.elf.utils.CookieUtil;
import com.elf.utils.VmUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author laichendong
 */
public class ElfBaseAction extends ActionSupport {

    private static final long serialVersionUID = 7596236152185729924L;
    public static final String LOGIN_SESSION_NAME = "loginedUser"; // session中保存登录信息的句柄
    public static final int REMEMBER_ME_EXPIRY = 2 * 7 * 24 * 60 * 60;// “记住我”的过期时间，默认为2周
    public static final String REMEMBER_ME_COOKIE_NAME = "elfLoginCookie";// "记住我"cookie名称
    public static final String KEY_LOGIN_NAME = "loginName";// "记住我"cookie中用户名的key
    public static final String KEY_PASSWORD = "password";// "记住我"cookie中密码的key
    
    protected static final String KEY_ERROR = "error";
    protected List<String> errorDescription = new ArrayList<String>(); // 错误描述信息值对象
    protected Map<String, List<String>> ajaxErrorDescription = new HashMap<String, List<String>>();//ajax返回错误信息的值对象
    public VmUtil vu = new VmUtil();// vm中用于访问枚举的工具

    public List<String> getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(List<String> errorDescription) {
        this.errorDescription = errorDescription;
    }
    
    /**
     * 从异常对象构造供ajax返回的错误提示json串，提示内容为e.getMessage()
     * @param e
     * @return
     */
    protected String createErrorJsonFromException(Throwable e){
        errorDescription.clear();
        errorDescription.add(e.getMessage());
        ajaxErrorDescription.put(KEY_ERROR, errorDescription);
        return JSONObject.fromObject(ajaxErrorDescription).toString();
    }
    
    /**
     * 设置登录cookie
     * @param user
     * @param rememberMeExpiry
     * @param response
     */
    protected void setLoginedCookie(User user, long rememberMeExpiry, HttpServletResponse response) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        cookieMap.put(KEY_LOGIN_NAME, user.getLoginName());
        cookieMap.put(KEY_PASSWORD, user.getPassword());
        String cookieValue = JSONObject.fromObject(cookieMap).toString();
        CookieUtil.setBase64Cookie(response, REMEMBER_ME_COOKIE_NAME, cookieValue, null, "/", REMEMBER_ME_EXPIRY);
    }

    public VmUtil getVu() {
        return vu;
    }

    public void setVu(VmUtil vu) {
        this.vu = vu;
    }

    public Map<String, List<String>> getAjaxErrorDescription() {
        return ajaxErrorDescription;
    }

    public void setAjaxErrorDescription(Map<String, List<String>> ajaxErrorDescription) {
        this.ajaxErrorDescription = ajaxErrorDescription;
    }

}
