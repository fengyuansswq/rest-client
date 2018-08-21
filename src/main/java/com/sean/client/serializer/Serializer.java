package com.sean.client.serializer;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午5:36 sean Exp $
 * @description
 */
public interface Serializer {

    /**
     * 对象序列化
     *
     * @param obj
     * @param dateFormat
     * @return
     */
    <T> String serialize(T obj, String dateFormat);

    /**
     * 对象反序列化
     *
     * @param text
     * @param clazz
     * @param dateFormat
     * @return
     */
    <T> T deserialize(String text, Class<T> clazz, String dateFormat);
}
