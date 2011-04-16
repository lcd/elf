/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.utils;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.elf.ElfInitializer;

/**
 * 访问elfMeta的工具类
 * 
 * @author laichendong
 */
public class ElfMetasUtil {
    
    public static final String KEY_BLOG_NAME = "blogName";
    public static final String KEY_BLOG_URL = "blogUrl";
    
    /**
     * 用于struts2 action中取得elfMetas的方法
     * @param metaKey
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String get(String metaKey){
        ServletContext servletContext = Struts2Util.getServletContext();
        Map<String, String> elfMetas = (Map<String, String>) servletContext.getAttribute(ElfInitializer.ELF_METAS);
        return elfMetas.get(metaKey);
    }
    
    /**
     * 普通场景取得elfMetas的方法
     * @param metaKey
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String get(String metaKey, HttpServletRequest request){
        Map<String, String> elfMetas = (Map<String, String>) request.getSession().getServletContext().getAttribute(ElfInitializer.ELF_METAS);
        return elfMetas.get(metaKey);
    }
}
