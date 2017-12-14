package com.utils_library.view.webview;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by guojiadong
 * on 2017/12/14.
 */

public class CustomWebChromClient extends WebChromeClient {
    private OnPageLoadListener mOnPageLoadListener;
    private JsCallBackListener mOnJsCallBack;

    public void setmOnPageLoadListener(OnPageLoadListener onPageLoadListener) {
        this.mOnPageLoadListener = onPageLoadListener;
    }

    public void setmOnJsCallBack(JsCallBackListener mOnJsCallBack) {
        this.mOnJsCallBack = mOnJsCallBack;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if(mOnPageLoadListener != null){
            mOnPageLoadListener.onProgressChanged(view,newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if(mOnPageLoadListener != null){
            mOnPageLoadListener.onReceiveTitle(view,title);
        }
    }


    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        if(mOnJsCallBack != null){
            mOnJsCallBack.onJsAlert(view,url,message,result);
        }
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        if(mOnJsCallBack != null){
            mOnJsCallBack.onJsConfirm(view,url,message,result);
        }
        return true;
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if(mOnJsCallBack != null){
            mOnJsCallBack.onJsPrompt(view,url,message,defaultValue,result);
        }
        return true;
    }
}
