package com.yun9.wservice.http;

public interface AsyncHttpResponseCallback {
	public void onSuccess(Response response);

	public void onFailure(Response response);

}
