package com.yun9.mobile.department.callback;

import java.util.List;

import com.yun9.mobile.framework.model.UserContact;

public interface AsyncHttpUserContactCallback {
	public void handler(List<UserContact> userContacts);
}
