/**
 * elf
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.entities;

import java.io.Serializable;

import com.elf.enumlations.Autoload;

/**
 * 
 * @author laichendong
 */
public class Option implements Serializable  {
    private static final long serialVersionUID = 257159338644978887L;
    private int id;
    private String name;
    private String value;
    private Autoload autoload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Autoload getAutoload() {
        return autoload;
    }

    public void setAutoload(Autoload autoload) {
        this.autoload = autoload;
    }

}
