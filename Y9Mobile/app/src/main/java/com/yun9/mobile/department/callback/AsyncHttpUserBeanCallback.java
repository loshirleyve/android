package com.yun9.mobile.department.callback;

import java.util.List;

import com.yun9.mobile.framework.model.UserBean;

public interface AsyncHttpUserBeanCallback {
	public void handler(List<UserBean> userbeans);
}
