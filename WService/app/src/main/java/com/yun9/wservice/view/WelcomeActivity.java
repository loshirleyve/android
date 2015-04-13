package com.yun9.wservice.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import com.yun9.wservice.MainApplication;
import com.yun9.wservice.R;
import com.yun9.jupiter.actvity.BaseActivity;

public class WelcomeActivity extends BaseActivity {
	private ImageView mImageView;
	private ProgressDialog proDlg;
	private AlertDialog updateDlg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
		init();
		enterAPP();
	}

	private void init() {
        mImageView = (ImageView) findViewById(R.id.iv_welcome);
    }

	private void enterAPP(){
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
                //BeanConfig.getInstance().load();
//				SessionManager sessionManager = BeanConfig.getInstance()
//						.getBeanContext().get(SessionManager.class);

//				boolean isFirst = sessionManager.isFirst();
//				boolean isLogin = sessionManager.isLogin();

				// 第一次打开，转到登录界面
//				if (isFirst) {
////					Intent intent = new Intent(context, LoginActivity.class);
////					startActivity(intent);
////					finish();
//				} else if (!isFirst && isLogin) { // 已经打开过，并且已经登录
//					sessionManager.setFirst(false);
//					Intent intent = new Intent(context, MainActivity.class);
//					startActivity(intent);
//					finish();
//				} else { // 其他情况
////					Intent intent = new Intent(context, LoginActivity.class);
////					startActivity(intent);
////					finish();
//				}
			}
		}, 1000);
	}
	

	@Override
	public void showToast(String msg) {
		super.showToast(msg);
	}


}
