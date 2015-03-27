package com.yun9.mobile.msg.action;

import android.app.Activity;

import com.yun9.mobile.msg.action.entity.ActionContext;
import com.yun9.mobile.msg.model.MyMsgCard;

/**
 * 消息卡片详情额外处理按钮的功能定义
 * @author yun9
 *
 */
public interface ExtraMenuHandler {
	
	public enum MenuHandlerType {
		NONE("none"),BPMX5("bpm");
		
		private String type;
		
		private MenuHandlerType(String type) {
			this.setType(type);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	void handle(MyMsgCard cardInfo,Activity activity);
	
	void callback(ActionContext actionContext);
	
	MenuHandlerType getMenuType();
	
}
