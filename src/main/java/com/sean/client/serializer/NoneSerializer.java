package com.sean.client.serializer;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午5:43 sean Exp $
 * @description
 */
public class NoneSerializer implements Serializer {

    private NoneSerializer() {
    }

    public static final NoneSerializer getInstance() {
        return NoneSerializerHolder.INSTANCE;
    }

    @Override
    public <T> String serialize(T obj, String dateFormat) {
        return obj.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(String text, Class<T> clazz, String dateFormat) {
        return (T) text;
    }

    private static final class NoneSerializerHolder {
        private static final NoneSerializer INSTANCE = new NoneSerializer();
    }
}
