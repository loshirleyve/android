package com.yun9.jupiter.http.support;

import com.yun9.jupiter.http.ResponseOriginal;

import org.apache.http.Header;


public class DefaultResponseOriginal implements ResponseOriginal {
	private int statusCode;
	private Header[] headers;
	private byte[] responseBody;

	private Throwable error;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public byte[] getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(byte[] responseBody) {
		this.responseBody = responseBody;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}
}
