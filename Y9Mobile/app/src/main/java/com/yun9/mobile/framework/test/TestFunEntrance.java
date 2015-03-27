package com.yun9.mobile.framework.test;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.framework.base.activity.EnterActivity;

public class TestFunEntrance implements EnterActivity{

	@Override
	public void enter(Activity context, Map<String, Object> params) {
		Intent intent = new Intent(context, FunTestActivity.class);
		context.startActivity(intent);
	}

}
