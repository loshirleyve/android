package com.yun9.mobile.msg.action.entity;

import com.google.gson.JsonObject;

public class ActionData extends JsonParam {

	public ActionData(String json) {
		super(json);
	}

	public ActionData(JsonObject element) {
		super(element);
	}

	private static final long serialVersionUID = 1L;

}
