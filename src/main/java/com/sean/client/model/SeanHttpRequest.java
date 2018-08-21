package com.sean.client.model;

import org.springframework.http.HttpMethod;

import java.io.Serializable;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 下午4:15 sean Exp $
 * @description
 */
public final class SeanHttpRequest<T extends Serializable> {
    /** Http请求数据 */
    private T               request;

    /** Http请求地址 */
    private String          endpoint;

    /** Http method */
    private HttpMethod      requestMethod;

    /** 请求参数序列化方法 */
    private SerializeMethod requestSerializeMethod;

    /** 应答数据序列化方法 */
    private SerializeMethod responseSerializeMethod;

    /** 参数传递方式 */
    private ParameterType   parameterType;

    /** Object url入参的Key */
    private String          urlObjectKey;

    /** 日期格式 */
    private String          dateFormat;

    /** URL参数是否需要编码*/
    private boolean         encoded;

    /** URL参数编码字符集 ，默认UTF-8*/
    private String          charset = "UTF-8";

    /** 返回体编码字符集,默认使用响应头编码 */
    private String          rCharset;
}
