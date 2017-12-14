package com.utils_library.view.webview;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;

/**
 * Created by guojiadong
 * on 2017/12/14.
 */

public interface JsCallBackListener {
    void jsCallBack(String value);

    void onJsAlert(WebView view, String url, String message, JsResult result);

    void onJsConfirm(WebView view, String url, String message, JsResult result);

    void onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);
}
