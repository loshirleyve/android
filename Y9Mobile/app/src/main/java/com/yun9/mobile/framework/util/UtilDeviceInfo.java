package com.yun9.mobile.framework.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class UtilDeviceInfo {

	/**
	 * @param activity
	 * @return 屏幕像素高度
	 */
	public static int getDeviceHeightPixels(Activity activity){
    	DisplayMetrics metric = new DisplayMetrics();
    	activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;   // 屏幕高度（像素）
		return height;
	}
	
	/**
	 * @param activity
	 * @return 屏幕像素宽度
	 */
	public static int getDeviceWidthPixels(Activity activity){
    	DisplayMetrics metric = new DisplayMetrics();
    	activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
		return width;
	}
	
	
	
	
	
	/**
	 * @param activity
	 * @return 屏幕像素高度
	 */
	public static int getDeviceHeightPixels(Context context){
		
		
		
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height =  windowManager.getDefaultDisplay().getHeight();;   // 屏幕高度（像素）
		return height;
	}
	
	/**
	 * @param activity
	 * @return 屏幕像素宽度
	 */
	public static int getDeviceWidthPixels(Context context){
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width =  windowManager.getDefaultDisplay().getWidth();     // 屏幕宽度（像素）
		return width;
	}
	
	
	
	
	
	
}
