package com.elf.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.elf.dao.ArticleDao;
import com.elf.entities.Article;
import com.elf.entities.Structure;
import com.elf.entities.User;
import com.elf.vo.ArticleListFilter;

@Repository("articleDao")
public class ArticleDaoImpl extends SqlMapClientDaoSupport implements ArticleDao {

    @Override
    public int insert(Article article) {
        return (Integer) getSqlMapClientTemplate().insert("articles.insert", article);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getArticleList(ArticleListFilter filter) {
        return getSqlMapClientTemplate().queryForList("articles.getArticleList", filter);
    }

    @Override
    public User getAuthor(int articleId) {
        return (User) getSqlMapClientTemplate().queryForObject("articles.getAuthor", articleId);
    }

    @Override
    public Structure getCategory(int articleId) {
        return (Structure) getSqlMapClientTemplate().queryForObject("articles.getCategory", articleId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Structure> getTags(int articleId) {
        return  getSqlMapClientTemplate().queryForList("articles.getTags", articleId);
    }

    @Override
    public int getAllCommentsCount(int articleId) {
        return (Integer) getSqlMapClientTemplate().queryForObject("articles.getAllCommentsCount", articleId);
    }

}
