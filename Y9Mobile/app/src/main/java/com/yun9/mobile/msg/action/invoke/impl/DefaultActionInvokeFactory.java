package com.yun9.mobile.msg.action.invoke.impl;

import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;

import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.msg.action.entity.ActionContext;
import com.yun9.mobile.msg.action.entity.ActionParams;
import com.yun9.mobile.msg.action.invoke.ActionInvokeFactory;
import com.yun9.mobile.msg.action.invoke.ActionInvoker;

public class DefaultActionInvokeFactory implements ActionInvokeFactory, Bean,
		Injection {
	
	private static final Logger logger = Logger.getLogger(DefaultActionInvokeFactory.class);

	private List<ActionInvoker> actionInvokers;

	@Override
	public void invoke(ActionContext actionContext) {
		ActionParams params = actionContext.getParams();
		try {
			for (ActionInvoker invokeer : actionInvokers) {
				if (invokeer.getActionType().getType().equals(
						params.getType())) {
					invokeer.invoke(actionContext);
				}
			}
		} catch (Exception e) {
			Toast.makeText(actionContext.getActivity(), "系统异常，请稍候重试！", Toast.LENGTH_LONG).show();
			logger.exception(e);
		}
	}

	@Override
	public void injection(BeanContext beanContext) {
		// TODO 这里获得配置的actionInvokers列表
		actionInvokers = new ArrayList<ActionInvoker>();
		actionInvokers.add(new BPMActionInvoker());

	}

	@Override
	public Class<?> getType() {
		return ActionInvokeFactory.class;
	}

}
