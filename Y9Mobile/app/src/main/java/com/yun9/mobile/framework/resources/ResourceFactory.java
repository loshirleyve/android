package com.yun9.mobile.framework.resources;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

public interface ResourceFactory {
	public Resource create(String name);

	public void invok(Resource resource, AsyncHttpResponseCallback callback);
}
