package com.yun9.jupiter.http;


import com.yun9.jupiter.repository.Resource;

public interface Request {
	public Resource getResource();

	public void setResource(Resource resource);

	public RequestParams getRequestParams();

	public void setRequestParams(RequestParams requestParams);
}
