package com.yun9.jupiter.http;

public interface AsyncHttpResponseCallback {
	public void onSuccess(Response response);

	public void onFailure(Response response);

}
