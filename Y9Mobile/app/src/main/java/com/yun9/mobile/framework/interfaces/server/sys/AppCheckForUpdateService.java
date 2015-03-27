package com.yun9.mobile.framework.interfaces.server.sys;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

public interface AppCheckForUpdateService {
	public void getAppUpdateInfo(AsyncHttpResponseCallback callback);
}
