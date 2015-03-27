package com.yun9.mobile.framework.auth;

import java.util.Map;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.model.Device;

public interface AuthManager {
	public static final String LOGIN = "login";

	public void login(Map<String, Object> params,
			AsyncHttpResponseCallback callback);

	public void logout();

	
	public void registerDevice();
	
}
