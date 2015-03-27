package com.yun9.mobile.framework.interfaces.scope;

import java.util.Map;

import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;


public interface MsgScopeCallBack {
	public void onSuccess(int mode, Map<String, User> users,Map<String, Org> orgs);

	public void onFailure();
}
