package com.demowork;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jph.takephoto.app.TakePhoto;
import com.utils_library.appupdate.UpdateUtil;

/**
 * Created by guojiadong
 * on 2017/8/28.
 */

public class TakePhotoTest extends FragmentActivity implements View.OnClickListener {
    private TakePhoto takePhoto;
    private ImageView showImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);
        Button camera = (Button) findViewById(R.id.activity_imge_camera);
        camera.setOnClickListener(this);
        Button gralley = (Button) findViewById(R.id.activity_imge_grally);
        gralley.setOnClickListener(this);
        showImg = (ImageView) findViewById(R.id.activity_image_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_imge_camera:
                UpdateUtil.chackUpdate(TakePhotoTest.this);
                break;
            case R.id.activity_imge_grally:
                takePhoto.onPickFromGallery();
                break;
        }
    }
}
