/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


import org.apache.log4j.Logger;

import com.elf.entities.Structure;

/**
 * 工具函数集
 * 
 * @author laichendong
 */
public class ElfTools {
    private static Logger logger = Logger.getLogger(ElfTools.class);

    /**
     * 读取属性文件。如读取失败，返回空的properties对象，即properties.isEmpty() == true
     * 
     * @param fileLocation
     * @return
     */
    public static Properties readPropertiesFile(String fileLocation) {
        InputStream in = ElfTools.class.getResourceAsStream(fileLocation);
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            logger.error("读取属性文件：" + fileLocation + "失败", e);
        }
        return properties;
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    /**
     * 把逗号（全角半角都可以）字符串格式化成sql语句中能直接用的格式
     * 
     * @param newTagNames
     * @return
     */
    public static String formatCSVForSQL(String str) {
        String[] s = splitCSV(str);
        StringBuffer sb = new StringBuffer();
        for (String ss : s) {
            sb.append("\"" + ss + "\",");
        }
        return sb.substring(0, sb.length() - 1);
    }
    
    /**
     * 逗号分割字符串处理成数组，支持全角和半角逗号共存及其的任意数量任意组合
     * @param csvString
     * @return
     */
    public static String[] splitCSV(String csvString){
        csvString = csvString.replaceAll("，+", ",");
        csvString = csvString.replaceAll(",+", ",");
        if(csvString.startsWith(",")){
            csvString = csvString.substring(1);
        }
        if(csvString.endsWith(",")){
            csvString = csvString.substring(0, csvString.length());
        }
        if(csvString.isEmpty()){
            return new String[0];
        }
        return csvString.split(",");
    }

    /**
     * 删除数组里指定下标的元素，后面元素依次前移
     * @param array
     * @param index
     */
    public static void deleteArrayItem(Object[] array, int index) {
        //参数不合法
        if (null == array || array.length == 0 || index < 0 || index >= array.length){
            return;
        }
        //数组长度为1
        if (array.length == 1) {
            array[0] = null;
            return;
        }
        //正常情况
        for (int i = index; i < array.length - 1; ++i) {
            array[i] = array[i + 1];
        }
        array[array.length - 1] = null;
    }

    /**
     * 数组去重
     * @param array 
     * @return 
     * @return
     */
    public static <T> T[] uniqueArrayItem(T[] array) {
        Set<T> set = new LinkedHashSet<T>();
        for(T item : array){
            set.add(item);
        }
        return set.toArray(array);
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objectToMap(Object obj){
        Map<String, Object> map = new HashMap<String, Object>();
        @SuppressWarnings("rawtypes")
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            String fieldName = field.getName();
            String getterName = fieldGetterName(fieldName);
            try {
                Method getter = clazz.getMethod(getterName);
                map.put(fieldName, getter.invoke(obj));
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                //该属性没有提供get方法，跳过
                continue;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    private static String fieldGetterName(String fieldName) {
        String firstLetter = fieldName.substring(0,1).toUpperCase();
        return ("get" + firstLetter + fieldName.substring(1));
    }

    public static void main(String[] ad){
        Structure s = new Structure();
        s.setAlias("sss");
        s.setId(122);
        s.setName("sdf");
        
        System.out.println(objectToMap(s));
    }
}
