package com.xinxi.ergatebesh.common.OkHttp;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestUtils {


    private static final String TAG = "requestApi";
    public static String serverUrl = "http://47.105.145.222:8889/wpmapi";

    public static String token = null;
    public static String i18n = null;

    public static String requestAPI(String apiPath, String data) throws IOException {
        Log.i(TAG, "requestAPI request to send: " + serverUrl + apiPath + "\n" + "requestAPI data: " + data);
        OkHttpClient okHttpClient_post_json = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), data);

        Request.Builder builder = new Request.Builder().post(body).url(serverUrl + apiPath);
        // 请求“/login”接口的时候，带“Authorization”，会403.当url请求登录接口的时候，不带token
        if (token != null && !apiPath.equals("/login")) {
            builder.addHeader("Authorization", token);
        }
        builder.addHeader("i18n-lang", i18n);
        Request request = builder.build();
        Response response = okHttpClient_post_json.newCall(request).execute();

        return response.body().string();
    }

    public static String requestGetAPI(String apiPath, Map<String, Object> data) throws IOException {

        //创建对象
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5 * 1000, TimeUnit.SECONDS)
                .readTimeout(5 * 1000, TimeUnit.SECONDS)
                .writeTimeout(5 * 1000, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();//日志拦截器;
        StringBuilder stringBuffer = new StringBuilder("?");
        for (String key : data.keySet()) {
            stringBuffer.append(key);
            stringBuffer.append("=");
            stringBuffer.append(data.get(key));
            stringBuffer.append("&");
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        Log.i(TAG, "requestAPI request to send: " + serverUrl + apiPath + stringBuffer);
        //通过url新建Request对象
        Request request = new Request.Builder().get()
                .url(serverUrl + apiPath + stringBuffer)
                .addHeader("Authorization", token)
                .addHeader("i18n-lang", i18n)
                .build();
        //添加请求
        Response response = client.newCall(request).execute();
        //结果回调
        return response.body().string();
    }


    public static String myRequestGetAPI(String apiPath) throws IOException {

        //创建对象
        OkHttpClient client = new OkHttpClient();

        Log.i(TAG, "requestAPI request to send: " + serverUrl + apiPath);
        //通过url新建Request对象
        Request request = new Request.Builder().get()
                .url(serverUrl + apiPath )
                .addHeader("Authorization", token)
                .addHeader("i18n-lang", i18n)
                .build();
        //添加请求
        Response response = client.newCall(request).execute();
        //结果回调
        return response.body().string();
    }

    public static String requestAPI(String apiPath, Bundle data) throws IOException {
        JSONObject json = new JSONObject();
        Set<String> keys = data.keySet();
        for (String key : keys) {
            try {
                // json.put(key, bundle.get(key)); see edit below
                json.put(key, data.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return requestAPI(apiPath, json.toString());
    }

    public static String httpsPostImgRequest(File file) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                // 此处可添加上传 参数
                // photoFile 表示上传参数名,logo.png 表示图片名字
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))//文件
                .addFormDataPart("group","wpm")
                .build();

        Request.Builder builder = new Request.Builder();
        builder.url(serverUrl + ApiAddressCommon.api_upload)
                .post(requestBody);
        if (token != null) {
            builder.addHeader("Authorization", token);
        }
        Request request = builder.build();
        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }
    public static String httpsPostImgRequest(File file, String group, String name, String imagepath) throws IOException {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(5 * 1000, TimeUnit.MINUTES)
                .writeTimeout(5 * 1000, TimeUnit.MINUTES)
                .readTimeout(5 * 1000, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .build();
        RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                // 此处可添加上传 参数
                // photoFile 表示上传参数名,logo.png 表示图片名字
                .addFormDataPart("file", imagepath, image)
                .addFormDataPart("group",group)
                .addFormDataPart("name",name)
                .build();

        Request.Builder builder = new Request.Builder();
        builder.url(serverUrl + ApiAddressCommon.api_upload)
                .addHeader("i18n-lang", i18n)
                .post(requestBody);
        if (token != null) {
            builder.addHeader("Authorization", token);
        }
        Request request = builder.build();
        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }


}
