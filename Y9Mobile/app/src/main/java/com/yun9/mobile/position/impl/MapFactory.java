package com.yun9.mobile.position.impl;

import com.yun9.mobile.position.iface.Map;

import android.app.Activity;

public class MapFactory{
	public static final int DEFAULT = 0;
	
	public static Map createMap(Activity activity){
		
		return createMap(activity, DEFAULT);
	}
	
	
	public static Map createMap(Activity activity, int option){
		
//		if(option == DEFAULT){
//			return  new AcquirePosition(activity);
//		}
		return null;
		
	}
}
