package com.yun9.mobile.framework.http;

import org.apache.http.Header;

public interface ResponseOriginal {
	public int getStatusCode();

	public void setStatusCode(int statusCode);

	public Header[] getHeaders();

	public void setHeaders(Header[] headers);

	public byte[] getResponseBody();

	public void setResponseBody(byte[] responseBody);

	public Throwable getError();

	public void setError(Throwable error);
}
