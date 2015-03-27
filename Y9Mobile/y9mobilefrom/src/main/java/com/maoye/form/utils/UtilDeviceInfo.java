package com.maoye.form.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class UtilDeviceInfo {

	/**
	 * @param activity
	 * @return 屏幕像素高度
	 */
	public static int getDeviceHeightPixels(Context context){
		
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height =  windowManager.getDefaultDisplay().getHeight();;   // 屏幕高度（像素）
		return height;
	}
	
}
