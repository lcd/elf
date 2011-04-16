/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.excptions;

/**
 * 当执行删除操作时，指定id上不存在对应数据时抛出此异常
 * @author laichendong
 */
public class DataTryToDeleteNotExistException extends ElfException {

    private static final long serialVersionUID = -4233277151451076630L;

    public DataTryToDeleteNotExistException() {
        super();
    }

    public DataTryToDeleteNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataTryToDeleteNotExistException(String message) {
        super(message);
    }

    public DataTryToDeleteNotExistException(Throwable cause) {
        super(cause);
    }

}
