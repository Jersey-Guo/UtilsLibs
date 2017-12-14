package com.utils_library.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.utils_library.R;
import com.utils_library.activity.ActivityInstanceRef;
import com.utils_library.utils.uiutils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guojiadong
 * on 2017/8/29.
 */

@SuppressWarnings("deprecation")
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public ImageView iv_back;
    public TextView tv_title;
    public TextView tv_menu;
    public ImageView iv_menu;
    public RelativeLayout title_rootview;
    public View baseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = setContentView(savedInstanceState);
        if (layoutId != 0) {
            setContentView(layoutId);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setTitleBarColorWhite();
        initView();
        initData();
    }

    public void setTitleBarColorWhite(){
        setTitleBarColor(getResources().getColor(R.color.white));
    }

    public void setTitleBarColorPrimay(){
        setTitleBarColor(getResources().getColor(R.color.colorPrimary));
    }
    private void setTitleBarColor(int color) {
        StatusBarUtil.setColor(this, color, 50);

    }


    public void initTitleView(boolean isVisibleForTitle) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_menu = (TextView) findViewById(R.id.tv_menu);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        title_rootview = (RelativeLayout) findViewById(R.id.layout_title_rootview);
        titleBarIsVisible(isVisibleForTitle);
    }

    public void showIvBack() {
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(this);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }


    private void titleBarIsVisible(boolean isVisible) {
        if (isVisible) {
            title_rootview.setVisibility(View.VISIBLE);
        } else {
            title_rootview.setVisibility(View.GONE);
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 50);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityInstanceRef.setCurActivity(this);
    }


    public abstract void initView();

    public abstract void initData();

    public abstract int setContentView(Bundle savedInstanceState);

    public void getIntentValue() {

    }

    public void setClickListener(){

    }

    /**
     * 直接提示文字
     *
     * @param msg 文字
     */
    public final void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    /**
     * 文字id
     *
     * @param id 文字资源ID
     */
    public final void showToast(int id) {
        ToastUtil.showToast(this, getResources().getString(id));
    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    private final int WLC_REQUEST_PERMISSION_CODE = 10521;
    private PermissionsResultListener mListener;

    /**
     * 检查注册权限的方法
     *
     * @param activity   Activity
     * @param permission 需要的注册的权限 , 可变长参数
     */
    public void checkPermissions(Activity activity, PermissionsResultListener resultListener, String... permission) {
        this.mListener = resultListener;
        List<String> denyPermissions = null;
        //获取没有注册的权限
        for (String value : permission) {
            if (ContextCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                if (denyPermissions == null) denyPermissions = new ArrayList<>();
                denyPermissions.add(value);
            }
        }

        //判断是否存在没注册的权限
        if (denyPermissions == null) {
            //权限都注册了
            mListener.onSuccessful();
        } else {
            //系统弹框 申请权限
            ActivityCompat.requestPermissions(activity, denyPermissions.toArray(new String[]{}), WLC_REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * 在Activity中 重写onRequestPermissionsResult方法 中 调用 本对象的 onRequestPermissionsResult方法
     *
     * @param requestCode  请求码   从父回调方法 获取
     * @param permissions  注册的权限   从父回调方法 获取
     * @param grantResults 注册权限的结果  从父回调方法 获取
     */
    public void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断很刚才请求码 是否一致
        if (requestCode == WLC_REQUEST_PERMISSION_CODE) {
            ArrayList<String> denialPermissions = null;
            //收集被拒接的权限
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (denialPermissions == null) denialPermissions = new ArrayList<>();
                    denialPermissions.add(permissions[i]);
                }
            }
            //判断 是否有拒绝的权限
            if (denialPermissions == null) {
                if (mListener != null) {
                    mListener.onSuccessful();
                }
            } else {
                ArrayList<String> neverAskPermission = null;
                //判断是否 点了 不再询问
                for (String denyPermission : denialPermissions) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, denyPermission)) {
                        //跳转去到设置权限页面

                        if (neverAskPermission == null) neverAskPermission = new ArrayList<>();

                        neverAskPermission.add(denyPermission);
                    }
                }
                if (neverAskPermission == null) {
                    if (mListener != null) {
                        mListener.onFailure();
                    }
                    denialPermissions(denialPermissions);
                } else {
                    if (mListener != null) {
                        mListener.onFailure();
                    }
                    goTOSetPermissionActivity(neverAskPermission);
                }
            }
        }

    }

    /**
     * 用户权限 点了 不再询问 , 只能跳转 到 系统的设置权限页面由 用户 自己 打开权限
     * 在本方法里 填写 是否跳转 到 设置权限页面 .
     *
     * @param neverAskPermission 被点不再询问的权限 集合
     */
    public void goTOSetPermissionActivity(List<String> neverAskPermission) {
    }


    /**
     * 注册权限被拒绝
     *
     * @param denialPermissions 被拒绝的权限 集合
     */
    public void denialPermissions(List<String> denialPermissions) {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_back) {
            finish();
        }
    }

    public interface PermissionsResultListener {

        //成功
        void onSuccessful();

        //失败
        void onFailure();
    }

}
