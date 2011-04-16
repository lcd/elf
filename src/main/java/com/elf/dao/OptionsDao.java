/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.dao;

import java.util.List;
import com.elf.entities.Option;
import com.elf.enumlations.Autoload;

/**
 * 
 * @author laichendong
 */
public interface OptionsDao {

    List<Option> selectMultiOptions(Autoload autoload);

}
