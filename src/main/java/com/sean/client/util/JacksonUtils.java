package com.sean.client.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午6:23 sean Exp $
 * @description
 */
public class JacksonUtils {

    /** 日期类型默认序列化格式 */
    private static final String              DEFAULT_DATE_FORMAT  = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static{
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        MAPPER.registerModule(new GuavaModule());
        MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
    }
}
