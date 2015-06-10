package com.yun9.jupiter.http;

import com.yun9.jupiter.repository.Resource;

public interface AsyncHttpResponseCallback {
	public void onSuccess(Response response);

	public void onFailure(Response response);

	public void onFinally(Response response);

}
