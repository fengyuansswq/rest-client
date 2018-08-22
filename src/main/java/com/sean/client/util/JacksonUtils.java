package com.sean.client.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Strings;
import com.sean.client.exception.DeSerializeException;
import com.sean.client.exception.SerializeException;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午6:23 sean Exp $
 * @description
 */
public class JacksonUtils {

    /**
     * 日期类型默认序列化格式
     */
    private static final String      DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final ObjectMapper MAPPER              = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
    }

    /**
     * java对象序列化为字符串
     *
     * @param obj 待序列化的java对象
     * @return
     */
    public static String toJSONString(Object obj) {
        return toJSONString(obj, null);
    }

    /**
     * Java对象转换为JSON字符串
     *
     * @param obj        Java对象
     * @param dateFormat 日期格式
     * @return 序列化后的JSON字符串
     */
    public static String toJSONString(Object obj, String dateFormat) {
        String json;
        try {
            if (StringUtils.isNotBlank(dateFormat)) {
                MAPPER.setDateFormat(new SimpleDateFormat(dateFormat));
                json = MAPPER.writeValueAsString(obj);
                MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
            } else {
                json = MAPPER.writeValueAsString(obj);
            }
            return json;
        } catch (JsonProcessingException e) {
            throw new SerializeException(obj, e);
        }
    }

    public static <T> T fromJSONString(String json, Class<T> valueType) {
        if (Strings.isNullOrEmpty(json) || valueType == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, valueType);
        } catch (Exception e) {
            throw new DeSerializeException(valueType, json, e);
        }
    }

    public static <T> List<T> fromJSONString(String json, TypeReference typeReference) {
        if (Strings.isNullOrEmpty(json) || typeReference == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            throw new DeSerializeException("deserialize list error, errorType = " + typeReference.getType().getTypeName() + ", json = " + json, e);
        }
    }

    public static Map<String, String> fromJSONString(String json) {
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class));
        } catch (Exception e) {
            throw new DeSerializeException("deserialize map error, errorType = Map<String, String>" + ", json = " + json, e);
        }
    }
}
