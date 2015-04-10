package com.yun9.wservice.http;

import java.util.Map;

public interface RequestParams {

	public static final String USERID = "userid";
	public static final String INSTID = "instid";
	public static final String DEVICEMODEL = "devicemodel";
	public static final String DEVICEID = "deviceid";
	public static final String DEVICESERIAL = "deviceserial";
	

	public static final String LOCATIONX = "locationx";
	public static final String LOCATIONY = "locationy";
	public static final String LOCATIONLABEL = "locationlabel";
	public static final String LOCATIONSCALE = "locationscale";

	public String getToken();

	public void setToken(String token);

	public String getAction();

	public void setAction(String action);

	public RequestParams data(String key, Object value);

	public RequestParams header(String key, Object value);
	
	public Map<String, Object> getHeader();

	public void setHeader(Map<String, Object> header);

	public Map<String, Object> getData();

	public void setData(Map<String, Object> data);

}
