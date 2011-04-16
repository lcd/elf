/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.dao;

import java.util.List;

import com.elf.entities.ArticleTagMap;

/**
 * 
 * @author laichendong
 */
public interface ArticleTagMapDao {

    void insertBatch(List<ArticleTagMap> atmList);

}
