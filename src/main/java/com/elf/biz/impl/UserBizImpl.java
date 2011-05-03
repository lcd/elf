/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.biz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;

import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Service;

import com.elf.biz.UserBiz;
import com.elf.dao.UserDao;
import com.elf.entities.User;
import com.elf.enumlations.UserStatus;
import com.elf.mail.MailContentGenerator;
import com.elf.mail.MailInfo;
import com.elf.mail.MailSender;
import com.elf.utils.ElfMetasUtil;
import com.elf.utils.ElfTools;
import com.elf.utils.PasswordGenerator;

/**
 * 
 * @author laichendong
 */
@Service("userBiz")
public class UserBizImpl implements UserBiz {
    @Resource
    private UserDao userDao;
    @Resource
    private MailContentGenerator mailContentGenerator;// 邮件内容生成器

    @Override
    public boolean isRegisted(String loginName) {
        int loginNameCount = userDao.selectLoginNameCount(loginName);
        return loginNameCount > 0;
    }

    @Override
    public void register(User user) throws EmailException {
        // 生成密码
        String password = PasswordGenerator.generate();
        user.setPassword(password);
        
        // 发送注册邮件
        Map<String, Object> registerMailData = new HashMap<String, Object>();
        registerMailData.put("loginName", user.getLoginName());
        registerMailData.put("password", user.getPassword());
        registerMailData.put("email", user.getEmail());
        sendRegisterMail(registerMailData);

        // 初始化用户的必要属性
        user.setRegisterTime(new Date());
        user.setStatus(UserStatus.ORIGINAL_PASSWORD);
        // 密码加密
        String passwordMd5 = ElfTools.md5(user.getPassword());
        user.setPassword(passwordMd5);
        // 向数据库插入数据
        userDao.insert(user);
    }

    /**
     * 发送注册邮件
     * 
     * @param registerMailData
     * @return
     * @throws EmailException
     */
    private void sendRegisterMail(Map<String, Object> registerMailData) throws EmailException {
        String mailContext = mailContentGenerator.generate("register", registerMailData);
        MailInfo mailInfo = new MailInfo();
        mailInfo.setContent(mailContext);
        mailInfo.setTo((String) registerMailData.get("email"));
        mailInfo.setSubject("[" + ElfMetasUtil.get(ElfMetasUtil.KEY_BLOG_NAME) + "]您的用户名和密码");
        new MailSender().send(mailInfo);
    }

    @Override
    public User login(User user, boolean pwdMd5ed) throws LoginException {
        // 缓存密码及其md5
        String password = user.getPassword();
        //String passwordMd5 = ElfTools.md5(user.getPassword());
        // 从数据库取数据
        user = userDao.selectSingleUser(user);
        if (user == null) {
            throw new LoginException("用户名错误。");
        }
        // 对比密码
        if(pwdMd5ed && !user.getPassword().equals(password)){//加密过的密码
        	throw new LoginException("密码错误。");
        }else if(!pwdMd5ed && !user.getPassword().equals(ElfTools.md5(password))){//没加密过的密码
        	throw new LoginException("密码错误。");
        }else{
	        // 验证通过，返回查询出来的user对象
	        user.setPassword(password);
	        return user;
        }
    }

	@Override
	public User modifyPassword(User user, String newPwd) {
		//加密密码
		newPwd = ElfTools.md5(newPwd);
		user.setPassword(newPwd);
		if(user.getStatus() == UserStatus.ORIGINAL_PASSWORD){
			user.setStatus(UserStatus.ACTIVE);
		}
		//更新数据库
		return userDao.modifyPassword(user);
	}

}
