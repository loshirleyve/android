package com.yun9.mobile.push;

import android.content.Context;

import com.google.gson.JsonObject;

public interface PushMessageActionHandler {
	
	String BASE_PARAM_TYPE = "type";
	String BASE_PARAM_VALUE = "value";
	void handle(Context context,JsonObject actionContext);
	
	HandlerType getHandlerType();

}
