/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.biz;

import java.util.List;
import java.util.Map;

import com.elf.entities.Article;
import com.elf.entities.User;
import com.elf.vo.ArticleListFilter;

/**
 * 
 * @author laichendong
 */
public interface ArticleBiz {

    void addArticle(Article article, String tagIds, User author);

    List<Map<String, Object>> getArticleListData(ArticleListFilter filter);

}
