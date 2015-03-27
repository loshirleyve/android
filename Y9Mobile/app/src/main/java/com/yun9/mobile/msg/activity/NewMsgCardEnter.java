package com.yun9.mobile.msg.activity;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.framework.base.activity.EnterActivity;

public class NewMsgCardEnter implements EnterActivity {
	
	@Override
	public void enter(Activity context, Map<String, Object> params) {
		Intent intent = new Intent(context, NewMsgCard.class);
		context.startActivity(intent);
	}

}
