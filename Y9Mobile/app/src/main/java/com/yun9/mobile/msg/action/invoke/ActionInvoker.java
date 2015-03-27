package com.yun9.mobile.msg.action.invoke;

import com.yun9.mobile.msg.action.entity.ActionContext;

public interface ActionInvoker {
	
	public enum ActionType {
		BPM("bpm");
		
		private String type;
		
		private ActionType(String type) {
			this.setType(type);
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	}
	
	void invoke(ActionContext actionContext);
	
	ActionType getActionType();

}
