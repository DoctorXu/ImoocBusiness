package com.doctor.libnetwork.okhttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Doctor on 2021/1/13.
 */

public class CommonRequest {

    /**
     * @param
     * @return
     * @desc 对外提供get/post/文件上传等请求
     * @author Administrator
     * @time 2021/1/13 23:05
     */
    public static Request createPostRequest(String url, RequestParams params) {
        return createPostRequest(url, params, null);
    }

    /**
     * @param
     * @return
     * @desc 对外提供get/post/文件上传等请求
     * @author Administrator
     * @time 2021/1/13 22:54
     */
    public static Request createPostRequest(String url, RequestParams params, RequestParams headers) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                // 参数遍历
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .headers(headersBuilder.build())
                .post(formBodyBuilder.build())
                .build();
        return request;
    }

    public static Request createGetRequest(String url, RequestParams params) {
        return createPostRequest(url, params, null);
    }

    /**
     * @param
     * @return
     * @desc 创建带请求头的get请求
     * @author Administrator
     * @time 2021/1/13 23:13
     */
    public static Request createGetRequest(String url, RequestParams params, RequestParams headers) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                // 参数遍历
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        Headers.Builder headersBuilder = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return new Request.Builder()
                .url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .headers(headersBuilder.build())
                .get()
                .build();
    }

    public static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    /**
     * @desc 文件上传请求
     * @author Administrator
     * @time 2021/1/13 23:37
     * @param 
     * @return 
     */
    public static Request createMultiPostRequest(String url, RequestParams params) {
        MultipartBody.Builder requestBody = new MultipartBody.Builder();
        requestBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    requestBody.addPart(Headers.of(
                            "Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    requestBody.addPart(Headers.of(
                            "Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        return new Request.Builder().url(url).post(requestBody.build()).build();
    }
}
