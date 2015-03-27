package com.yun9.mobile.framework.interfaces.scope;

import java.util.Map;

import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;

public interface MsgScope {
	public void getMsgScope(int currentMode, MsgScopeCallBack callBack);
	
	public void getMsgScope(int currentMode, Map<String, User> selectedUsers, Map<String, Org> selectedOrgs, MsgScopeCallBack callBack);
}
