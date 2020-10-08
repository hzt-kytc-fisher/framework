/*
 * Copyright 2020 tuhu.cn All right reserved. This software is the
 * confidential and proprietary information of tuhu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tuhu.cn
 */
package com.kytc.framework.http;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.kytc.framework.exception.BaseErrorCodeEnum;
import com.kytc.framework.exception.BaseException;
import okhttp3.*;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.*;

/**
 * @Author: 何志同
 * @Date: 2020/9/28 9:31
 * @Description:
 **/
public class OkHttpUtils {
    public static String get(String url) {
        return get(url,null);
    }
    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }
    private static OkHttpClient init(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().hostnameVerifier(
                new HostnameVerifier(){
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }
        ).sslSocketFactory(createSSLSocketFactory(),new TrustAllCerts()).build();
        /*okHttpClient.dispatcher().setMaxRequests(100);
        okHttpClient.dispatcher().setMaxRequestsPerHost(10);*/
        return okHttpClient;
    }
    public static String get(String url, Map<String,String> headers) {
        OkHttpClient okHttpClient = init();
        final Request request = new Request.Builder()
                .url(url)
                .headers(initHeaders(headers))
                .build();
        final Call call = okHttpClient.newCall(request);
        return execute(call);
    }
    public static String delete(String url,Map<String,String> headers) {
        OkHttpClient okHttpClient = init();
        final Request request = new Request.Builder()
                .url(url)
                .delete()
                .headers(initHeaders(headers))
                .build();
        final Call call = okHttpClient.newCall(request);
        return execute(call);
    }
    private static Headers initHeaders(Map<String,String> headers){
        Headers.Builder builder = new Headers.Builder();
        if(!CollectionUtils.isEmpty(headers)){
            for(Map.Entry<String,String> header:headers.entrySet()){
                builder.add(header.getKey(),header.getValue());
            }
        }
        builder.add("content-type","application/json");
        builder.add("Content-Type","application/json");
        builder.add("Accept","application/json");
        return builder.build();
    }
    public static String put(String url,Map<String,String> headers) {

        return put(url,null,headers);
    }

    public static String put(String url,Object objs,Map<String,String> headers) {
        OkHttpClient okHttpClient = init();
        final Request request = new Request.Builder()
                .url(url)
                .put(RequestBody.create(MediaType.parse("application/json"), null == objs?"{}": JSON.toJSONString(objs)))
                .headers(initHeaders(headers))
                .build();
        final Call call = okHttpClient.newCall(request);
        return execute(call);
    }

    public static String post(String url, Object obj, Map<String,String> headers) {
        OkHttpClient okHttpClient = init();
        Headers headers1 = initHeaders(headers);
        String params = null;
        if(obj instanceof String){
            params = (String)obj;
        }else{
            params = JSON.toJSONString(obj);
        }
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), params))
                .headers(headers1)
                .build();
        final Call call = okHttpClient.newCall(request);
        return execute(call);
    }

    public static String patch(String url, Object obj, Map<String,String> headers) {
        OkHttpClient okHttpClient = init();
        Headers headers1 = initHeaders(headers);
        String params = null;
        if(obj instanceof String){
            params = (String)obj;
        }else{
            params = JSON.toJSONString(obj);
        }
        final Request request = new Request.Builder()
                .url(url)
                .patch(RequestBody.create(MediaType.parse("application/json"), params))
                .headers(headers1)
                .build();
        final Call call = okHttpClient.newCall(request);
        return execute(call);
    }

    public static String post(String url, Object obj){
        return post(url,obj,null);
    }

    private static String execute(Call call){
        Response response = null;
        try {
            response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            throw new BaseException(BaseErrorCodeEnum.HTTP_REQUEST_FAILED,e);
        } finally {
            if( null != response ){
                response.close();
            }
        }
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("Authorization","token 518e11229c6ceded2918a80f65120e7bff0fa1e9");
        //map.put("Accept","application/vnd.github.inertia-preview+json");
        map.put("Accept","application/vnd.github.v3+json");
        /*String s = get("https://api.github.com/repos/i79627313/email/branches",map);
        System.out.println(s);*/
        Map<String,Object> data = new HashMap<>();
        data.put("name","haha");
        data.put("description","开心呀");
        String s1 = patch("https://199.232.69.194/repos/hzt-kytc/test",data,map);
        System.out.println(s1);
    }
}