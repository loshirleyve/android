package com.yun9.mobile.msg.action.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.yun9.mobile.msg.action.ExtraMenuHandler;

public class ActionContext implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> data;
	private ExtraMenuHandler handler;
	private ActionParams params;
	private Activity activity;
	private String label;
	
	public ActionContext(ActionParams params,ExtraMenuHandler handler,Activity activity) {
		this.setParams(params);
		this.setHandler(handler);
		this.setActivity(activity);
		this.data = new HashMap<String,Object>();
	}
	
	public ActionContext put(String key,Object value) {
		this.data.put(key, value);
		return this;
	}
	
	public Object get(String key) {
		return this.data.get(key);
	}

	public ExtraMenuHandler getHandler() {
		return handler;
	}

	public void setHandler(ExtraMenuHandler handler) {
		this.handler = handler;
	}

	public ActionParams getParams() {
		return params;
	}

	public void setParams(ActionParams params) {
		this.params = params;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
