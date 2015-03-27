package com.yun9.mobile.msg.action.invoke.impl;

import com.yun9.mobile.msg.action.entity.ActionContext;
import com.yun9.mobile.msg.action.invoke.ActionInvoker;

public class BPMActionInvoker implements ActionInvoker {

	@Override
	public void invoke(ActionContext actionContext) {
		BPMCommentActivity.startActivity(actionContext);
	}

	@Override
	public ActionType getActionType() {
		return ActionType.BPM;
	}

}
