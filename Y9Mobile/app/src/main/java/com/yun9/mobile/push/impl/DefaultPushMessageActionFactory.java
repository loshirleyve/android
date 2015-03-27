package com.yun9.mobile.push.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.JsonObject;
import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.JsonUtil;
import com.yun9.mobile.push.HandlerType;
import com.yun9.mobile.push.PushMessageActionFactory;
import com.yun9.mobile.push.PushMessageActionHandler;
import com.yun9.mobile.push.ah.func.CheckDutyActionHandler;
import com.yun9.mobile.push.ah.func.MsgCardPushMessageActionHandler;

public class DefaultPushMessageActionFactory implements
		PushMessageActionFactory, Bean, Injection {

	private List<PushMessageActionHandler> handlers;

	@Override
	public void dealWith(Context context, String params) {
		if (!AssertValue.isNotNullAndNotEmpty(params)) {
			return;
		}
		JsonObject paramsJson = JsonUtil.fromString(params);
		PushMessageActionHandler handler = choiceHandler(paramsJson);
		if (AssertValue.isNotNull(handler)) {
			handler.handle(context, paramsJson);
		}

	}

	private PushMessageActionHandler choiceHandler(JsonObject paramsJson) {
		HandlerType ht = HandlerType.getByTypeAndValue(
				paramsJson.get(PushMessageActionHandler.BASE_PARAM_TYPE)
						.getAsString(),
				paramsJson.get(PushMessageActionHandler.BASE_PARAM_VALUE)
						.getAsString());
		if (ht == null) {
			return null;
		}
		for (PushMessageActionHandler h : handlers) {
			if (ht == h.getHandlerType()) {
				return h;
			}
		}
		return null;
	}

	@Override
	public void injection(BeanContext beanContext) {
		// FIXME 这里应该采用注入的
		handlers = new ArrayList<PushMessageActionHandler>();
		handlers.add(new MsgCardPushMessageActionHandler());
		handlers.add(new CheckDutyActionHandler());
	}

	@Override
	public Class<?> getType() {
		return PushMessageActionFactory.class;
	}

}
