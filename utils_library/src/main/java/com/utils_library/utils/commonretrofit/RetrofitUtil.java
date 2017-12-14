package com.utils_library.utils.commonretrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.utils_library.utils.commonutil.GetPhoneInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by guojiadong
 * 此类有需要重载的可能性所以写为单例模式
 * 也可写为静态方法的调用
 * on 2017/6/29.
 */

public class RetrofitUtil {
    private static final String BASE_URL = "http://japi.juhe.cn/joke/content/";

    private static class InstanceHolde {
        static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }

    public static RetrofitUtil getIntance() {
        return InstanceHolde.INSTANCE;
    }

    private RetrofitUtil() {

    }

    public  Retrofit getRetrofit(final Context context, String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = BASE_URL;
        }
        //初始化retrofit
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //添加请求头
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("mac", GetPhoneInfo.getWifiMac(context))
                                .addHeader("uuid", GetPhoneInfo.Installtion_ID(context))
                                .build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(5*1000, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
