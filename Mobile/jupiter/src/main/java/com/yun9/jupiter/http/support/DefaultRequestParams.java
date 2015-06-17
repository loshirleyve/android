package com.yun9.jupiter.http.support;

import com.yun9.jupiter.http.RequestParams;
import com.yun9.jupiter.util.AssertValue;

import java.util.HashMap;
import java.util.Map;


public class DefaultRequestParams implements RequestParams {


	private String token;

	private String action;

	private Map<String, Object> header;

	private Map<String, Object> data;

	public Map<String, Object> getHeader() {
		return header;
	}

	public void setHeader(Map<String, Object> header) {
		this.header = header;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public RequestParams data(String key, Object value) {
		if (!AssertValue.isNotNull(data)) {
			this.data = new HashMap<String, Object>();
		}
		this.data.put(key, value);
		return this;
	}

	public RequestParams header(String key, Object value) {
		if (!AssertValue.isNotNull(this.header)) {
			this.header = new HashMap<String, Object>();
		}
		this.header.put(key, value);
		return this;
	}

}
