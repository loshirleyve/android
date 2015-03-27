package com.yun9.mobile.framework.interfaces.topic;

import com.yun9.mobile.framework.model.Topic;

public interface SelectTopicCallback {
	public void onSuccess(Topic topic);

	public void onFailure();
}