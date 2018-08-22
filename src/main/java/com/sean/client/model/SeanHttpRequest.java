package com.sean.client.model;

import com.google.common.collect.Maps;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Map;

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


    /** Http头信息*/
    private Map<String, String> headers = Maps.newHashMap();

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public HttpMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(HttpMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public SerializeMethod getRequestSerializeMethod() {
        return requestSerializeMethod;
    }

    public void setRequestSerializeMethod(SerializeMethod requestSerializeMethod) {
        this.requestSerializeMethod = requestSerializeMethod;
    }

    public SerializeMethod getResponseSerializeMethod() {
        return responseSerializeMethod;
    }

    public void setResponseSerializeMethod(SerializeMethod responseSerializeMethod) {
        this.responseSerializeMethod = responseSerializeMethod;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public String getUrlObjectKey() {
        return urlObjectKey;
    }

    public void setUrlObjectKey(String urlObjectKey) {
        this.urlObjectKey = urlObjectKey;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public boolean isEncoded() {
        return encoded;
    }

    public void setEncoded(boolean encoded) {
        this.encoded = encoded;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getrCharset() {
        return rCharset;
    }

    public void setrCharset(String rCharset) {
        this.rCharset = rCharset;
    }
}
