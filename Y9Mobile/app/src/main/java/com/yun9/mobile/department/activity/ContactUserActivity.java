package com.yun9.mobile.department.activity;

import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.yun9.mobile.R;
import com.yun9.mobile.department.fragment.DepartmentTreeTabFragment;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.util.AssertValue;

/**
 * 
 * 项目名称：yun9mobile 类名称： ContactUserActivity 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2014-10-29下午2:30:33 修改人：ruanxiaoyu 修改时间：2014-10-29下午2:30:33 修改备注：
 * 
 * @version
 * 
 */
public class ContactUserActivity extends FragmentActivity {

	public static Map<String, User> selectedUsers;
	public static Map<String, Org> selectedOrgs;
	public static MsgScopeCallBack contactUserCallback;
	public static int selectUserOrOrg;//显示用户或者显示组织 0都显示，1只显示用户，2只显示组织
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_user);
		replace();
	}

	public void replace() {
		DataInfoService data=new DataInfoService();
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DepartmentTreeTabFragment dpartmentGroupTabFragment = new DepartmentTreeTabFragment(
				this,data.getUserInst().getName()
				,selectedUsers,selectedOrgs,selectUserOrOrg,contactUserCallback);
		dpartmentGroupTabFragment.setIsselect(false);
		dpartmentGroupTabFragment.setState(1);
		ft.replace(R.id.fl_content, dpartmentGroupTabFragment,
				ContactUserActivity.class.getName());
		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(AssertValue.isNotNullAndNotEmpty(selectedOrgs))
			selectedOrgs.clear();
			if(AssertValue.isNotNullAndNotEmpty(selectedUsers))
			selectedUsers.clear();
			contactUserCallback.onFailure();
			finish();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}