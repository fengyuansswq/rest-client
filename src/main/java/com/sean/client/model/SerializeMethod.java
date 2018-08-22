package com.sean.client.model;

import com.sean.client.serializer.*;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午5:34 sean Exp $
 * @description
 */
public enum SerializeMethod {
                             /** none */
                             NONE_SERIALIZER(NoneSerializer.getInstance()),
                             /** fastjson */
                             FASTJSON_SERIALIZER(FastJsonSerializer.getInstance()),
                             /** Jackson */
                             JACKSON_SERIALIZER(JacksonSerializer.getInstance()),
                             /** xstream dict */
                             XSTREAM_DICT_SERIALIZER(XStreamDictSerializer.getInstance());

    private Serializer serializer;

    private SerializeMethod(Serializer serializer) {
        this.serializer = serializer;
    }

    /**
     * Getter method for property <tt>serializer</tt>.
     *
     * @return property value of serializer
     */
    public Serializer getSerializer() {
        return serializer;
    }
}
