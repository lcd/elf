/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 邮件发送器
 * 
 * @author laichendong
 */
public class MailSender {
    public void send(MailInfo mailInfo) throws EmailException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(mailInfo.getHostName());
        email.setAuthentication(mailInfo.getUserName(), mailInfo.getPassword());
        email.setSubject(mailInfo.getSubject());
        email.addTo(mailInfo.getTo());
        email.setFrom(mailInfo.getFrom());
        email.setHtmlMsg(mailInfo.getContent());
        email.setCharset("UTF-8");
        email.send();
    }
}
