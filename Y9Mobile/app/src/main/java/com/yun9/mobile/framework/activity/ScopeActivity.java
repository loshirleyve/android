package com.yun9.mobile.framework.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.adapter.ScopeAdapter;
import com.yun9.mobile.framework.constant.scope.ConstantScope;
import com.yun9.mobile.framework.model.ScopeModel;
import com.yun9.mobile.framework.presenters.ScopePresenter;
import com.yun9.mobile.framework.view.TitleBarView;

public class ScopeActivity extends Activity {

	private final String TITLE = "选择分享范围";
	
	private ScopePresenter presenter;
	private ListView lvScope;
	private ScopeAdapter scopeAdapter;
	private List<ScopeModel> datas;
    private View vLeft;
    private View vRight;
    private TitleBarView tbvTitleBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scope);
		
		presenter = new ScopePresenter(ScopeActivity.this);
		
		initWidget();
		
		bindEvent();
		
	}
	

	
	protected void initWidget() {
		tbvTitleBar = (TitleBarView) findViewById(R.id.tbvTitleBar);
		
		vLeft = tbvTitleBar.getBtnLeft();
		vLeft.setVisibility(View.VISIBLE);
//		vRight = tbvTitleBar.getBtnFuncNav();
//		vRight.setVisibility(View.VISIBLE);
		TextView tvTitle = tbvTitleBar.getTvTitle();
		tvTitle.setText(TITLE);
		tvTitle.setVisibility(View.VISIBLE);
		lvScope = (ListView) findViewById(R.id.lvScope);
		
		datas = new ArrayList<ScopeModel>();
//		datas.add(new ScopeModel("公开"));
		datas.add(new ScopeModel(ConstantScope.SCOPE_PRIVATE));
		datas.add(new ScopeModel(ConstantScope.SCOPE_USER));
		datas.add(new ScopeModel(ConstantScope.SCOPE_ORG));
		scopeAdapter = new ScopeAdapter(datas, ScopeActivity.this, presenter.mode());
		lvScope.setAdapter(scopeAdapter);
		
	}

	protected void bindEvent() {
		lvScope.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				scopeAdapter.radioNotifyDataSetChanged(position);
				presenter.onItemClick(position);
			}
		});
		
		vLeft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				presenter.onCancelOnClick();
			}
		});
	}
	
	 @Override
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
		 presenter.onCancelOnClick();
		 return false;
	  }
}
