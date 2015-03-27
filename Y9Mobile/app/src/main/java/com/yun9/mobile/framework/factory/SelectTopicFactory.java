package com.yun9.mobile.framework.factory;

import android.app.Activity;

import com.yun9.mobile.framework.impls.topic.SelectTopicImpl;
import com.yun9.mobile.framework.interfaces.topic.SelectTopic;

public class SelectTopicFactory {

	public static SelectTopic create(Activity activity){
		
		return new SelectTopicImpl(activity);
	}
}
