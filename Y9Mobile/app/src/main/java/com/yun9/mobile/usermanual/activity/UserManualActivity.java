package com.yun9.mobile.usermanual.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yun9.mobile.R;
import com.yun9.mobile.usermanual.fragment.UserManualListFragment;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserManualActivity
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-5下午5:56:20
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-5下午5:56:20  
 * 修改备注：    
 * @version     
 *     
 */
public class UserManualActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_manual);
		replace();
	}

	public void replace() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		UserManualListFragment fragment=new UserManualListFragment(getApplicationContext());
		ft.replace(R.id.fl_content, fragment,
				UserManualActivity.class.getName());
		ft.commit();
}

}
