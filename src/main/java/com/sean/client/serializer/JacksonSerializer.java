package com.sean.client.serializer;

import com.sean.client.util.JacksonUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午6:09 sean Exp $
 * @description
 */
public class JacksonSerializer implements Serializer {
    public static Serializer getInstance() {
        return JacksonSerializerHolder.INSTANCE;
    }

    @Override
    public <T> String serialize(T obj, String dateFormat) {
        if (StringUtils.isNotBlank(dateFormat)) {
            return JacksonUtils.toJSONString(obj, dateFormat);
        } else {
            return JacksonUtils.toJSONString(obj);
        }
    }

    @Override
    public <T> T deserialize(String text, Class<T> clazz, String dateFormat) {
        return JacksonUtils.fromJSONString(text, clazz);
    }

    private static final class JacksonSerializerHolder {
        private static final JacksonSerializer INSTANCE = new JacksonSerializer();
    }
}
