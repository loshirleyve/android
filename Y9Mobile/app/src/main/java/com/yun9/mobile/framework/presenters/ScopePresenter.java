package com.yun9.mobile.framework.presenters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.department.support.SelectContactUser;
import com.yun9.mobile.department.support.SelectContactUserFactory;
import com.yun9.mobile.framework.constant.scope.ConstantScope;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.util.AssertValue;

public class ScopePresenter {

	protected static final String tag = ScopePresenter.class.getSimpleName();
	private Activity activity;
	private int currentMode;
	public static MsgScopeCallBack callBack;
	private MsgScopeCallBack mCallBack;

	// 联系人页面选择的用户和组织
	public static Map<String, User> selectedUsers = null;
	public static Map<String, Org> selectedOrgs = null;

	private static Map<String, User> mSelectedUsers;
	private static Map<String, Org> mSlectedOrgs;

	private List<String> useridList;
	private List<String> orgidList;

	/**
	 * @param activity
	 */
	public ScopePresenter(Activity activity) {
		super();
		this.activity = activity;
		init();
	}

	private void init() {
		getMode();
	}

	private void getMode() {
		Intent intent = activity.getIntent();
		currentMode = intent.getIntExtra(ConstantScope.INTENTPARM_MODE,
				ConstantScope.SCOPE_ORG_INT);
		mCallBack = callBack;
		mSelectedUsers = selectedUsers;
		mSlectedOrgs = selectedOrgs;
	}

	public int mode() {
		return currentMode;
	}

	public void onItemClick(int position) {
		if (position == ConstantScope.SCOPE_ORG_INT) {
			currentMode = ConstantScope.SCOPE_ORG_INT;
		} else if (position == ConstantScope.SCOPE_PRIVATE_INT) {
			currentMode = ConstantScope.SCOPE_PRIVATE_INT;
		} else if (position == ConstantScope.SCOPE_USER_INT) {
			currentMode = ConstantScope.SCOPE_USER_INT;
		}

		if (currentMode == ConstantScope.SCOPE_PRIVATE_INT) {
			try {
				mCallBack.onSuccess(currentMode, null, null);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			activity.finish();
		} else// 如果选择org 公司组织模式 则去获取联系人
		{
			selectUserOrg();
		}

	}

	public void onCancelOnClick() {
		activity.finish();
		try {
			if (mCallBack != null) {
				mCallBack.onSuccess(currentMode, mSelectedUsers, mSlectedOrgs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
					mCallBack.onSuccess(currentMode, selectedUsers,
							selectedOrgs);
					activity.finish();
				}

				@Override
				public void onFailure() {
				}

			}, selectedUsers, selectedOrgs, currentMode);
		} else {
			selectContactUser.selectContactUser(new MsgScopeCallBack() {

				@Override
				public void onSuccess(int mode, Map<String, User> users,
						Map<String, Org> orgs) {
					selectedUsers = users;
					selectedOrgs = orgs;
					mCallBack.onSuccess(currentMode, selectedUsers,
							selectedOrgs);
					activity.finish();
				}

				@Override
				public void onFailure() {
				}
			}, currentMode);
		}
	}

}
