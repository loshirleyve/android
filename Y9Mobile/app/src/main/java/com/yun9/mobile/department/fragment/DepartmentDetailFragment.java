package com.yun9.mobile.department.fragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.department.activity.AddUserActivity;
import com.yun9.mobile.department.adapter.DepartmentGridViewAdapter;
import com.yun9.mobile.department.adapter.UserGridViewAdapter;
import com.yun9.mobile.department.callback.AsyncHttpUserBeanCallback;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.UserBean;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.imageloader.fragment.MyGridView;
import com.yun9.mobile.roundimage.RoundImageView;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   DepartmentDetailFragment
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-30下午4:30:28
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-30下午4:30:28  
 * 修改备注：    
 * @version     
 *     
 */
public class DepartmentDetailFragment extends Fragment {
	private TextView title_txt;//DepartmentDetailActivity的标题，因为在fragment点击部门时要替换fragment也要替换标题，便于控制写在这里
	private Context mContext;
	private View baseView;
	private TextView department_txt;
	private TextView dept_txt;
	private MyGridView userimagegridview;
	private MyGridView departmentimagegridview;
	private List<Org> orgs;
	private Org org;
	private ProgressDialog progressDialog;
	
	private RoundImageView adduser;
	private RoundImageView adddepartment;
	
	public DepartmentDetailFragment(Context context,Org org,TextView title_txt)
	{
		this.mContext=context;
		this.orgs=org.getChildren();
		this.org=org;
		this.title_txt=title_txt;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_department_user, null);
		initView();
		load();
		return baseView;
	}
	
	public void initView()
	{
		title_txt.setText(org.getName().trim());
		progressDialog = TipsUtil.openDialog(null, false, getActivity());
		department_txt=(TextView)baseView.findViewById(R.id.department_txt);
		dept_txt=(TextView)baseView.findViewById(R.id.dept_txt);
		userimagegridview=(MyGridView)baseView.findViewById(R.id.userimagegridview);
		departmentimagegridview=(MyGridView)baseView.findViewById(R.id.departmentimagegridview);
		adduser=(RoundImageView)baseView.findViewById(R.id.adduser);
		adddepartment=(RoundImageView)baseView.findViewById(R.id.adddepartment);
		adduser.setOnClickListener(adduserOnclick);
	}
	
	public void load()
	{
		DataInfoService data=new DataInfoService();
		Map<String, String> params = new HashMap<String, String>();
		params.put("orgid", org.getId());
		data.getOrgUserBeanCallBack(params, new AsyncHttpUserBeanCallback() {
			@Override
			public void handler(List<UserBean> users) {
				if(AssertValue.isNotNullAndNotEmpty(users))
					department_txt.setText("成员("+users.size()+")");
				else
					department_txt.setText("成员(0)");
				UserGridViewAdapter userAdapter=new UserGridViewAdapter(mContext, users,progressDialog,baseView,getActivity());
				userimagegridview.setAdapter(userAdapter);
				if(!AssertValue.isNotNullAndNotEmpty(users) && !AssertValue.isNotNullAndNotEmpty(orgs))
					progressDialog.dismiss();
			}
		});
		if(AssertValue.isNotNullAndNotEmpty(orgs))
			dept_txt.setText("下级部门("+orgs.size()+")");	
		DepartmentGridViewAdapter departmentAdapter=new DepartmentGridViewAdapter(mContext,orgs,progressDialog,getFragmentManager(),title_txt);
		departmentimagegridview.setAdapter(departmentAdapter);	    
	}
	
	OnClickListener adduserOnclick=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			getActivity().finish();
			Intent intent = new Intent(mContext, AddUserActivity.class);
			intent.putExtra("org",(Serializable)org); 
			getActivity().startActivity(intent);
		}
	};
	
}
