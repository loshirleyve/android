package com.yun9.mobile.framework.interfaces;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

public interface LoadMoreable<Data> {
	
	void loadMore(AsyncHttpResponseCallback callback);

}
