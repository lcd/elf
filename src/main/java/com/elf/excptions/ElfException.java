/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.excptions;

/**
 * elf自定义异常基类
 * @author laichendong
 */
public class ElfException extends Exception {

    private static final long serialVersionUID = 6965655116783650948L;

    public ElfException() {
        super();
    }

    public ElfException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElfException(String message) {
        super(message);
    }

    public ElfException(Throwable cause) {
        super(cause);
    }
    
    
}
