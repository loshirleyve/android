package com.yun9.wservice.sys;

import com.yun9.wservice.http.AsyncHttpResponseCallback;

import java.util.Map;



public interface AuthManager {
	public static final String LOGIN = "login";

	public void login(Map<String, Object> params,
                      AsyncHttpResponseCallback callback);

	public void logout();

	
	public void registerDevice();
	
}
