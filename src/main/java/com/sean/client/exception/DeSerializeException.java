package com.sean.client.exception;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/22 上午10:27 sean Exp $
 * @description
 */
public class DeSerializeException extends RuntimeException {

    public DeSerializeException(Class errorClass, Throwable throwable) {
        super("deSerialize failed, errorClass is : " + errorClass.getName(), throwable);
    }

    public DeSerializeException(Class errorClass, String jsonStr, Throwable throwable) {
        super("deSerialize failed, errorClass is : " + errorClass.getName() + ", json str is : " + jsonStr, throwable);
    }

    public DeSerializeException(Object object, Throwable throwable) {
        super("deSerialize failed, errorClass is : " + object.getClass().getName(), throwable);
    }

    public DeSerializeException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
