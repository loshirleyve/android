package com.yun9.mobile.framework.http;

public interface AsyncHttpResponseCallback {
	public void onSuccess(Response response);

	public void onFailure(Response response);

}
