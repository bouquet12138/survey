package top.systemsec.survey.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by xiaohan on 2017/11/18.
 */

public class OkHttpUtil {

    private static final String TAG = "OkHttpUtil";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client =
                httpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback, int outTime) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client =
                httpBuilder.connectTimeout(outTime, TimeUnit.SECONDS)
                        .writeTimeout(outTime, TimeUnit.SECONDS)
                        .build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 封装好的用于提交表单的类
     *
     * @param address
     * @param keyAndValues
     * @param callback
     */
    public static void postOkHttpFormRequest(String address,
                                             Map<String, String> keyAndValues, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        if (keyAndValues != null) {
            Iterator iterator = keyAndValues.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                Log.d(TAG, "postOkHttpFormRequest: key " + key +" value " + value);
                if (value != null)
                    builder.add(key, value);
                else
                    builder.add(key, "");
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 上传图片
     */
    public static void postImage(String address, File file, okhttp3.Callback callback) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        requestBody.addFormDataPart("fileName", "hello.jpg", body);

        Request request = new Request.Builder()
                .url(address)
                .post(requestBody.build())
                .build();
        okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient =
                httpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 提交带文件的
     *
     * @param address
     * @param map
     * @param callback
     */
    public static void postForm(String address, Map<String, String> map, okhttp3.Callback callback) {

        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Log.d("OKHttpUtil", "postForm: " + entry.getKey() + "  " + entry.getValue());
                if (entry.getValue() != null)
                    requestBody.addFormDataPart(entry.getKey(), entry.getValue());
                else
                    requestBody.addFormDataPart(entry.getKey(), "");
            }
        }
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody.build())
                .build();
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient =
                httpBuilder.connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postJson(String address, String json, okhttp3.Callback callback) {
        postJson(address, json, callback, 5);
    }

    public static void postJson(String address, String json, okhttp3.Callback callback, int outTime) {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient =
                httpBuilder.connectTimeout(outTime, TimeUnit.SECONDS)
                        .writeTimeout(outTime, TimeUnit.SECONDS)
                        .build();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据
        RequestBody requestBody = RequestBody.create(JSON, json);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }


}
