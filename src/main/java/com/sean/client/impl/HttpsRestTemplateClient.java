package com.sean.client.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sean.client.RestHttpClientUtil;
import com.sean.client.RestTemplateClient;
import com.sean.client.exception.HttpInvokeException;
import com.sean.client.model.SeanHttpRequest;
import com.sean.client.model.SeanHttpResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/22 上午10:41 sean Exp $
 * @description
 */
public class HttpsRestTemplateClient implements RestTemplateClient, InitializingBean {

    private static Logger               logger = LoggerFactory.getLogger(HttpsRestTemplateClient.class);

    private static final List<Class<?>> clazzs = Lists.newArrayList();

    /** Spring rest template */
    private RestTemplate                restTemplate;

    static {
        clazzs.add(String.class);
        clazzs.add(Integer.class);
        clazzs.add(Long.class);
        clazzs.add(Double.class);
        clazzs.add(Float.class);
        clazzs.add(Short.class);
        clazzs.add(Character.class);
        clazzs.add(Byte.class);
    }

    public HttpsRestTemplateClient() {
        try {
            CloseableHttpClient httpClient = RestHttpClientUtil.acceptsUntrustedCertsHttpClient();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            restTemplate = new RestTemplate(clientHttpRequestFactory);
        } catch (KeyStoreException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        } catch (NoSuchAlgorithmException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        } catch (KeyManagementException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(restTemplate, "RestTemplate instance is required");
    }

    /**
     * @see RestTemplateClient#invoke(SeanHttpRequest, java.lang.Class)
     */
    @Override
    public <Request extends Serializable, Response extends Serializable> SeanHttpResponse<Response> invoke(SeanHttpRequest<Request> request,
                                                                                                           Class<Response> clazz) throws HttpInvokeException {
        validate(request);

        // Endpoint
        String url = request.getEndpoint();
        HttpMethod httpMethod = request.getRequestMethod();

        // Get http headers
        HttpHeaders httpHeaders = getHttpHeaders(request.getHeaders());

        // call remote method
        ResponseEntity<String> responseEntity = null;
        try {
            switch (request.getParameterType()) {
                case URL_PIAR: {
                    MultiValueMap<String, String> urlPairsParams = getUrlPairsParameters(request);
                    restTemplate.getForEntity(url,String.class,urlPairsParams);
                    break;
                }
                case URL_OBJECT: {
                    MultiValueMap<String, String> urlObjectParams = getUrlObjectParameters(request);
                    restTemplate.getForEntity(url,String.class,urlObjectParams);
                    break;
                }
                case BODY: {
                    String bodyRequestStr = getBodyParameters(request);
                    HttpEntity<String> httpEntity = new HttpEntity<String>(bodyRequestStr, httpHeaders);
                    ResponseExtractor<ResponseEntity<String>> responseExtractor = getResponseEntity(String.class, request.getrCharset());
//                    RequestCallback requestCallback = new HttpEntityRequestCallback(httpEntity, String.class);
//                    responseEntity = restTemplate.execute(url, httpMethod, requestCallback, responseExtractor);

                    break;
                }
                case REST: {
                    HttpEntity<String> httpEntity = new HttpEntity<String>(StringUtils.EMPTY, httpHeaders);
                    ResponseExtractor<ResponseEntity<String>> responseExtractor = getResponseEntity(String.class, request.getrCharset());
//                    RequestCallback requestCallback = new HttpEntityRequestCallback(httpEntity, String.class);
//                    responseEntity = restTemplate.execute(url, httpMethod, requestCallback, responseExtractor);
                    break;
                }
                default:
                    throw new HttpInvokeException("Parameter type is not supported, type=" + request.getParameterType().name());
            }
        } catch (Exception e) {
            throw new HttpInvokeException(e);
        }

        if (responseEntity == null) {
            throw new HttpInvokeException("Response is null");
        }

        switch (responseEntity.getStatusCode()) {
            case OK:
                break;
            case CREATED:
                break;
            case ACCEPTED:
                break;
            default:
                throw new HttpInvokeException("Response error, http error code: " + responseEntity.getStatusCode());
        }

        SeanHttpResponse<Response> response = createResponse(request, responseEntity, clazz);
        return response;
    }

    /**
     * 生成响应处理器
     *
     * @param type
     * @return
     */
    private ResponseExtractor<ResponseEntity<String>> getResponseEntity(Type type, String rcharsetStr) {
        Charset rcharset = null;
        if (!StringUtils.isBlank(rcharsetStr) && Charset.isSupported(rcharsetStr)) {
            rcharset = Charset.forName(rcharsetStr);
        }
//        ResponseEntityExtractor<String> responseExtractor = new ResponseEntityExtractor<String>(type, rcharset);
//        return responseExtractor;
        return null;
    }

    /**
     * 创建response
     *
     * @param responseEntity
     * @return
     */
    @SuppressWarnings("unchecked")
    private <Request extends Serializable, Response extends Serializable> SeanHttpResponse<Response> createResponse(SeanHttpRequest<Request> request,
                                                                                                                    ResponseEntity<String> responseEntity, Class<Response> clazz) {
        String responseBodyStr = responseEntity.getBody();

        if (responseBodyStr == null) {
            return null;
        }

        SeanHttpResponse<Response> response = new SeanHttpResponse<Response>();
        if (request.getResponseSerializeMethod() == null) {
            response.setResponse((Response) responseBodyStr);
        } else {
            Response responseObj = request.getResponseSerializeMethod().getSerializer().deserialize(responseBodyStr, clazz, request.getDateFormat());
            response.setResponse(responseObj);
        }
        response.getHeaders().putAll(responseEntity.getHeaders().toSingleValueMap());
        return response;
    }

    /**
     * 构建url key-value请求参数
     *
     * @param request
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private <Request extends Serializable> MultiValueMap<String, String> getUrlPairsParameters(SeanHttpRequest<Request> request) throws IllegalArgumentException,
                                                                                                                                 IllegalAccessException {
        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
        if (!clazzs.contains(request.getRequest().getClass())) {
            Map<String, Object> beanProps = convertBean(request.getRequest());
            for (Map.Entry<String, Object> beanProp : beanProps.entrySet()) {
                mvm.add(beanProp.getKey(), StringUtils.defaultIfBlank(String.valueOf(beanProp.getValue()), ""));
            }
        } else {
            mvm.add(request.getUrlObjectKey(), String.valueOf(request.getRequest()));
        }

        return mvm;
    }

    /**
     * 构建url key-value请求参数，参数为JSON对象，或XML对象
     *
     * @param request
     * @return
     */
    private <Request extends Serializable> MultiValueMap<String, String> getUrlObjectParameters(SeanHttpRequest<Request> request) {
        Assert.notNull(request.getRequestSerializeMethod(), "Request serializer is invalid");
        String requestParamStr = request.getRequestSerializeMethod().getSerializer().serialize(request.getRequest(), request.getDateFormat());
        String paramKey = request.getUrlObjectKey();
        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<String, String>();
        mvm.add(paramKey, requestParamStr);
        return mvm;
    }

    /**
     * 构建post body请求参数
     *
     * @param request
     * @return
     */
    private <Request extends Serializable> String getBodyParameters(SeanHttpRequest<Request> request) {
        Assert.notNull(request.getRequestSerializeMethod(), "Request serializer is invalid");
        String requestParamStr = request.getRequestSerializeMethod().getSerializer().serialize(request.getRequest(), request.getDateFormat());
        return requestParamStr;
    }

    /**
     * 转换bean为map类型
     *
     * @param bean
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private Map<String, Object> convertBean(Object bean) throws IllegalArgumentException, IllegalAccessException {
        Map<String, Object> params = Maps.newHashMap();

        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            if (Modifier.isTransient(modifiers)) {
                continue;
            }
            if (Modifier.isStatic(modifiers)) {
                continue;
            }
            if (Modifier.isVolatile(modifiers)) {
                continue;
            }

            if (field.isAnnotationPresent(JSONField.class)) {
                JSONField anno = field.getAnnotation(JSONField.class);
                String name = anno.name();
                Object value = field.get(bean);
                params.put(name, value);
            } else if (field.isAnnotationPresent(XStreamAlias.class)) {
                XStreamAlias anno = field.getAnnotation(XStreamAlias.class);
                String name = anno.value();
                Object value = field.get(bean);
                params.put(name, value);
            } else {
                String name = field.getName();
                Object value = field.get(bean);
                params.put(name, value);
            }
        }
        return params;
    }

    /**
     * 构建HTTP头信息
     *
     * @param httpHeaders
     * @return
     */
    private HttpHeaders getHttpHeaders(Map<String, String> httpHeaders) {
        HttpHeaders hh = new HttpHeaders();
        for (Map.Entry<String, String> header : httpHeaders.entrySet()) {
            hh.add(header.getKey(), header.getValue());
        }
        return hh;
    }

    /**
     * 入参校验
     *
     * @param request
     */
    private <Request extends Serializable> void validate(SeanHttpRequest<Request> request) {
        Assert.notNull(request, "Request is invalid");
        Assert.notNull(request.getRequest(), "Request parameter is required");
        Assert.isTrue(StringUtils.isNotBlank(request.getEndpoint()), "Endpoint url is required");
        Assert.notNull(request.getRequestMethod(), "Http request method is required");
        Assert.notNull(request.getParameterType(), "Request parameter type is required");
    }

}
