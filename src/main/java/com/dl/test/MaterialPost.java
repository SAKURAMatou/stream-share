package com.dl.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MaterialPost {
    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJoeXl5VmVyc2lvbiI6IjAiLCJkb21haW5OYW1lIjoiaGQiLCJpc3MiOiJjbW9zIiwibG9naW5Db2RlIjoiMTg3OTI0OTQ3MDEiLCJleHAiOjE2MjU4ODUzMzUsInVzZXJJZCI6ImMwOWVhZDNlZjY3OTQ1NTc5ZTc4OGZmNGQ4ZDQ0OTYyIn0.1r7WYOvefCp8ivAPFpmdzlOvXGcSU3pJUTSeI2CWcDk";

    public static void main(String[] args) {
        MaterialPost materialPost = new MaterialPost();
        byte[] aByte = materialPost.getByte();
        String url = "https://api.deskpro.cn/openApi/file/filemanage/uploadFile/scces";
        String resStr = materialPost.doPostMaterial(url, null, aByte, "12367对接参数.txt");
        System.out.println(resStr);
    }

    public String doPostMaterial(String url, JSONObject params, byte[] file, String filename) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            // 多表单请求
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            // 20210118 chenyun 设置请求mode，解决中文名称文件乱码问题
            entityBuilder.setMode(HttpMultipartMode.RFC6532);
            // 基本数据信息
            if (params != null) {
                entityBuilder.addTextBody("params", params.toJSONString(),
                        ContentType.create("text/plain", Consts.UTF_8));
            }
            // 附件材料信息
            if (file != null) {
                entityBuilder.addBinaryBody("file", file, ContentType.DEFAULT_BINARY, filename);
            }
            HttpEntity reqEntity = entityBuilder.build();

            httpPost.setEntity(reqEntity);

            BasicHeader authorization = new BasicHeader("Authorization", token);
            httpPost.addHeader(authorization);
            response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, Consts.UTF_8);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public byte[] getByte() {
        FileInputStream fileInputStream = null;
        byte[] fileByte = null;
        try {
            File f = new File("C:\\Users\\daili\\Desktop\\12367对接参数.txt");
            fileInputStream = new FileInputStream(f);
            StringBuilder bf = new StringBuilder();
            while (fileInputStream.read() != -1) {
                bf.append(fileInputStream.read());
            }
            fileByte = bf.toString().getBytes();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileByte;
    }
}
