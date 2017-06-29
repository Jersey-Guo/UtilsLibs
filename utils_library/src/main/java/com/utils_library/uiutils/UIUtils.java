package com.utils_library.uiutils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by guojiadong
 * on 2016/11/24.
 */

public class UIUtils {

    /**
     * 底部弹出pop
     *
     * @param activity
     * @param contentView
     * @param parent
     * @param pop
     * @return
     */
    public static PopupWindow showBottomPopup(final Activity activity, final View contentView, final View parent,
                                              PopupWindow pop) {
        if (activity == null || contentView == null || parent == null) {
            return null;
        }
        if (pop == null) {
            pop = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);
        }
        pop.setContentView(contentView);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        //动画效果自己设置
//        pop.setAnimationStyle();
        pop.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                UIUtils.setBackgroundAlpha(activity.getWindow(), 1);
            }
        });
        return pop;
    }

    /**
     * 中间弹出PopupWindow.
     *
     * @param activity
     * @param contentView 内容布局。
     * @param parent      a parent view to get the {@link View#getWindowToken()} token from
     * @param pop         可复用的PopupWindow对象。
     * @return
     * @since 2015年6月19日
     */
    @SuppressWarnings("deprecation")
    public static PopupWindow showCenterPopup(final Activity activity, int width, int heigth, final View contentView, final View parent,
                                              PopupWindow pop) {
        if (activity == null || contentView == null || parent == null) {
            return null;
        }
        if (pop == null) {
            pop = new PopupWindow(contentView, width, heigth,
                    true);
        }
        pop.setContentView(contentView);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        //动画效果自己设置
//        pop.setAnimationStyle();
        pop.showAtLocation(parent, Gravity.CENTER, 0, 0);
        UIUtils.setBackgroundAlpha(activity.getWindow(), 0.6f);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                UIUtils.setBackgroundAlpha(activity.getWindow(), 1);
            }
        });
        return pop;
    }


    /**
     * 设置Activity的窗口透明度
     *
     * @param window {@link Window} ，如 Activity.getWindow()
     * @param alpha  透明度。1f - 无遮挡； 0f - 全黑 ； 0f~1f - 窗口变暗
     */
    public static void setBackgroundAlpha(Window window, float alpha) {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        if (alpha == 1) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        window.setAttributes(attributes);
    }





    /**
     * 裁剪图片
     *
     * @param bm        位图
     * @param newWidth  新图宽度
     * @param newHeight 新图高度
     * @return
     */
    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
