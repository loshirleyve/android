package com.yun9.jupiter.repository;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;

import java.util.Map;


public interface Resource {
	
	public class HEADER {
		public static final String USERID = "userid";
		public static final String INSTID = "instid";
		public static final String DEVICEMODEL = "devicemodel";
		public static final String DEVICEID = "deviceid";

		public static final String LOCATIONX = "locationx";
		public static final String LOCATIONY = "locationy";
		public static final String LOCATIONLABEL = "locationlabel";
		public static final String LOCATIONSCALE = "locationscale";

		public static final String LASTUPID = "lastupid";
		public static final String LASTDOWNID = "lastdownid";
		public static final String LIMITROW = "limitrow";
	}

	public void invok(AsyncHttpResponseCallback callback);

	public void invokSync(AsyncHttpResponseCallback callback);
	
	public Map<String, Object> getParams();

	public void setParams(Map<String, Object> params);

	public Resource param(String key, Object value);

	public Resource header(String key, Object value);

	public Map<String, Object> getHeader();

	public void setHeader(Map<String, Object> header);

	public Repository getRepository();
	
	public boolean isFromService();
	
	public void setFromService(boolean fromService);
}
