package com.yun9.mobile.msg.action.entity;

import com.google.gson.JsonObject;

public class ActionHeader extends JsonParam {

	public ActionHeader(JsonObject element) {
		super(element);
	}
	
	public String getActionName() {
		return getString("actionName");
	}
	
	public String getBpmnInstId() {
		return getString("bpmnInstId");
		}
	
	public String getNodeName() {
		return getString("nodeName");
		}
	
	public String getTaskId() {
		return getString("taskId");
		}

	private static final long serialVersionUID = 1L;

}
