package com.yun9.jupiter.manager;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;

import java.util.Map;



public interface AuthManager {
	public static final String LOGIN = "login";

	public void login(Map<String, Object> params,
                      AsyncHttpResponseCallback callback);

	public void logout();

	
	public void registerDevice();
	
}
