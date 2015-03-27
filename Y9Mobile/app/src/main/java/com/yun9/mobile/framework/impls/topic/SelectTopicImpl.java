package com.yun9.mobile.framework.impls.topic;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.framework.activity.TopicActivity;
import com.yun9.mobile.framework.interfaces.topic.SelectTopic;
import com.yun9.mobile.framework.interfaces.topic.SelectTopicCallback;
import com.yun9.mobile.framework.presenters.TopicPresenter;

public class SelectTopicImpl implements SelectTopic{

	private Activity activity;
	
	
	/**
	 * @param activity
	 */
	public SelectTopicImpl(Activity activity) {
		super();
		this.activity = activity;
	}


	@Override
	public void selectTopic(SelectTopicCallback callBack) {
		Intent intent = new Intent(activity, TopicActivity.class);
		TopicPresenter.callBack = callBack;
		activity.startActivity(intent);
	}

}
