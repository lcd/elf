/**
 * elf.
 * Copyright (c) 2010-2011 All Rights Reserved.
 */
package com.elf.excptions;

/**
 * 登录异常
 * 
 * @author laichendong
 */
public class LoginException extends ElfException {

    private static final long serialVersionUID = -8352921131782101090L;

    public LoginException() {
        super();
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

}
