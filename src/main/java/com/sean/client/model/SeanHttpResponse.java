/**
 * LY.com Inc
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.sean.client.model;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * @author shengsheng
 * @version Id: SeanHttpResponse, v 0.1 2018/8/21 23:31 shengsheng Exp $
 * @description
 */
public final class SeanHttpResponse<T extends Serializable> {
    /** Http请求数据 */
    private T                   response;

    /** Http请求头*/
    private Map<String, String> headers = Maps.newHashMap();
    public SeanHttpResponse() {
    }

    public SeanHttpResponse(T response, Map<String, String> headers) {
        super();
        this.response = response;
        this.headers = headers;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}