package com.elf.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;


/**
 * cookie 工具类
 * @author laichendong @ 2010-11-8 18:03:40
 *
 */
public class CookieUtil {
	private static Logger logger = Logger.getLogger(CookieUtil.class);
	
	/**
	 * 从request中取出指定名称的cookie
	 * @param request 请求
	 * @param name cookie名称
	 * @return 对应的cookie对象，如果没有对应的cookie则返回空值
	 */
	public static Cookie getCookie(HttpServletRequest request, String name){
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
    		for(Cookie cookie : cookies){
    			if(cookie.getName().equals(name)){
    				try {
    					cookie.setValue(URLDecoder.decode(cookie.getValue(),"utf-8"));
    					return cookie;
    				} catch (UnsupportedEncodingException e) {
    					logger.error("系统不支持utf-8编码",e);
    					return null;
    				}
    			}
    		}
		}
		return null;
	}
	
	/**
	 * 获得使用base64解密后的cookie，前提是这个cookie值是通过base64加密后的
	 * @param request 请求
	 * @param name cookie名
	 * @return
	 */
	public  static Cookie getBase64Cookie(HttpServletRequest request, String name){
		Cookie cookie = getCookie(request, name);
		if(cookie != null){
			String decodedValue = new String(Base64.decodeBase64(cookie.getValue()));
			cookie.setValue(decodedValue);
			return cookie;
		}
		return null;
	}
	
	/**
	 * 比较完整参数的设置cookie
	 * @param response 响应
	 * @param name cookie名称
	 * @param value cookie值
	 * @param domain cookie域
	 * @param path cookie共享路径
	 * @param expire cookie过期时间，以秒为单位
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String domain, String path, int expire){
		Cookie cookie;
		try {
			cookie = new Cookie(name,URLEncoder.encode(value,"utf-8"));
			cookie.setDomain(domain);
			cookie.setPath(path);
			cookie.setMaxAge(expire);
			response.addCookie(cookie);
			logger.debug(JSONObject.fromObject(cookie).toString());
		} catch (UnsupportedEncodingException e) {
			logger.error("this system unsupport utf-8", e);
		}
	}
	
	/**
	 * 把值用base64加密后再放入cookie
	 * @param response 响应
	 * @param name cookie名称
	 * @param value cookie值
	 * @param domain cookie域
	 * @param path cookie共享路径
	 * @param expire cookie过期时间，以秒为单位
	 */
	public static void setBase64Cookie(HttpServletResponse response, String name, String value, String domain, String path, int expire){
		String encodedValue = Base64.encodeBase64String(value.getBytes());
		setCookie(response, name, encodedValue, domain, path, expire);
	}
	
	/**
	 * 简化的setCookie方法,当前域，根路径，一天过期
	 * @param response 响应
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value){
		setCookie(response, name, value, null, "/", 60*60*24);
	}
	
	/**
	 * 简化的base64 cookie方法，当前域，根路径，一天过期
	 * @param response 响应
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public static void setBase64Cookie(HttpServletResponse response, String name, String value){
		String encodedValue = Base64.encodeBase64String(value.getBytes());
		setCookie(response, name, encodedValue);
	}
	
	/**
	 * 删除指定名称的cookie,如果找不到对应的cookie则抛出异常
	 * @param request 请求
	 * @param response 响应
	 * @param name  cookie名称
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String name){
		Cookie cookie = getCookie(request, name);
		if(cookie != null){
			cookie.setMaxAge(0);
			cookie.setValue(null);
			response.addCookie(cookie);
		}
	}
}
