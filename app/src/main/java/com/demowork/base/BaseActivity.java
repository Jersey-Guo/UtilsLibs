package com.demowork.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.utils_library.activity.ActivityInstanceRef;

/**
 * Created by guojiadong
 * on 2017/6/29.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public abstract int setContentView(Bundle savedInstanceState);
    public abstract void initView();
    public abstract void setModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutId = setContentView(savedInstanceState);
        if(layoutId != 0){
            setContentView(layoutId);
            initView();
            setModel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityInstanceRef.setCurActivity(this);
    }

}
