package com.yun9.mobile.framework.topic;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

public interface TopicFactory {

	public void getTopics(String instid, AsyncHttpResponseCallback callback);
	
	
	public void getTopics(String instid, String userid, String name,AsyncHttpResponseCallback callback);
}
