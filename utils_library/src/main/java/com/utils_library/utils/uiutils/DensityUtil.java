package com.utils_library.utils.uiutils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 分辨率有关
 */
public class DensityUtil {

	/**
	 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().density;
		
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}



	/**
	 * 获取屏幕宽度
	 *
	 * @param context
	 * @return
	 */
	public static int getFullScreenWidth(Context context) {
		if (context != null) {
			DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
			return displayMetrics.widthPixels;
		}
		return 0;
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param context
	 * @return
	 */
	public static int getFullScreenHeight(Context context) {
		if (context != null) {
			DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
			return displayMetrics.heightPixels;
		}
		return 400;
	}

}