package com.peakmain.asmactualcombat.utils;


import android.webkit.WebSettings;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.peakmain.asmactualcombat.App;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author ：Peakmain
 * createTime：2022/5/16
 * mail:2726449200@qq.com
 * describe：
 */
public class RetrofitUtils {
    private static Retrofit retrofit = null;
    private static Map<Class, Object> services = new ConcurrentHashMap<>();

    public static Retrofit getInstance() {

        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(addQueryParameterInterceptor());
            //设置超时
            builder.connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS);

            OkHttpClient client = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.wanandroid.com/")
                    //增加返回值为Gson的支持(以实体类返回)

                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().
                            registerTypeAdapter(String.class, new TypeAdapter<String>() {
                                @Override
                                public void write(JsonWriter writer, String value) throws IOException {
                                    if (value == null) {
                                        // 在这里处理null改为空字符串
                                        writer.value("");
                                        return;
                                    }
                                    writer.value(value);

                                }

                                @Override
                                public String read(JsonReader reader) throws IOException {
                                    if (reader.peek() == JsonToken.NULL) {
                                        reader.nextNull();
                                        return "";
                                    }
                                    return reader.nextString();

                                }
                            }).create()))
                    //增加返回值为Oservable<T>的支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }


    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> service) {
        if (services.get(service) == null) {
            services.put(service, getInstance().create(service));
        }
        return (T) services.get(service);
    }

    public static void changeApiBaseUrl() {
        retrofit = null;
        services.clear();
    }

    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        return chain -> {
            Request originalRequest = chain.request();
            HttpUrl.Builder modifiedUrl = originalRequest.url()
                    .newBuilder();


            Request newRequest = originalRequest.newBuilder()
                    .url(modifiedUrl.build()).removeHeader("User-Agent").addHeader("User-Agent",
                            getUserAgent()).build();
            return chain.proceed(newRequest);
        };
    }

    private static String getUserAgent() {
        String userAgent;
        try {
            userAgent = WebSettings.getDefaultUserAgent(App.getApp());
        } catch (Exception e) {
            userAgent = System.getProperty("http.agent");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
