package com.yun9.mobile.department.callback;

import java.util.List;

import com.yun9.mobile.framework.model.UserManual;

public interface AsyncHttpUserManualCallback {
	public void handler(List<UserManual> userManuals);
}
