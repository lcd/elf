/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.mail;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 更具模板和数据生成邮件内容
 * 
 * @author laichendong
 */
@Component("mailContentGenerator")
public class MailContentGenerator {
    private String templateDirPath; // 模板文件夹路径
    private VelocityEngine velocityEngine; // velcotyEnging用于处理模板

    public String generate(String templateName, Map<String, Object> mailData) {
        String templateLocation = templateDirPath + "/" + templateName + ".vm";
        String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", mailData);
        return content;
    }

    public String getTemplateDirPath() {
        return templateDirPath;
    }

    public void setTemplateDirPath(String templateDirPath) {
        this.templateDirPath = templateDirPath;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

}
