package com.yun9.jupiter.http.support;


import com.yun9.jupiter.http.Request;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.http.ResponseCache;
import com.yun9.jupiter.http.ResponseOriginal;

public class DefaultResponse implements Response {
	private ResponseOriginal original;

	private Object payload;

	private String data;

	private int length;

	private String cause;

	private String code;

	private String cache;

	private Request request;

	private ResponseCache responseCache;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ResponseOriginal getOriginal() {
		return original;
	}

	public void setOriginal(ResponseOriginal original) {
		this.original = original;
	}

	public ResponseOriginal createOriginal() {
		ResponseOriginal responseOriginal = new DefaultResponseOriginal();
		return responseOriginal;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public ResponseCache getResponseCache() {
		return responseCache;
	}

	public void setResponseCache(ResponseCache responseCache) {
		this.responseCache = responseCache;
	}
}
