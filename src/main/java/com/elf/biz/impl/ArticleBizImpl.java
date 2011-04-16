/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elf.biz.ArticleBiz;
import com.elf.dao.ArticleDao;
import com.elf.dao.ArticleTagMapDao;
import com.elf.entities.Article;
import com.elf.entities.ArticleTagMap;
import com.elf.entities.Structure;
import com.elf.entities.User;
import com.elf.enumlations.ArticlePublishStatus;
import com.elf.utils.ElfTools;
import com.elf.vo.ArticleListFilter;

/**
 * 
 * @author laichendong
 */
@Service("articleBiz")
public class ArticleBizImpl implements ArticleBiz {

    @Resource
    private ArticleDao articleDao;
    @Resource
    private ArticleTagMapDao articleTagMapDao;

    @Override
    public void addArticle(Article article, String tagIds, User author) {
        // 初始化文章属性，比如添加时间等
        article.setAuthorId(author.getId());
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setPublishStatus(ArticlePublishStatus.NORMAL);
        // 保存文章
        int articleId = articleDao.insert(article);

        // 保存文章和标签的映射
        List<ArticleTagMap> atmList = new ArrayList<ArticleTagMap>();
        String[] tagIdArray = ElfTools.splitCSV(tagIds);
        for (String tagId : tagIdArray) {
            ArticleTagMap atm = new ArticleTagMap();
            atm.setArticleId(articleId);
            atm.setTagId(Integer.parseInt(tagId));
            atmList.add(atm);
        }
        articleTagMapDao.insertBatch(atmList);
    }

    @Override
    public List<Map<String, Object>> getArticleListData(ArticleListFilter filter) {
        // 处理“开始”，“结束”时间
        String beginDate = filter.getBeginDate();// 保护现场
        if (beginDate.isEmpty()) {
            filter.setBeginDate("0000-00-00");
        }
        String endDate = filter.getEndDate();// 保护现场
        if (endDate.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            filter.setEndDate(sdf.format(c.getTime()));
        }

        // 获取数据
        List<Map<String, Object>> returnData = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<Article> articleList = articleDao.getArticleList(filter);
        for (Article article : articleList) {
            map = ElfTools.objectToMap(article);
            map.remove("publishStatus");
            map.put("publishStatus", ArticlePublishStatus.NORMAL);
            //作者昵称
            String authorNickname = articleDao.getAuthor(article.getId()).getNickname();
            map.put("authorNickname", authorNickname);
            //类型标题
            String categoryTitle = articleDao.getCategory(article.getId()).getName();
            map.put("categoryTitle", categoryTitle);
            //所有标签
            List<Structure> tags = articleDao.getTags(article.getId());
            map.put("tags", tags);
            //所有评论数
            int allCommentsCount = articleDao.getAllCommentsCount(article.getId());
            map.put("allCommentsCount", allCommentsCount);

            returnData.add(map);
        }

        // 还原现场。恢复“开始”和“结束时间”
        filter.setBeginDate(beginDate);
        filter.setEndDate(endDate);

        return returnData;
    }

}
