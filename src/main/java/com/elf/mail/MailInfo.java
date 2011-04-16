/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.mail;

import java.util.Properties;

import com.elf.utils.ElfTools;

/**
 * 邮件信息
 * 
 * @author laichendong
 */
public class MailInfo {
    public static final String CONFIG_LOCATION = "/mail.properties";
    
    public static final String KEY_HOST_NAME = "hostName";
    public static final String KEY_SMTP_PORT = "smtpPort";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_FROM = "from";
    
    private String hostName;
    private String smtpPort;
    private String userName;
    private String password;
    private String from;
    
    private String to;
    private String subject;
    private String content;
    
    public MailInfo() {
        Properties mailProperties = ElfTools.readPropertiesFile(CONFIG_LOCATION);
        this.hostName = mailProperties.getProperty(KEY_HOST_NAME);
        this.smtpPort = mailProperties.getProperty(KEY_SMTP_PORT);
        this.userName = mailProperties.getProperty(KEY_USER_NAME);
        this.password = mailProperties.getProperty(KEY_PASSWORD);
        this.from = mailProperties.getProperty(KEY_FROM);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
