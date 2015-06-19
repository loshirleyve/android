package com.yun9.jupiter.http;

public interface Response {
    public final static String RESPONSE_CODE_SERVICEERROR = "500";
    public final static String RESPONSE_CODE_NETWORKERROR = "502";
    public final static String RESPONSE_CODE_PARAMSERROR = "503";


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

    public ResponseCache getResponseCache();

    public void setResponseCache(ResponseCache responseCache);
}
