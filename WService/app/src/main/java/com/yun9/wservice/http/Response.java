package com.yun9.wservice.http;

public interface Response {

	public ResponseOriginal getOriginal();

	public void setOriginal(ResponseOriginal original);

	public ResponseOriginal createOriginal();

	public String getData();

	public void setData(String data);

	public String getCause();

	public void setCause(String cause);

	public String getCode();

	public void setCode(String code);

	public Object getPayload();

	public void setPayload(Object payload);

	public Request getRequest();

	public void setRequest(Request request);

}
