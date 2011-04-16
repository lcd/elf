package com.elf.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.elf.dao.ArticleTagMapDao;
import com.elf.entities.ArticleTagMap;

@Repository("articleTagMapDao")
public class ArticleTagMapDaoImpl extends SqlMapClientDaoSupport implements ArticleTagMapDao {

    @Override
    public void insertBatch(List<ArticleTagMap> atmList) {
        SqlMapClientTemplate template = getSqlMapClientTemplate();
        for(ArticleTagMap atm : atmList){
            template.insert("article_tag_map.insert", atm);
        }
    }

}
