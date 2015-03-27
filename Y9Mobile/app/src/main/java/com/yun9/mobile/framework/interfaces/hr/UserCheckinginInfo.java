package com.yun9.mobile.framework.interfaces.hr;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

/**
 * 用户考勤信息
 * @author lhk
 *
 */
public interface UserCheckinginInfo {
	public void getUserCheckinginInfo(String instid, String userid, String begindate, String enddate, AsyncHttpResponseCallback callback);
	
	
	
	
}
