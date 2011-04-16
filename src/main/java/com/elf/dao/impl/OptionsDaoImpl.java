/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.elf.dao.OptionsDao;
import com.elf.entities.Option;
import com.elf.enumlations.Autoload;

/**
 * 
 * @author laichendong
 */
@Repository("optionsDao")
public class OptionsDaoImpl extends SqlMapClientDaoSupport implements OptionsDao {

    /** 
     * @see com.elf.dao.OptionsDao#selectMultiOptions(com.elf.enumlations.Autoload)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Option> selectMultiOptions(Autoload autoload) {
        return  getSqlMapClientTemplate().queryForList("options.selectMultiOptions", autoload);
    }

}
