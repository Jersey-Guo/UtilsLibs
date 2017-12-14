package com.utils_library.view.webview;

import android.webkit.WebView;

/**
 * Created by guojiadong
 * on 2017/12/14.
 */

public interface OnWebLoadListener {
    void onLoadErrorListener(WebView view, String url, int errorCode, String desciption);
    void onWebLoadTitle(WebView view, String title);
}
