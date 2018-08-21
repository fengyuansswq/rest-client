/**
 * LY.com Inc
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.sean.client.exception;

/**
 * @author shengsheng
 * @version Id: HttpInvokeException, v 0.1 2018/8/21 23:34 shengsheng Exp $
 * @description
 */
public class HttpInvokeException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 1L;

    public HttpInvokeException() {
        super();
    }

    public HttpInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpInvokeException(String message) {
        super(message);
    }

    public HttpInvokeException(Throwable cause) {
        super(cause);
    }

}