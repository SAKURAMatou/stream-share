package com.dl.test;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试发送有请求头的请求
 */
public class SendPostWithToken {

    public String sendWithToken1(String url, String param, BasicHeader header) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String res = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);
            if (header != null) {
                httpPost.setHeader(header);
            }
            StringEntity myEntity = new StringEntity(param, "UTF-8");
            myEntity.setContentType("application/json;charset=UTF-8");
            httpPost.setEntity(myEntity);
            response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                res = EntityUtils.toString(entity, "UTF-8");

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static void main(String[] args) {
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJoeXl5VmVyc2lvbiI6IjAiLCJkb21haW5OYW1lIjoiaGQiLCJpc3MiOiJjbW9zIiwibG9naW5Db2RlIjoiMTg3OTI0OTQ3MDEiLCJleHAiOjE2MjYwOTE0MTIsInVzZXJJZCI6ImMwOWVhZDNlZjY3OTQ1NTc5ZTc4OGZmNGQ4ZDQ0OTYyIn0.MeqQHg9lRivN6eTPHFnyTbsoKZX98_C9HuEJQIrL3IA";
    Pattern patternCaseserial = Pattern.compile(
            "^(DH|ZX|WZ|WX|AP|WB|YJ|DX|MP|QT|DS|DC|SZ|ZW|PB|GP|RL|GD|GN|RX|WY|CT)((1|2|3|5|6|7|8|9)[0-9])((61)\\d{4})((\\d{2})((0[1-9])|(1[0|1|2]))([0-3][0-9]))(\\d{5})$");
    Matcher matcher = patternCaseserial.matcher("DH9361070021062800022");
    System.out.println("匹配结果："+matcher.find());
}
}
