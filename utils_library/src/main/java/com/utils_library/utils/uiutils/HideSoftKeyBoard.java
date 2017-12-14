package com.utils_library.utils.uiutils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘控制类
 */
public class HideSoftKeyBoard {
	public static void hideSoftKeyboard(Context context) {
		try {
			Activity act = (Activity) context;
			if (act.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
				if (act.getCurrentFocus() != null) {
					((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(act
							.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
				// closeBoard(context);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeBoard(Context mcontext) {
		InputMethodManager imm = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
		if (imm.isActive()) // 一直是true
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void hideSystemKeyBoard(Context mcontext, View v) {
		InputMethodManager imm = (InputMethodManager) ((Activity) mcontext).getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
}
