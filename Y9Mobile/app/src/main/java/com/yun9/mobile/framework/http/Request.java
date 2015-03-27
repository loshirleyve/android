package com.yun9.mobile.framework.http;

import com.yun9.mobile.framework.resources.Resource;

public interface Request {
	public Resource getResource();

	public void setResource(Resource resource);

	public RequestParams getRequestParams();

	public void setRequestParams(RequestParams requestParams);
}
