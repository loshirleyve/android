package com.yun9.mobile.serviceorder.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.framework.base.activity.EnterActivity;

public class ServiceOrderActivityEnter implements EnterActivity {

	public Activity context ;
	private Map<String, Object> params;
	
	@Override
	public void enter(Activity context, Map<String, Object> params) {
		this.context= context;
		this.params = params;
		
		Intent intent = new Intent(context, ServiceOrderActivity.class);
		context.startActivity(intent);
	}

}
