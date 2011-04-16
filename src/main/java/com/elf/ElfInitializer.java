/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.elf.dao.OptionsDao;
import com.elf.entities.Option;
import com.elf.enumlations.Autoload;

import org.springframework.stereotype.Component;

/**
 * elf初始化器，执行elf初始化所需的工作
 * 
 * @author laichendong
 */
@Component
public class ElfInitializer extends HttpServlet {
    
    private static final long serialVersionUID = 8809347508002414390L;

    public static final String ELF_METAS = "elfMetas";
    
    @Resource
    private OptionsDao optionsDao;
    private Logger logger = Logger.getLogger(ElfInitializer.class);

    public ElfInitializer(){
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        loadElfMetasToContext(config.getServletContext());
    }
    
    /**
     * 加载options中为autoLoad的option到容器上下文中
     */
    private void loadElfMetasToContext(ServletContext context) {
        logger.info("加载ElfMetas到servletContext中。");
        List<Option> optionsList = optionsDao.selectMultiOptions(Autoload.YES);
        Map<String, String> elfMetas = new HashMap<String, String>();
        if (optionsList != null) {
            for (Option option : optionsList) {
                elfMetas.put(option.getName(), option.getValue());
            }
        }
        context.setAttribute(ELF_METAS, elfMetas);
        logger.info("ElfMetas加载完成:"+elfMetas);
    }

}
