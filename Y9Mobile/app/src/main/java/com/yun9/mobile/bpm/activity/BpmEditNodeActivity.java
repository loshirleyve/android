package com.yun9.mobile.bpm.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.bpm.model.ProcessDefNodeAssignInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeInfo;
import com.yun9.mobile.department.support.SelectContactUser;
import com.yun9.mobile.department.support.SelectContactUserFactory;
import com.yun9.mobile.department.support.UserConstant;
import com.yun9.mobile.framework.base.activity.BaseActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.interfaces.scope.MsgScopeCallBack;
import com.yun9.mobile.framework.model.Inst;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.view.TextTitleBarView;

/**
 * 编辑流程节点
 * @author yun9
 *
 */
public class BpmEditNodeActivity extends BaseActivity {
	
	public static final int BPM_EDIT_NODE_RETURN_CODE = 120;

	private TextTitleBarView titleBarView;
	private TextView nodeName;
	private TextView choiceUser;
	private TextView choiceOrg;
	private LinearLayout llChoiceOrg;
	private LinearLayout llChoiceUser;
	private GridView llOrgName;
	private GridView llUsersName;
	private BaseAdapter orgAdapter;
	private BaseAdapter userAdapter;
	private ProcessDefNodeInfo node;
	private int editNodeIndex;
	private int totalNodeSize;
	private List<User> choiceUserList;
	private List<Org> choiceOrgList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_bpm_edit_node);
		node = (ProcessDefNodeInfo) this.getIntent().getSerializableExtra("node");
		if (AssertValue.isNotNull(node)) {
			editNodeIndex =  this.getIntent().getIntExtra("index", -1);
		} else {
			totalNodeSize =  this.getIntent().getIntExtra("totalNodeSize", 0);
			node = new ProcessDefNodeInfo();
			node.setAllowReject(true);
			editNodeIndex = -1;
		}
		super.onCreate(savedInstanceState);
		initData();
	}

	@Override
	protected void initWidget() {
		titleBarView = (TextTitleBarView) findViewById(R.id.title_bar);
		nodeName = (TextView) findViewById(R.id.bpm_node_name);
		llChoiceOrg = (LinearLayout) findViewById(R.id.ll_choice_org);
		llChoiceUser = (LinearLayout) findViewById(R.id.ll_choice_user);
		choiceUser = (TextView) findViewById(R.id.tv_choice_user);
		choiceOrg = (TextView) findViewById(R.id.tv_choice_org);
		llOrgName = (GridView) findViewById(R.id.ll_org_name);
		llUsersName = (GridView) findViewById(R.id.ll_users_name);
	}

	@Override
	protected void bindEvent() {
		titleBarView.getBtnReturn().setVisibility(View.VISIBLE);
		titleBarView.getTvTitle().setVisibility(View.VISIBLE);
		titleBarView.getTvTitle().setText(R.string.bpm_add_node_t);
		titleBarView.getBtnFuncNav().setVisibility(View.VISIBLE);
		titleBarView.getBtnReturn().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		titleBarView.getBtnFuncNav().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				node.setName(nodeName.getText().toString());
				collectPlugins();
				Intent intent = new Intent();
				intent.putExtra("node", node);
				intent.putExtra("index", editNodeIndex);
				setResult(BPM_EDIT_NODE_RETURN_CODE, intent);
				finish();
			}

			private void collectPlugins() {
				node.getAssigns().clear();
				SessionManager sessionManager = BeanConfig.getInstance()
						.getBeanContext().get(SessionManager.class);
				Inst inst = sessionManager.getAuthInfo().getInstinfo();// 当前机构
				for(User tempUser : choiceUserList) {
					node.getAssigns().add(new ProcessDefNodeAssignInfo(tempUser.getId(),inst.getId()));
				}
				for(Org tempOrg : choiceOrgList) {
					node.getAssigns().add(new ProcessDefNodeAssignInfo(tempOrg.getId(),tempOrg.getId(),ProcessDefNodeAssignInfo.Type.DEPT));
				}
			}
		});
		
		llChoiceOrg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectUserOrg(UserConstant.SELECT_ORG);
			}
		});
		llChoiceUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectUserOrg(UserConstant.SELECT_USER);
			}
		});
	}

	private void initData() {
		initNodePlugins();
		if (AssertValue.isNotNullAndNotEmpty(node.getName())) {
			titleBarView.getTvTitle().setText(node.getName());
			nodeName.setText(node.getName());
		} else if (totalNodeSize <10){
			nodeName.setText("第"+number2Chinese.get(totalNodeSize+1)+"审批人");
		}
		initAdapter();
	}
	
	private void initAdapter() {
		orgAdapter = new BaseAdapter(){
			@Override
			public int getCount() {
				return choiceOrgList.size();
			}

			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public boolean isEnabled(int position) {
				return false;
			}
			
			@Override
			public boolean areAllItemsEnabled() {
				return false;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return create(choiceOrgList.get(position));
			}
			
		};
		llOrgName.setAdapter(orgAdapter);
		
		userAdapter = new BaseAdapter(){
			@Override
			public int getCount() {
				return choiceUserList.size();
			}

			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public boolean isEnabled(int position) {
				return false;
			}
			
			@Override
			public boolean areAllItemsEnabled() {
				return false;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				return create(choiceUserList.get(position));
			}
			
		};
		llUsersName.setAdapter(userAdapter);
	
	}

	private void initNodePlugins() {
		choiceUserList = new ArrayList<User>();
		choiceOrgList= new ArrayList<Org>();
		if (!AssertValue.isNotNull(node.getAssigns())) {
			node.setAssigns(new ArrayList<ProcessDefNodeAssignInfo>());
			return;
		}
		List<ProcessDefNodeAssignInfo> plugins = node.getAssigns();
		for (ProcessDefNodeAssignInfo p : plugins) {
			if (ProcessDefNodeAssignInfo.Type.DEPT.equals(p.getType())) {
				Org org = new Org();
				org.setId(p.getInstId());
				if (AssertValue.isNotNull(BpmActivity.ORG_CACHE.get(p.getInstId()))) {
					org.setName(BpmActivity.ORG_CACHE.get(p.getInstId()).getName());
				} else {
					org.setName(p.getInstId());
				}
				choiceOrgList.add(org);
			} else {
				User u = new User();
				u.setId(p.getUserId());
				if (AssertValue.isNotNull(BpmActivity.USER_CACHE.get(p.getUserId()))) {
					u.setName(BpmActivity.USER_CACHE.get(p.getUserId()).getName());
				} else {
					u.setName(p.getUserId());
				}
				choiceUserList.add(u);
			}
		}
		choiceUser(choiceUserList);
		choiceOrg(choiceOrgList);
	}

	private void selectUserOrg(final int selectUserOrOrg) {
		SelectContactUser selectContactUser = SelectContactUserFactory
				.create(this);
		selectContactUser.selectContactUser(new MsgScopeCallBack() {
			@Override
			public void onSuccess(int mode, Map<String, User> users,
					Map<String, Org> orgs) {
				if (selectUserOrOrg == UserConstant.SELECT_USER
						&& AssertValue.isNotNull(users)) {
					choiceUserList.clear();
					choiceUser(users.values());
					choiceUserList.addAll(users.values());
					userAdapter.notifyDataSetChanged();
				} else if (selectUserOrOrg == UserConstant.SELECT_ORG
						&& AssertValue.isNotNull(orgs)) {
					choiceOrgList.clear();
					choiceOrg(orgs.values());
					choiceOrgList.addAll(orgs.values());
					orgAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onFailure() {
				// Do Nothing
			}

		},getUserMap(),getOrgMap(),selectUserOrOrg);
	
		}
	
	private Map<String, Org> getOrgMap() {
		Map<String, Org> orgs = new HashMap<String, Org>();
		for (Org o : choiceOrgList) {
			orgs.put(o.getId(), o);
		}
		return orgs;
	}

	private Map<String, User> getUserMap() {
		Map<String, User> users = new HashMap<String, User>();
		for (User u : choiceUserList) {
			users.put(u.getId(), u);
		}
		return users;
	}

	private void choiceOrg(Collection<Org> choiceOrgList2) {
		if (!AssertValue.isNotNullAndNotEmpty(choiceOrgList2)) {
			choiceOrg.setVisibility(View.VISIBLE);
			llOrgName.setVisibility(View.GONE);
			return;
		}
		choiceOrg.setVisibility(View.GONE);
		llOrgName.setVisibility(View.VISIBLE);
	}

	private TextView create(Org tempOrg) {
		TextView tv = new TextView(getApplicationContext());
		android.widget.AbsListView.LayoutParams params = new android.widget.AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(params);
		tv.setTextColor(getResources().getColor(R.color.black));
		tv.setTextSize(14);
		tv.setFocusable(false);
		tv.setFocusableInTouchMode(false);
		tv.setText(tempOrg.getName());
		return tv;
	}

	private void choiceUser(Collection<User> choiceUserList2) {
		if (!AssertValue.isNotNullAndNotEmpty(choiceUserList2)) {
			choiceUser.setVisibility(View.VISIBLE);
			llUsersName.setVisibility(View.GONE);
			return;
		}
		choiceUser.setVisibility(View.GONE);
		llUsersName.setVisibility(View.VISIBLE);
	}

	private TextView create(User tempUser) {
		TextView tv = new TextView(getApplicationContext());
		android.widget.AbsListView.LayoutParams params = new android.widget.AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(params);
		tv.setTextColor(getResources().getColor(R.color.black));
		tv.setTextSize(14);
		tv.setFocusable(false);
		tv.setFocusableInTouchMode(false);
		tv.setText(tempUser.getName());
		return tv;
	}
	
	private static  Map<Integer, String> number2Chinese;
	
	static {
		number2Chinese = new HashMap<Integer, String>();
		number2Chinese.put(1, "一");
		number2Chinese.put(2, "二");
		number2Chinese.put(3, "三");
		number2Chinese.put(4, "四");
		number2Chinese.put(5, "五");
		number2Chinese.put(6, "六");
		number2Chinese.put(7, "七");
		number2Chinese.put(8, "八");
		number2Chinese.put(9, "九");
		number2Chinese.put(10, "十");
	}
}
