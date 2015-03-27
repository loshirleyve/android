package com.yun9.mobile.msg.action.entity;

import com.google.gson.JsonObject;

public class ActionValues extends JsonParam {
	
	public ActionValues(String json) {
		super(json);
	}

	public ActionValues(JsonObject element) {
		super(element);
	}

	private static final long serialVersionUID = 1L;

}
