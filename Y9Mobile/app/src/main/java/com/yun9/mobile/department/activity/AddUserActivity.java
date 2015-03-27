package com.yun9.mobile.department.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.mobile.R;
import com.yun9.mobile.department.callback.AsyncHttpOrgCardCallback;
import com.yun9.mobile.department.callback.AsyncHttpUserCallback;
import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.department.support.SelectContactUser;
import com.yun9.mobile.department.support.SelectContactUserFactory;
import com.yun9.mobile.department.support.UserConstant;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.util.AssertValue;

/**    
 *     
 * 项目名称：WelcomeActivity   
 * 类名称：   UserDetailPopView
 * 类描述：    
 * 创建人：  ruanxiaoyu
 * 创建时间：  2014-12-31上午10:47:59
 * 修改人：ruanxiaoyu  
 * 修改时间：2014-12-31上午10:47:59  
 * 修改备注：    
 * @version     
 *     
 */
public class AddUserActivity extends Activity{
	private Context mContext;
	private ImageButton return_btn;
	private RelativeLayout choosedepartment;
	private TextView departmentname;
	private RelativeLayout chooseuser;
	private Org org;//选择关联的组织
	private Button commit;
	private Map<String, User> selectusers;//把选择的用户添加到选择的部门下面
	private Map<String, Org> selectorg;//选择的部门
	private List<User> orgUsers;//选择的部门下的用户
	private List<User> selectUserDoubles;//就是已经和部门关联的人
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user);
		initWidget();
	}
	
	public void initWidget()
	{
		mContext=getApplicationContext();
		return_btn = (ImageButton) findViewById(R.id.return_btn);
		departmentname=(TextView)findViewById(R.id.departmentname);
		org=(Org)getIntent().getSerializableExtra("org");
		departmentname.setText(org.getName());
		getOrgUser();
		selectorg=new HashMap<String, Org>();
		selectorg.put(org.getId(),org);
		commit=(Button)findViewById(R.id.commit);
		choosedepartment=(RelativeLayout)findViewById(R.id.choosedepartmentlayout);
		chooseuser=(RelativeLayout)findViewById(R.id.chooseuserlayout);
		return_btn.setOnClickListener(returnOnclick);
		commit.setOnClickListener(buildUserDepartment);
		choosedepartment.setOnClickListener(choosedepartmentOnclick);
		chooseuser.setOnClickListener(chooseuserOnclick);
		
		
		
	}

	// 返回按钮点击事件
	OnClickListener returnOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	
	//选择部门
	OnClickListener choosedepartmentOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			selectOrg();
		}
	};
	//选择用户
	OnClickListener chooseuserOnclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			selectUser();
		}
	};
	
	
	//选择用户多选
	public void selectUser() {
		if(selectusers==null)
		{
			SelectContactUser selectContactUser = SelectContactUserFactory
					.create(this);
				selectContactUser.selectContactUser(new MsgScopeCallBack() {
					@Override
					public void onSuccess(int mode, Map<String, User> users,
							Map<String, Org> orgs) {
						    selectusers=users;
						    if(AssertValue.isNotNullAndNotEmpty(users))
						    	filterUser(selectusers);
					}
		
					@Override
					public void onFailure() {
					}
				}, null, null, UserConstant.SELECT_USER);
		}
		else
		{
			SelectContactUser selectContactUser = SelectContactUserFactory
					.create(this);
				selectContactUser.selectContactUser(new MsgScopeCallBack() {
					@Override
					public void onSuccess(int mode, Map<String, User> users,
							Map<String, Org> orgs) {
						    selectusers=users;
						    if(AssertValue.isNotNullAndNotEmpty(users))
						    	filterUser(selectusers);
					}
					@Override
					public void onFailure() {
					}
				}, selectusers, null, UserConstant.SELECT_USER);
		}
	}
	
	
	//选择部门单选
	public void selectOrg() {
		SelectContactUser selectContactUser = SelectContactUserFactory
				.create(this);
			selectContactUser.selectContactUser(new MsgScopeCallBack() {
				@Override
				public void onSuccess(int mode, Map<String, User> users,
						Map<String, Org> orgs) {
					selectorg=orgs;
					for (String key : selectorg.keySet()) {
						departmentname.setText(selectorg.get(key).getName());
						org=selectorg.get(key);
						getOrgUser();
					}
				}
				@Override
				public void onFailure() {
				}
			}, null, selectorg, UserConstant.SELECT_ORG_RADIO);
	}
	
	public List<User> getOrgUser()
	{
		orgUsers=new ArrayList<User>();
		DataInfoService data=new DataInfoService();
		Map<String, String> params=new HashMap<String, String>();
		params.put("orgid", org.getId());
		data.getOrgUserCallBack(params, new AsyncHttpUserCallback() {
			
			@Override
			public void handler(List<User> users) {
					orgUsers=users;
				
			}
		});
		return orgUsers;
	}
	
	
	public void filterUser(Map<String, User> users)
	{
		String selectusername="";
		selectUserDoubles=new ArrayList<User>();
		if(AssertValue.isNotNullAndNotEmpty(orgUsers))
		{
			 for (User user:orgUsers) {
				 if(AssertValue.isNotNull(users.get(user.getId())))
				 {
					 users.remove(user.getId());
					 selectUserDoubles.add(user);
				 }
			 }
		}
	    if(AssertValue.isNotNullAndNotEmpty(selectUserDoubles))
	    {
	    	if(!AssertValue.isNotNullAndNotEmpty(users))
	    		commit.setVisibility(View.GONE);
	    	else
	    		commit.setVisibility(View.VISIBLE);
	    	for (User user : selectUserDoubles) {
	    		selectusername+=user.getName()+",";
			}
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setTitle("提示消息").setMessage("您选择的"+selectusername+"已经和该组织关联系统已帮您过滤,是否继续？")
	    	.setPositiveButton("继续", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       })
	       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	   finish();
	           }
	       }).show();
	    }
	}
	
	
	//构建用户和组织的关系
	OnClickListener buildUserDepartment = new OnClickListener() {
		@Override
		public void onClick(View v) {
			DataInfoService data=new DataInfoService();
			Map<String, Object> params=new HashMap<String,Object>();
			List<String> useridlists=new ArrayList<String>();
			List<String> orgidlist=new ArrayList<String>();
			List<String> noticecontent=new ArrayList<String>();
			 for (String key : selectusers.keySet()) {
				 useridlists.add(selectusers.get(key).getId());
			 }
			 orgidlist.add(org.getId());
			 noticecontent.add("您被邀请加入"+data.getUserInst().getName()+"下的"+org.getName()+"组织");
			 params.put("userids",useridlists);
			 params.put("orgid",orgidlist);
			 params.put("noticecontent",noticecontent);
			 data.saveOrgCardCallback(params, new AsyncHttpOrgCardCallback() {
					@Override
					public void handler(String result) {
						Toast.makeText(mContext, result,Toast.LENGTH_SHORT).show();
					}
				});
			 selectusers.clear();
			 finish();
			 Intent intent = new Intent(mContext, DepartmentDetailActivity.class);
			 intent.putExtra("org",(Serializable)org); 
			 startActivity(intent);
		}
	};
	
}
	
	
	
