package com.yun9.mobile.framework.impls.scope;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.yun9.mobile.framework.activity.ScopeActivity;
import com.yun9.mobile.framework.constant.Constant;
import com.yun9.mobile.framework.constant.scope.ConstantScope;
import com.yun9.mobile.framework.interfaces.scope.MsgScope;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.presenters.ScopePresenter;

public class MsgScopeImpl implements MsgScope{

	private Activity activity;
	private Context context;
	
	/**
	 * @param context
	 */
	public MsgScopeImpl(Activity activity) {
		super();
		this.activity = activity;
	}



	@Override
	public void getMsgScope(int currentMode, MsgScopeCallBack callBack) {
		Intent intent = new Intent(activity, ScopeActivity.class);
		intent.putExtra(ConstantScope.INTENTPARM_MODE, currentMode);
		ScopePresenter.callBack = callBack;
		
		activity.startActivity(intent);
	}



	@Override
	public void getMsgScope(int currentMode,
			Map<String, User> selectedUsers, Map<String, Org> selectedOrgs, MsgScopeCallBack callBack) {
		Intent intent = new Intent(activity, ScopeActivity.class);
		intent.putExtra(ConstantScope.INTENTPARM_MODE, currentMode);
		ScopePresenter.callBack = callBack;
		ScopePresenter.selectedOrgs = selectedOrgs;
		ScopePresenter.selectedUsers = selectedUsers;
		activity.startActivity(intent);
	}

}
