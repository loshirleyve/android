package com.yun9.mobile.msg.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;

import com.yun9.mobile.department.support.SelectContactUser;
import com.yun9.mobile.department.support.SelectContactUserFactory;
import com.yun9.mobile.department.support.UserConstant;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.util.AssertValue;

public class NewMsgCardContactsPresenter {

	protected static final String tag = NewMsgCardContactsPresenter.class
			.getSimpleName();
	private Activity activity;

	private List<String> useridList;
	private List<String> orgidList;

	// 联系人页面选择的用户和组织
	private Map<String, User> selectedUsers;
	private Map<String, Org> selectedOrgs;
	/**
	 * @param activity
	 */
	
	public NewMsgCardContactsPresenter(Activity activity) {
		super();
		this.activity = activity;
		init();
	}

	private void init() {
		selectedUsers = new HashMap<String, User>();
		selectedOrgs = new HashMap<String, Org>();
		useridList = new ArrayList<String>();
		orgidList = new ArrayList<String>();
	}

	public void addContactUserOnClickListener() {
		selectUserOrg();
	}

	public void selectUserOrg() {
		SelectContactUser selectContactUser = SelectContactUserFactory
				.create(activity);
		useridList = new ArrayList<String>();
		orgidList = new ArrayList<String>();
		if (AssertValue.isNotNullAndNotEmpty(selectedUsers)
				|| AssertValue.isNotNullAndNotEmpty(selectedOrgs)) {
			selectContactUser.selectContactUser(new MsgScopeCallBack() {

				@Override
				public void onSuccess(int mode, Map<String, User> users,
						Map<String, Org> orgs) {
					selectedUsers = users;
					selectedOrgs = orgs;
					if (AssertValue.isNotNullAndNotEmpty(useridList))
						useridList.clear();
					if (AssertValue.isNotNullAndNotEmpty(orgidList))
						orgidList.clear();
					for (String key : users.keySet()) {
						useridList.add(users.get(key).getId());
					}
					for (String key : orgs.keySet()) {
						orgidList.add(orgs.get(key).getId());
					}
					
				}

				@Override
				public void onFailure() {
					activity.finish();
				}

			}, selectedUsers, selectedOrgs,UserConstant.SELECT_USER_AND_ORG);
		} else {
			selectContactUser.selectContactUser(new MsgScopeCallBack() {

				@Override
				public void onSuccess(int mode, Map<String, User> users,
						Map<String, Org> orgs) {
					selectedUsers = users;
					selectedOrgs = orgs;
					if (AssertValue.isNotNullAndNotEmpty(useridList))
						useridList.clear();
					if (AssertValue.isNotNullAndNotEmpty(orgidList))
						orgidList.clear();
					for (String key : users.keySet()) {
						useridList.add(users.get(key).getId());
					}
					for (String key : orgs.keySet()) {
						orgidList.add(orgs.get(key).getId());
					}
				}

				@Override
				public void onFailure() {
					activity.finish();
				}

			},UserConstant.SELECT_USER_AND_ORG);
		}
	}

	/**
	 * 
	 * @param userids
	 * @param orgids
	 */
	public void getIds(List<String> userids, List<String> orgids) {

		if (AssertValue.isNotNullAndNotEmpty(useridList)
				&& AssertValue.isNotNull(userids)) {
			userids.addAll(useridList);
		}
		if (AssertValue.isNotNullAndNotEmpty(orgidList)
				&& AssertValue.isNotNull(orgids)) {
			orgids.addAll(orgidList);
		}
	}

	public Map<String, Org> getSelectedOrgs() {
		return selectedOrgs;
	}

	public Map<String, User> getSelectedUsers() {
		return selectedUsers;
	}

	public void updateSelectedOrgs(Map<String, Org> orgs) {
		selectedOrgs.clear();
		orgidList.clear();
		if (AssertValue.isNotNullAndNotEmpty(orgs)) {
			for (Map.Entry<String, Org> org : orgs.entrySet()) {
				String key = org.getKey();
				Org value = org.getValue();
				selectedOrgs.put(key, value);
				orgidList.add(value.getId());
			}
		}
	}

	public void updateSelectedUsers(Map<String, User> users) {
		selectedUsers.clear();
		useridList.clear();
		if (AssertValue.isNotNullAndNotEmpty(users)) {
			for (Map.Entry<String, User> user : users.entrySet()) {
				String key = user.getKey();
				User value = user.getValue();
				selectedUsers.put(key, value);
				useridList.add(value.getId());
			}
		}
	}

}
