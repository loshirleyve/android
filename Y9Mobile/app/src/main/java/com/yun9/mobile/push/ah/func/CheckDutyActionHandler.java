package com.yun9.mobile.push.ah.func;

import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;
import com.yun9.mobile.framework.activity.SignActivity;
import com.yun9.mobile.push.HandlerType;

public class CheckDutyActionHandler extends AbsFuncPushMessageActionHandler {

	@Override
	public void handle(Context context, JsonObject actionContext) {
		Intent intent = new Intent(context,
				SignActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		context.startActivity(intent);
	}

	@Override
	public HandlerType getHandlerType() {
		return HandlerType.FUNC_CHECK_DUTY;
	}

}
