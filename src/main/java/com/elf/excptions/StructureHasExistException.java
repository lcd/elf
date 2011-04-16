/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.excptions;

/**
 * 添加类别或标签时，如果存在同名的类别或标签，抛出此异常
 * @author laichendong
 */
public class StructureHasExistException extends ElfException {

    private static final long serialVersionUID = -5114713017975574570L;

    public StructureHasExistException() {
        super();
    }

    public StructureHasExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public StructureHasExistException(String message) {
        super(message);
    }

    public StructureHasExistException(Throwable cause) {
        super(cause);
    }

}
