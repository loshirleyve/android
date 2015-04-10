package com.yun9.wservice;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.interfaces.ui4presenter.Ui4preWelcomeActivity;
import com.yun9.mobile.framework.presenters.PresenterWelcomeActivity;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.position.activity.DemoPositionActivity;

public class WelcomeActivity extends BaseActivity implements Ui4preWelcomeActivity{
	protected static final String TAG = "WelcomeActivity";
	private ImageView mImageView;
	private ProgressDialog proDlg;
	private AlertDialog updateDlg;
	private PresenterWelcomeActivity presenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_welcome);
		super.onCreate(savedInstanceState);
		init();
		enterAPP();
	}

	private void init() {
		initProDlg();
		
//		presenter = new PresenterWelcomeActivity(this);
	}
	
	private void initProDlg() {
		proDlg = new ProgressDialog(this);
		proDlg.setCancelable(false);
		
		
		Builder builder = new Builder(WelcomeActivity.this);
		builder.setTitle("APP升级提示");
		builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				presenter.updateEnsureOnclickListener();
			}
		});
		builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				presenter.updateCancelOnClickListener();
			}
		});
		updateDlg = builder.create();
	}
	

	private void enterAPP(){
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
				SessionManager sessionManager = BeanConfig.getInstance()
						.getBeanContext().get(SessionManager.class);

				boolean isFirst = sessionManager.isFirst();
				boolean isLogin = sessionManager.isLogin();

				// 第一次打开，转到登录界面
				if (isFirst) {
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
					finish();
				} else if (!isFirst && isLogin) { // 已经打开过，并且已经登录
					sessionManager.setFirst(false);
					Intent intent = new Intent(context, MainActivity.class);
					startActivity(intent);
					finish();
				} else { // 其他情况
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}, 1000);
	}
	
	@Override
	protected void initWidget() {
		mImageView = (ImageView) findViewById(R.id.iv_welcome);
	}

	@Override
	protected void bindEvent() {

	}
	
	@Override
	public void showToast(String msg) {
		super.showToast(msg);
	}

	@Override
	public void showProgressDlg(String title, String message) {
		proDlg.setTitle(title);
		proDlg.setMessage(message);
		proDlg.show();
	}

	@Override
	public void closeProgressDlg() {
		proDlg.dismiss();
	}

	@Override
	public void showUpdateDlg(String title, String message) {
		updateDlg.setTitle(title);
		updateDlg.setMessage(message);
		updateDlg.show();
	}
}
