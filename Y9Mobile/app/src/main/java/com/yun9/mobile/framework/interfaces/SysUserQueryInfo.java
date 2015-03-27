package com.yun9.mobile.framework.interfaces;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

public interface SysUserQueryInfo
{
    public void getUserQueryInfo(String userId,AsyncHttpResponseCallback callback);
}
