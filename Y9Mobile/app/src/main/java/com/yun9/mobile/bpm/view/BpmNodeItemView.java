package com.yun9.mobile.bpm.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.bpm.activity.BpmActivity;
import com.yun9.mobile.bpm.model.ProcessDefNodeInfo;
import com.yun9.mobile.bpm.model.ProcessDefNodeAssignInfo;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.Org;
import com.yun9.mobile.framework.model.User;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;

public class BpmNodeItemView extends LinearLayout {
	
	private Context mContext;
	private TextView tvAddNode;
	private LinearLayout llOrgName;
	private LinearLayout llUsersName;
	private ProcessDefNodeInfo node;

	public BpmNodeItemView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater.from(mContext).inflate(R.layout.bpm_node_item, this);
		tvAddNode = (TextView) findViewById(R.id.tv_add_node);
		llOrgName = (LinearLayout) findViewById(R.id.ll_org_name);
		llUsersName = (LinearLayout) findViewById(R.id.ll_users_name);
	}
	
	public void load(ProcessDefNodeInfo node) {
		this.node = node;
		this.tvAddNode.setText(node.getName());
		loadPlugins();
		
	}

	private void loadPlugins() {
		llUsersName.removeAllViews();
		llOrgName.removeAllViews();
		if (!AssertValue.isNotNullAndNotEmpty(node.getAssigns())) {
			return;
		}
		for (ProcessDefNodeAssignInfo p : node.getAssigns()) {
			if (ProcessDefNodeAssignInfo.Type.DEPT.equals(p.getType())) {
				TextView tv = create(p);
				llOrgName.addView(tv);
			}else {
				TextView tv = create(p);
				llUsersName.addView(tv);
			}
		}
	}

	private TextView create(ProcessDefNodeAssignInfo p) {
		TextView tv = new TextView(mContext);
		android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(2, 0, 0, 0);
		tv.setLayoutParams(params);
		tv.setTextColor(getResources().getColor(R.color.black));
		// 组织跟用户，文本大小不同
		if (ProcessDefNodeAssignInfo.Type.DEPT.equals(p.getType())) {
			tv.setTextSize(16);
			loadOrgName(tv,p.getInstId());
		} else {
			tv.setTextSize(12);
			loadUserName(tv,p.getUserId());
		}
		return tv;
	}

	private void loadUserName(final TextView tv, final String userId) {
		if (AssertValue.isNotNull(BpmActivity.USER_CACHE.get(userId))) {
			tv.setText(BpmActivity.USER_CACHE.get(userId).getName());
		} else {
			Resource resource = ResourceUtil.get("SysUserQuery");
			resource.param("id", userId);
			resource.invok(new AsyncHttpResponseCallback() {
				@SuppressWarnings("unchecked")
				@Override
				public void onSuccess(Response response) {
					List<User> users = (List<User>) response.getPayload();
					if (AssertValue.isNotNullAndNotEmpty(users)) {
						tv.setText(users.get(0).getName());
						BpmActivity.USER_CACHE.put(userId, users.get(0));
					} else {
						TipsUtil.showToast("无法找到指定ID的用户。", getContext());
						tv.setText(userId);
					}
				}
				
				@Override
				public void onFailure(Response response) {
					TipsUtil.showToast("获取用户信息失败。", getContext());
					tv.setText(userId);
				}
			});
		}
	}

	private void loadOrgName(final TextView tv, final String instId) {
		if (AssertValue.isNotNull(BpmActivity.ORG_CACHE.get(instId))) {
			tv.setText(BpmActivity.ORG_CACHE.get(instId).getName());
		} else {
			Resource resource = ResourceUtil.get("SysInstDimOrgQueryByOrgIdService");
			resource.param("orgid", instId);
			resource.invok(new AsyncHttpResponseCallback() {
				@Override
				public void onSuccess(Response response) {
					Org org = (Org) response.getPayload();
					if (AssertValue.isNotNull(org)) {
						tv.setText(org.getName());
						BpmActivity.ORG_CACHE.put(instId, org);
					} else {
						TipsUtil.showToast("无法找到指定ID的部门。", getContext());
						tv.setText(instId);
					}
				}
				
				@Override
				public void onFailure(Response response) {
					TipsUtil.showToast("获取部门失败。", getContext());
					tv.setText(instId);
				}
			});
		}
	}
}
