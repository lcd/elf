/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.view;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.elf.biz.UserBiz;
import com.elf.entities.User;
import com.elf.utils.CookieUtil;
import com.elf.view.www.UserAction;
import org.apache.commons.codec.binary.Base64;

/**
 * 访问控制过滤器
 * 
 * @author laichendong
 */
public class ElfAcFilter implements Filter {

    @SuppressWarnings("unused")
    private Logger logger = Logger.getLogger(ElfAcFilter.class);
    private UserBiz userBiz;

    /**
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // 沉默是金
    }

    /**
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     *      javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        
        //如果是IE内核浏览器，跳转到不支持IE的页面
        String userAgent = request.getHeader("User-Agent");
        Pattern ieUaPattern = Pattern.compile("(msie) ([\\w.]+)", Pattern.CASE_INSENSITIVE); 
        Matcher m = ieUaPattern.matcher(userAgent);
        if(m.find()){
            response.sendRedirect("/noIE.jhtml");
            return;
        }

        // 尝试从session中取登录信息
        User loginedUser = null;
        Object o = request.getSession().getAttribute(ElfBaseAction.LOGIN_SESSION_NAME);
        if(o != null && o instanceof User){
        	loginedUser = (User) request.getSession().getAttribute(ElfBaseAction.LOGIN_SESSION_NAME);
        }
        if (loginedUser == null) {
            // session中没有登录信息，尝试从cookie中取登录信息
            Cookie loginCookie = CookieUtil.getBase64Cookie(request, ElfBaseAction.REMEMBER_ME_COOKIE_NAME);
            if (loginCookie != null) {
                try {
                    User userInCookie = loginWithCookie(loginCookie);
                    request.getSession().setAttribute(ElfBaseAction.LOGIN_SESSION_NAME, userInCookie);
                    // cookie登录成功,执行下一个filter
                    chain.doFilter(req, rep);
                } catch (LoginException e) {
                	e.printStackTrace();
                    // 有cookie但登录信息不正确，转向登录页面
                    redirectToLoginPage(request, response);
                    return;
                }
            } else {
                // 既无session又无cookie 转向登录页面
                redirectToLoginPage(request, response);
                return;
            }
        } else {
            // session中有登录信息，执行下一个filter
            chain.doFilter(req, rep);
        }
    }

    /**
     * 转向登录页面，同时把这次请求的url也传递给了登录页面，以便登录后进行跳转
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String targetAfterLogin = new String(new Base64().encode(request.getRequestURL().toString().getBytes()));
        response.sendRedirect("/login.jhtml?u=" + targetAfterLogin);
    }

    /**
     * 用cookie登录
     * 
     * @param loginCookie
     * @return
     * @throws LoginException
     */
    private User loginWithCookie(Cookie loginCookie) throws LoginException {
        String cookieValue = loginCookie.getValue();
        @SuppressWarnings("unchecked")
        Map<String, String> cookieMap = (Map<String, String>) JSONObject.toBean(JSONObject.fromObject(cookieValue),
                Map.class);
        User user = new User();
        user.setLoginName(cookieMap.get(UserAction.KEY_LOGIN_NAME));
        user.setPassword(cookieMap.get(UserAction.KEY_PASSWORD));
        return userBiz.login(user,true);
    }

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        // 从spring ioc容器中取得userBiz并初始化
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(config
                .getServletContext());
        userBiz = (UserBiz) webApplicationContext.getBean("userBiz");
    }

}
