package com.yun9.mobile.department.support;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.department.activity.ContactUserActivity;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： SelectContactUser 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2014-11-21下午5:09:33 修改人：ruanxiaoyu 修改时间：2014-11-21下午5:09:33 修改备注：
 * 
 * @version
 * 
 */
public class SelectContactUserImpl implements SelectContactUser {

	private Activity activity;

	/**
	 * @param activity
	 */
	public SelectContactUserImpl(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void selectContactUser(MsgScopeCallBack callBack,int selectUserOrOrg) {
		Intent intent = new Intent(activity, ContactUserActivity.class);
		ContactUserActivity.contactUserCallback= callBack;
		ContactUserActivity.selectUserOrOrg=selectUserOrOrg;
		activity.startActivity(intent);
	}

	@Override
	public void selectContactUser(MsgScopeCallBack callBack,
			Map<String, User> users, Map<String, Org> orgs,int selectUserOrOrg) {
		Intent intent = new Intent(activity, ContactUserActivity.class);
		ContactUserActivity.contactUserCallback= callBack;
		ContactUserActivity.selectedUsers=users;
		ContactUserActivity.selectedOrgs=orgs;
		ContactUserActivity.selectUserOrOrg=selectUserOrOrg;
		activity.startActivity(intent);
	}

}
