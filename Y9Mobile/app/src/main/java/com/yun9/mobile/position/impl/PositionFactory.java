package com.yun9.mobile.position.impl;

import android.app.Activity;

public class PositionFactory {

	public static final int DEFAULT = 0;
	
	public static AcquirePosition createPosition(Activity activity){
		
		return createPosition(activity, DEFAULT);
	}
	
	
	public static AcquirePosition createPosition(Activity activity, int option){
		
		if(option == DEFAULT){
			return  new AcquirePosition(activity);
		}
		return null;
		
	}
}
