/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.elf.entities.User;
import com.elf.view.www.UserAction;

/**
 * vm工具
 * 1.提供vm中访问特定包中枚举的机制
 * 2.提供vm中访问类静态属性和静态方法的机制
 * 
 * @author laichendong
 */
public class VmUtil {
    
    private Logger logger = Logger.getLogger(VmUtil.class);
    private static final String ENUM_PACKAGE_NAME = "com.elf.enumlations";// 枚举所在包

    /**
     * 取得指定枚举的值
     * 
     * @param enmuName
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object[] enumValus(String enumName) {
        try {
            Class c = Class.forName(ENUM_PACKAGE_NAME + "." + enumName);
            Method valuesM = c.getDeclaredMethod("values");
            return (Object[]) valuesM.invoke(c);
        } catch (ClassNotFoundException e) {
            logger.error("vm代码中要访问的枚举" + ENUM_PACKAGE_NAME + "." + enumName + "不存在", e);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 根据枚举名称和枚举常量名取的枚举常量对象
     * @param enumName
     * @param constantName
     * @return
     */
    public Object enumConstant(String enumName, String constantName){
        try {
            Class<?> c = Class.forName(ENUM_PACKAGE_NAME + "." + enumName);
            Object[] enumConstants = c.getEnumConstants();
            for(int i=0; i<enumConstants.length; i++){
               if( ((Enum<?>)enumConstants[i]).name().equalsIgnoreCase(constantName)){
                   return enumConstants[i];
               }
            }
        } catch (ClassNotFoundException e) {
            logger.error("vm代码中要访问的枚举" + ENUM_PACKAGE_NAME + "." + enumName + "不存在", e);
        }
        return null;
    }
    
    
    /**
     * 访问某个类的public属性
     * @param ClassQualifiedName
     * @param fieldName
     * @return
     */
    public Object publicField(String ClassQualifiedName,String fieldName){
        try {
            Class<?> c = Class.forName(ClassQualifiedName);
            return c.getDeclaredField(fieldName);
        } catch (ClassNotFoundException e) {
            logger.error("vm代码中要访问的类" + ClassQualifiedName + "不存在", e);
        } catch (SecurityException e) {
            logger.error("vm代码中要访问的属性" + ClassQualifiedName + "." + fieldName + "不是public的", e);
        } catch (NoSuchFieldException e) {
            logger.error("vm代码中要访问的属性" + ClassQualifiedName + "." + fieldName + "不存在", e);
        }
        return null;
    }
    
    /**
     * 访问session中的user对象
     * @param session
     * @return
     */
    public User sessionUser(HttpSession session){
        return (User) session.getAttribute(UserAction.LOGIN_SESSION_NAME);
    }
    
    /**
     * 日期格式化
     * @param date 日期对象
     * @param pattern  格式化pattern
     * @return
     */
    public String formatDate(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
    /**
     * 日期格式化  pattern = yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public String formatDateLong(Date date){
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 日期格式化  pattern = yyyy-MM-dd
     * @param date
     * @return
     */
    public String formatDateShort(Date date){
        return formatDate(date, "yyyy-MM-dd");
    }

    public static void main(String[] asd){
        VmUtil v = new VmUtil();
        System.out.println(v.enumConstant("CommentStatus",""));
    }
}
