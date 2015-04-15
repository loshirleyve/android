package com.yun9.wservice.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yun9.jupiter.actvity.JupiterActivity;
import com.yun9.jupiter.actvity.annotation.ViewInject;
import com.yun9.jupiter.bean.annotation.BeanInject;
import com.yun9.jupiter.repository.RepositoryManager;
import com.yun9.wservice.R;



public class WelcomeActivity extends JupiterActivity {
    @ViewInject(id = R.id.iv_welcome,click = "btnClick")
	private ImageView mImageView;

    @BeanInject
    private RepositoryManager repositoryManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
		init();
		enterAPP();
	}

	private void init() {
    }

    public void btnClick(View v){
        this.showToast("点击了图片！");
    }

	private void enterAPP(){
        this.showToast("你好！");
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(intent);
				finish();

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
