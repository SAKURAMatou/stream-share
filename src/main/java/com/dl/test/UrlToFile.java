package com.dl.test;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class UrlToFile {
    private String DownLoadUrl;

    private String fileName;


    private long fileStreamSize;

    private InputStream fileInputStream;

    private void getFileStreamByUrl(String urlStr) {
        this.DownLoadUrl = urlStr;
        try {
            // 统一资源
            URL url = new URL(DownLoadUrl);
//            if ("https".equalsIgnoreCase(url.getProtocol())) {
//                SslUtils.ignoreSsl();
//            }
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设置超时
            httpURLConnection.setConnectTimeout(1000 * 3);
            // 设置请求方式，默认是GET
//            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();
            // 文件大小
            this.fileStreamSize = httpURLConnection.getContentLength();
            String contentType = httpURLConnection.getContentType();
            System.out.println("contentType:"+contentType);
            this.fileInputStream = httpURLConnection.getInputStream();
            String filetype = URLConnection.guessContentTypeFromStream(fileInputStream);
            System.out.println("type:"+filetype);
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String ces = fileNameMap.getContentTypeFor("ces");
            System.out.println("ces:"+ces);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        UrlToFile urlToFile = new UrlToFile();
        String testUrl = "https://12367.pointlinkpro.com/sckm/item/downloadFileById?itemFileId=bd1aa7c8f62144c5ad851582fde08a0e";
        urlToFile.getFileStreamByUrl(testUrl);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        String[] split = testUrl.split("/");
//        Arrays.stream(split).forEach(System.out::println);
    }

    public String getDownLoadUrl() {
        return DownLoadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileStreamSize() {
        return fileStreamSize;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }
}
