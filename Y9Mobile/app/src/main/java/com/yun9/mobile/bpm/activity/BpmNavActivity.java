package com.yun9.mobile.bpm.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.yun9.mobile.R;
import com.yun9.mobile.bpm.model.SysInstProcess;
import com.yun9.mobile.framework.base.activity.BaseActivity;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.cache.UserInfoCache;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.Inst;
import com.yun9.mobile.framework.model.SysMdNavi;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.ResourceUtil;
import com.yun9.mobile.framework.util.TipsUtil;
import com.yun9.mobile.framework.view.FunNavView;
import com.yun9.mobile.framework.view.FunNavView.Holder;

public class BpmNavActivity extends BaseActivity {
	
	public final static String PROCESS_LIST_CACHE_KEY = "PROCESS_LIST_CACHE_KEY_";
	
	private FunNavView funNavView;
	
	private List<SysInstProcess> pros;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_bpm_nav);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initWidget() {
		funNavView = (FunNavView)findViewById(R.id.funnav_view);
		
	}

	private void loadNav() {
		// 不同机构，拥有的流程列表不同
		SessionManager sessionManager = BeanConfig.getInstance()
				.getBeanContext().get(SessionManager.class);
		Inst inst = sessionManager.getAuthInfo().getInstinfo();// 当前机构
		String cacheKey = PROCESS_LIST_CACHE_KEY + inst.getId();
		if (UserInfoCache.getInstance().contains(cacheKey)) {
			pros = UserInfoCache.getInstance().getList(cacheKey, SysInstProcess.class);
			funNavView.load(builderNavItems(pros));
		} else {
			loadNavFromServer(cacheKey);
		}
	}

	private void loadNavFromServer(final String cacheKey) {
		Resource resource = ResourceUtil.get("SysInstProcessQuery4InstRoleService");
		resource.param("1", "1");
		resource.invok(new AsyncHttpResponseCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Response response) {
				pros =(List<SysInstProcess>) response.getPayload();
				if (AssertValue.isNotNullAndNotEmpty(pros)) {
					UserInfoCache.getInstance().put(cacheKey, pros);
					funNavView.load(builderNavItems(pros));
				}
			}
			
			@Override
			public void onFailure(Response response) {
				TipsUtil.showToast("无法获取流程列表。", context);
			}
		});
	
	}

	private List<SysMdNavi> builderNavItems(List<SysInstProcess> pros) {
		List<SysMdNavi> navItems = new ArrayList<SysMdNavi>();
		for (SysInstProcess p : pros) {
			navItems.add(new SysMdNavi(p.getId(),
					p.getName(), "19320000000010111",""));
		}
		return navItems;

	}

	@Override
	protected void bindEvent() {
		funNavView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				FunNavView.Holder holder = (Holder) view.getTag();

				if (AssertValue.isNotNull(holder)) {
					Intent intent = new Intent(context, BpmActivity.class);
					intent.putExtra("sysinstprocess", pros.get(position));
					context.startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadNav();
	}

}
