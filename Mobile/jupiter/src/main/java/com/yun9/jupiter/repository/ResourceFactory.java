package com.yun9.jupiter.repository;


import com.yun9.jupiter.http.AsyncHttpResponseCallback;

public interface ResourceFactory {
	public Resource create(String name);

	public void invok(Resource resource, AsyncHttpResponseCallback callback);
	
	public void invokSync(Resource resource, AsyncHttpResponseCallback callback);
}
