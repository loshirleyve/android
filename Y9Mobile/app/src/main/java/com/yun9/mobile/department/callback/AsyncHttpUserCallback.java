package com.yun9.mobile.department.callback;

import java.util.List;

import com.yun9.mobile.framework.model.User;

public interface AsyncHttpUserCallback {
	public void handler(List<User> users);
}
