package com.sean.client;

import com.google.common.base.Throwables;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * @author sean
 * @version Id:,v0.1 2018/8/21 上午11:58 sean Exp $
 * @description
 */
public class ClientTest {

    private static Logger logger = LoggerFactory.getLogger(ClientTest.class);

    public static void main(String[] args) {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = RestHttpClientUtil.acceptsUntrustedCertsHttpClient();
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
            String result = restTemplate.getForObject("http://www.baidu.com", String.class);
            System.out.println(result);
        } catch (KeyStoreException e) {

            logger.error(Throwables.getStackTraceAsString(e));
        } catch (NoSuchAlgorithmException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        } catch (KeyManagementException e) {
            logger.error(Throwables.getStackTraceAsString(e));
        }

    }
}
