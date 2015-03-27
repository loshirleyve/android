package com.yun9.mobile.framework.interfaces.hr;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;

/**
 * 用户排班信息
 * @author Kass
 */
public interface UserScheduleInfo {
	/**
	 * 获取用户排班信息
	 * @param begindate
	 * @param enddate
	 * @param callback
	 */
	public void getUserScheduleInfo(long begindate, long enddate, AsyncHttpResponseCallback callback);
	
	/**
	 * 新增/修改用户排班信息
	 * @param workdate
	 * @param type
	 * @param shiftno
	 * @param callback
	 */
	public void saveUserScheduleInfo(long workdate, String type, String shiftno, AsyncHttpResponseCallback callback);
}
