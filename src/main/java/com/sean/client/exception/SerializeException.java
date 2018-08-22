package com.sean.client.exception;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/22 上午10:22 sean Exp $
 * @description
 */
public class SerializeException extends RuntimeException {

    public SerializeException(Class errorClass, Throwable throwable) {
        super("serialize failed, errorClass is " + errorClass.getName(), throwable);
    }

    public SerializeException(Object errorObject, Throwable throwable) {
        super("serialize failed, errorClass is " + errorObject.getClass().getName(), throwable);
    }

}