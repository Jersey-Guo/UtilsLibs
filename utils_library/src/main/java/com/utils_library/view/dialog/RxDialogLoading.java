package com.utils_library.view.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.utils_library.R;
import com.utils_library.utils.uiutils.ToastUtil;
import com.utils_library.view.progressbar.SpinKitView;


public class RxDialogLoading extends RxDialog {

    private SpinKitView mLoadingView;
    private View mDialogContentView;
    private TextView mTextView;
    private Context mContext;


    public RxDialogLoading(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public RxDialogLoading(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    public RxDialogLoading(Context context) {
        super(context);
        initView(context);
    }

    public RxDialogLoading(Activity context) {
        super(context);
        initView(context);
    }

    public RxDialogLoading(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        mDialogContentView = LayoutInflater.from(context).inflate(R.layout.dialog_loading_spinkit, null);
        mLoadingView = (SpinKitView) mDialogContentView.findViewById(R.id.spin_kit);
        mTextView = (TextView) mDialogContentView.findViewById(R.id.name);
        setContentView(mDialogContentView);
    }

    public void setLoadingText(CharSequence charSequence) {
        mTextView.setText(charSequence);
    }

    public void setLoadingColor(int color){
        mLoadingView.setColor(color);
    }

    public void cancel(cancelType code, String str) {
        cancel();
        switch (code) {
            case normal:
                ToastUtil.showToast(mContext,str);
                break;
            case error:
                ToastUtil.showToast(mContext,str);
                break;
            case success:
                ToastUtil.showToast(mContext,str);
                break;
            case info:
                ToastUtil.showToast(mContext,str);
                break;
            default:
                ToastUtil.showToast(mContext,str);
                break;
        }
    }

    public void cancel(String str) {
        cancel();
        ToastUtil.showToast(mContext,str);
    }

    public SpinKitView getLoadingView() {
        return mLoadingView;
    }

    public View getDialogContentView() {
        return mDialogContentView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    private enum cancelType {normal, error, success, info}
}
