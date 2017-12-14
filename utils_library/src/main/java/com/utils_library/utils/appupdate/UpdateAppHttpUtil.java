package com.utils_library.utils.appupdate;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONException;
import com.google.gson.Gson;
import com.utils_library.BuildConfig;
import com.utils_library.utils.commonutil.LogUtil;
import com.vector.update_app.HttpManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.intercepter.NoNetWorkException;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class UpdateAppHttpUtil implements HttpManager {
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 异步get
     *
     * @param url      get请求地址
     * @param params   get参数
     * @param callBack 回调
     */
    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        OkHttpUtils.get()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        callBack.onError(validateError(e, response));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callBack.onResponse(response);
                    }
                });
    }

    /**
     * 异步post
     *
     * @param url      post请求地址
     * @param params   post请求参数
     * @param callBack 回调
     */
    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        OkHttpClient mClient = new OkHttpClient();
        Request request = addHeaders(url).url(url).post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(params))).build();
        mClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(validateError(e, null));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    String responseStr = "";
                    if (body != null) {
                        responseStr = body.string();
                    }
                    LogUtil.e(BuildConfig.DEBUG, "onRequestResult---" + responseStr);
                    callBack.onResponse(responseStr);
                } else {
                    callBack.onError(validateError(null, response));
                }

            }
        });

    }

    protected String validateError(Exception error, Response response) {

        if (error != null) {
            if (error instanceof NoNetWorkException) {
                return "无网络，请联网重试";
            } else if (error instanceof SocketTimeoutException) {
                return "网络连接超时，请稍候重试";
            } else if (error instanceof JSONException) {
                return "json转化异常";
            } else if (error instanceof ConnectException) {
                return "服务器网络异常或宕机，请稍候重试";
            }
        }


        if (response != null) {
            int code = response.code();
            if (code >= 500) {
                return "服务器异常，请稍候重试";
            } else if (code < 500 && code >= 400) {
                return "接口异常，请稍候重试";
            } else {
                return String.format("未知异常 code = %d，请稍候重试", code);
            }
        }


        return "未知异常，请稍候重试";

    }


    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final FileCallback callback) {
        if (TextUtils.isEmpty(url)) {
            callback.onError("下载地址有误");
            return;
        } else {
            if (!url.contains("http")) {
                callback.onError("下载地址有误");
                return;
            }
        }
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(path, fileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        callback.onProgress(progress, total);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        callback.onError(validateError(e, response));
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        callback.onResponse(response);

                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        callback.onBefore();
                    }
                });

    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders(String tag) {
        Request.Builder builder = new Request.Builder()
                .tag(tag);
        return builder;
    }
}