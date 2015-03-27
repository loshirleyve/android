package com.yun9.mobile.framework.resources;

import java.util.ArrayList;
import java.util.List;

import com.yun9.mobile.framework.util.AssertValue;

public class Repository {

	private String name;

	private String action;

	private List<RepositoryParam> params;

	private String type;

	private String baseUrl;

	private String token;

	private String contentType;

	private RepositoryOutput output;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RepositoryParam> getParams() {
		return params;
	}

	public void setParams(List<RepositoryParam> params) {
		this.params = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public RepositoryOutput getOutput() {
		return output;
	}

	public void setOutput(RepositoryOutput output) {
		this.output = output;
	}

	public void putParam(String key, String value) {
		if (!AssertValue.isNotNull(params)) {
			this.params = new ArrayList<RepositoryParam>();
		}

		RepositoryParam param = new RepositoryParam();
		param.setKey(key);
		param.setValue(value);
		this.params.add(param);
	}
}
