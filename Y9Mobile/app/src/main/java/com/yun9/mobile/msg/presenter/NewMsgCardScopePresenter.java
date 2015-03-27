package com.yun9.mobile.msg.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;

import com.yun9.mobile.framework.constant.scope.ConstantScope;
import com.yun9.mobile.framework.impls.scope.MsgScopeImpl;
import com.yun9.mobile.framework.interfaces.scope.MsgScope;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.msg.interfaces.NewMsgCardIView;

public class NewMsgCardScopePresenter {

	private NewMsgCardIView iView;
	private static final Logger logger = Logger
			.getLogger(NewMsgCardScopePresenter.class);
	private static final int MODE_DEFAULT =-1;
	protected static final String tag = NewMsgCardScopePresenter.class
			.getSimpleName();
	private Activity activity;

	private int[] imageIds = new int[ConstantScope.SCOPE_NUM];
	private String[] texts = new String[] { ConstantScope.SCOPE_PRIVATE,
			ConstantScope.SCOPE_USER, ConstantScope.SCOPE_ORG };
	private int currentMod = MODE_DEFAULT;
	private int REQ_GET_MODE = 0x1001;

	// 联系人页面选择的用户和组织
	private Map<String, User> selectedUsers;
	private Map<String, Org> selectedOrgs;
//	private NewMsgCardContactsPresenter contactsPre;

	public NewMsgCardScopePresenter(NewMsgCardIView iView, Activity activity) {
		this.iView = iView;
		this.activity = activity;
		init();
	}

	private void init() {
//		contactsPre = new NewMsgCardContactsPresenter(activity);
//		selectedOrgs = contactsPre.getSelectedOrgs();
//		selectedUsers = contactsPre.getSelectedUsers();

//		this.selectedOrgs = new HashMap<String, Org>();
//		this.selectedUsers = new HashMap<String, User>();
		
		imageIds[ConstantScope.SCOPE_PRIVATE_INT] = activity.getResources()
				.getIdentifier("ic_private", "drawable", "com.yun9.mobile");
		imageIds[ConstantScope.SCOPE_USER_INT] = activity.getResources()
				.getIdentifier("ic_friend", "drawable", "com.yun9.mobile");
		imageIds[ConstantScope.SCOPE_ORG_INT] = activity.getResources()
				.getIdentifier("ic_friend", "drawable", "com.yun9.mobile");
		setUIScope();
		setOrgORuser();
	}

	public void scopeOnClickListener() {
		MsgScope msgScope = new MsgScopeImpl(activity);
		msgScope.getMsgScope(this.currentMod, this.selectedUsers, this.selectedOrgs,
				new MsgScopeCallBack() {

					@Override
					public void onSuccess(int mode, Map<String, User> users,
							Map<String, Org> orgs) {
						currentMod = mode;
						selectedUsers = users;
						selectedOrgs= orgs;
						setUIScope();
						setOrgORuser();
						// if(AssertValue.isNotNullAndNotEmpty(users)){
						// Log.i(tag, "  ");
						// for(Map.Entry<String, User> user : users.entrySet()){
						// String key = user.getKey();
						// User value = user.getValue();
						// }
						// }
						// else{
						// Log.i(tag, "  ");
						// }
//						if (currentMod > 0) {
//							selectedUsers = users;
//							selectedOrgs = orgs;
//
//							contactsPre.updateSelectedOrgs(orgs);
//							contactsPre.updateSelectedUsers(users);
//						}
					}

					@Override
					public void onFailure() {

					}
				});

	}

	private void setUIScope() {

		if (currentMod < 0 || currentMod > imageIds.length) {
			iView.scopeMode("选择分享范围", imageIds[2]);
		} else {
			iView.scopeMode(texts[currentMod], imageIds[currentMod]);
		}

	}

	private void setOrgORuser() {
		List<String> userNames = new ArrayList<String>();
		List<String> orgNames = new ArrayList<String>();
		if (currentMod==ConstantScope.SCOPE_USER_INT && AssertValue.isNotNullAndNotEmpty(selectedUsers)) {
			for (String key : selectedUsers.keySet()) {
				userNames.add(selectedUsers.get(key).getName().trim());
			}
		} else if (currentMod==ConstantScope.SCOPE_ORG_INT && AssertValue.isNotNullAndNotEmpty(selectedOrgs)) {
			for (String key : selectedOrgs.keySet()) {
				orgNames.add(selectedOrgs.get(key).getName().trim());
			}
		}
		iView.setOrgORuser(userNames, orgNames);
	}

	public String getCurrentMode() {
		if (currentMod == ConstantScope.SCOPE_ORG_INT) {
			return ConstantScope.SCOPE_ORG;
		} else if (currentMod == ConstantScope.SCOPE_PRIVATE_INT) {
			return ConstantScope.SCOPE_PRIVATE;
		} else if (currentMod == ConstantScope.SCOPE_USER_INT) {
			return ConstantScope.SCOPE_USER;
		}
		return null;
	}

	public void getIds(List<String> userids, List<String> orgids) {
		
		if(!AssertValue.isNotNull(userids)){
			userids = new ArrayList<String>();
		}
		
		if(!AssertValue.isNotNull(orgids)){
			orgids = new ArrayList<String>();
		}
		
		userids.clear();
		orgids.clear();
		if(AssertValue.isNotNullAndNotEmpty(this.selectedUsers)){
			for (String key : this.selectedUsers.keySet()) {
				userids.add(this.selectedUsers.get(key).getId());
			}
		}
		
		if(AssertValue.isNotNullAndNotEmpty(this.selectedOrgs)){
			for (String key : this.selectedOrgs.keySet()) {
				orgids.add(this.selectedOrgs.get(key).getId());
			}
		}
		
		
//		contactsPre.getIds(userids, orgids);
	}

}
