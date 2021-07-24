package com.xinxi.ergatebesh.common.OkHttp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/*
* request拦截   与   response拦截
*
* 此处仅做了框架中登录超时处理，其余处理，请自行添加
* */
public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();


        long t1 = System.nanoTime();//请求发起的时间
        Log.i("dt", String.format("发送请求 %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间


        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);

        Log.i("dt", String.format("接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers()));

        // 获取response数据
        BufferedSource source = response.body().source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        String result = buffer.clone().readString(charset);

        //修改response数据
        try {
            // 如果返回的是“401”，则提示登录超时，请重新登录
            JSONObject responseBodyJson = new JSONObject(result);
            if("401".equals(responseBodyJson.getString("code"))){
                String replace = result.replaceAll(responseBodyJson.getString("msg"), "登录超时，请重新登录");
                Response build = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), replace)).build();
                return build;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }
}