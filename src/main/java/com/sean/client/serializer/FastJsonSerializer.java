package com.sean.client.serializer;

import com.sean.client.util.FastJsonUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午5:49 sean Exp $
 * @description
 */
public class FastJsonSerializer implements Serializer {

    private FastJsonSerializer() {
    }

    public static final FastJsonSerializer getInstance() {
        return FastJsonSerializerHolder.INSTANCE;
    }

    /**
     * @see Serializer#serialize(java.lang.Object, java.lang.String)
     */
    @Override
    public <T> String serialize(T obj, String dateFormat) {
        if (StringUtils.isNotBlank(dateFormat)) {
            return FastJsonUtils.toJSONString(obj, dateFormat);
        } else {
            return FastJsonUtils.toJSONString(obj);
        }
    }

    /**
     * @see Serializer#deserialize(java.lang.String, java.lang.Class, java.lang.String)
     */
    @Override
    public <T> T deserialize(String text, Class<T> clazz, String dateFormat) {
        return FastJsonUtils.fromJSONString(text, clazz);
    }

    private static final class FastJsonSerializerHolder {
        private static final FastJsonSerializer INSTANCE = new FastJsonSerializer();
    }

}
