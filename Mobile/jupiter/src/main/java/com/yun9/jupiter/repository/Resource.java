package com.yun9.jupiter.repository;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;

import java.util.Map;


public interface Resource {

    public final class HEADER {
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

    public final class PULL_TYPE {
		public static final String DOWN = "down";
        public static final String UP ="up";
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

    public Resource pullUp(String id);

    public Resource pullDown(String id);

    public String getPullType();
}
