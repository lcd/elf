/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.dao;

import java.util.List;

import com.elf.entities.Article;
import com.elf.entities.Structure;
import com.elf.entities.User;
import com.elf.vo.ArticleListFilter;

/**
 * 
 * @author laichendong
 */
public interface ArticleDao {

    int insert(Article article);

    List<Article> getArticleList(ArticleListFilter filter);

    User getAuthor(int articleId);

    Structure getCategory(int articleId);

    List<Structure> getTags(int articleId);

    int getAllCommentsCount(int articleId);

}
