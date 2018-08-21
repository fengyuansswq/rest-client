package com.sean.client.serializer;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午6:09 sean Exp $
 * @description
 */
public class JacksonSerializer implements Serializer{
    public static Serializer getInstance() {
        return JacksonSerializerHolder.INSTANCE;
    }

    @Override
    public <T> String serialize(T obj, String dateFormat) {
        return null;
    }

    @Override
    public <T> T deserialize(String text, Class<T> clazz, String dateFormat) {
        return null;
    }

    private static final class JacksonSerializerHolder {
        private static final JacksonSerializer INSTANCE = new JacksonSerializer();
    }
}
