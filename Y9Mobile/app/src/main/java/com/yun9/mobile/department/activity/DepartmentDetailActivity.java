package com.yun9.mobile.department.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.fragment.DepartmentDetailFragment;
import com.yun9.mobile.framework.model.Org;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   DepartmentDetailActivity
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-30上午10:36:37
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-30上午10:36:37  
 * 修改备注：    
 * @version     
 *     
 */
public class DepartmentDetailActivity extends FragmentActivity{

	private TextView title_txt;
	private ImageButton returnBtn;
	private Org org;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_department_user);
		super.onCreate(savedInstanceState);
		initView();
		replace();
	}
	
	public void initView()
	{
		title_txt=(TextView)findViewById(R.id.title_txt);
		returnBtn=(ImageButton)findViewById(R.id.return_btn);
		returnBtn.setOnClickListener(returnOnclick);
		org=(Org)getIntent().getSerializableExtra("org");
	}
	
		
	public void replace()
	{
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		DepartmentDetailFragment departmentFragment = new DepartmentDetailFragment(getApplicationContext(),org,title_txt);
		ft.replace(R.id.fl_content, departmentFragment,
				DepartmentDetailActivity.class.getName());
		ft.commit();
	}

	
	OnClickListener returnOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
}
