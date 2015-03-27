package com.yun9.mobile.framework.activity;


import com.yun9.mobile.R;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.conf.PropertiesFactory;
import com.yun9.mobile.framework.constant.server.ConstantServer;
import com.yun9.mobile.framework.impls.presenter.ImplPre4UiAboutAppActivity;
import com.yun9.mobile.framework.interfaces.presenter4ui.Pre4UiAboutAppActivity;
import com.yun9.mobile.framework.interfaces.ui4presenter.UI4PreAboutAppActivity;
import com.yun9.mobile.framework.view.TitleBarView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AboutAppActivity extends Activity implements UI4PreAboutAppActivity{
	
	private final String TITLE = "关于移店通";
	private TitleBarView tbvTitleBar;
    private View vLeft;
    private View vRight;
	
    private TextView tvVersion;
    private RelativeLayout rlUpdate;
    
    private Pre4UiAboutAppActivity pre4Ui;
    private ImageView ivNewVersionTip;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutapp);
		
		findView();
		setEvent();
		init();
	}
	
	private ProgressDialog proDlg;
	private AlertDialog updateDlg;
	private void initProDlg() {
		proDlg = new ProgressDialog(this);
		proDlg.setCancelable(false);
	}
	
	
	
	private void setEvent() {
		rlUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pre4Ui.updateOnClick();
			}
		});
	}

	private void init() {
		
		initTitle();
		
		showVersion();
		
		initProDlg();
		
		pre4Ui = new ImplPre4UiAboutAppActivity(this, this);
		pre4Ui.work();
	}

	private void showVersion() {
		tvVersion = (TextView) findViewById(R.id.tvVersion);
		PropertiesFactory propertiesFactory;
		propertiesFactory = BeanConfig.getInstance().getBeanContext().get(PropertiesFactory.class);
		String internalversion = propertiesFactory.getString(ConstantServer.KEY_INTERNALVERSION, "utf-8");
		tvVersion.setText(internalversion);
	}

	private void findView() {
		rlUpdate = (RelativeLayout) findViewById(R.id.rlUpdate);
		ivNewVersionTip = (ImageView) findViewById(R.id.ivNewVersionTip);
	}
	
	/**
	 * 初始化标题
	 */
	private void initTitle(){
		tbvTitleBar = (TitleBarView) findViewById(R.id.tbvTitleBar);
		
		vLeft = tbvTitleBar.getBtnLeft();
		vLeft.setVisibility(View.VISIBLE);
		vRight = tbvTitleBar.getBtnFuncNav();
		vRight.setVisibility(View.GONE);
		TextView tvTitle = tbvTitleBar.getTvTitle();
		tvTitle.setText(TITLE);
		tvTitle.setVisibility(View.VISIBLE);
		
		
		vLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public void showUpdateProDlg(String title, String message) {
		proDlg.setTitle(title);
		proDlg.setMessage(message);
		proDlg.show();
		
	}

	@Override
	public void closeUpdateProDlg() {
		proDlg.dismiss();	
	}

	@Override
	public void showUpdateDlg(String title, String message) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				pre4Ui.updateEnsureOnclickListener();
			}
		});
		builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				pre4Ui.updateCancelOnClickListener();
			}
		});
		updateDlg = builder.create();
		updateDlg.show();
	}

	@Override
	public void showFocusUpdateDlg(String title, String message) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				pre4Ui.updateEnsureOnclickListener();
			}
		});
		updateDlg = builder.create();
		updateDlg.setCancelable(false);
		updateDlg.show();	
	}

	@Override
	public void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}



	@Override
	public void isShowNewVersionTip(boolean isUpdate) {
		if(isUpdate){
			ivNewVersionTip.setVisibility(View.VISIBLE);
		}else{
			ivNewVersionTip.setVisibility(View.GONE);
		}
	}
}
