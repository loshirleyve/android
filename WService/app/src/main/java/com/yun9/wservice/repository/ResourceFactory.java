package com.yun9.wservice.repository;


import com.yun9.wservice.http.AsyncHttpResponseCallback;

public interface ResourceFactory {
	public Resource create(String name);

	public void invok(Resource resource, AsyncHttpResponseCallback callback);
	
	public void invokSync(Resource resource, AsyncHttpResponseCallback callback);
}
