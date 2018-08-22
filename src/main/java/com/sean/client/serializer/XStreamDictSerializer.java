package com.sean.client.serializer;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.Map;
import java.util.TimeZone;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/22 上午10:37 sean Exp $
 * @description
 */
public class XStreamDictSerializer implements Serializer {

    /**
     * XStream Class Map
     */
    private Map<String, XStream> xstreams = Maps.newConcurrentMap();

    private XStreamDictSerializer() {
    }

    public static final XStreamDictSerializer getInstance() {
        return XStreamDictSerializerHolder.INSTANCE;
    }

    @Override
    public <T> String serialize(T obj, String dateFormat) {
        XStream xstream = retrieveXStream(obj.getClass());
        return xstream.toXML(obj);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(String text, Class<T> clazz, String dateFormat) {
        XStream xstream = retrieveXStream(clazz);
        return (T) xstream.fromXML(text);
    }

    private <T> XStream retrieveXStream(Class<T> clazz) {
        XStream xstream = xstreams.get(clazz.getName());
        if (xstream == null) {
            xstream = createXStream(clazz);
            xstreams.put(clazz.getName(), xstream);
        }
        return xstream;
    }

    private <T> XStream createXStream(Class<T> clazz) {
        XStream xstream = new XStream(new DomDriver("utf8"));
        xstream.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss",
                new String[]{"yyyy/MM/dd HH:mm:ss", "yyyy-MM-dd"}, TimeZone.getTimeZone("GMT+8")));
        xstream.registerConverter(new DoubleConverter());
        xstream.registerConverter(new IntConverter());
        xstream.ignoreUnknownElements();
        xstream.processAnnotations(clazz);
        return xstream;
    }

    private static final class XStreamDictSerializerHolder {
        private static final XStreamDictSerializer INSTANCE = new XStreamDictSerializer();
    }
}
