package com.yun9.wservice.repository.support;


import com.yun9.wservice.http.Request;
import com.yun9.wservice.http.RequestParams;
import com.yun9.wservice.repository.Resource;

public class DefaultRequest implements Request {
	private Resource resource;

	private RequestParams requestParams;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public RequestParams getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(RequestParams requestParams) {
		this.requestParams = requestParams;
	}

}
