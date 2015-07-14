package com.yun9.wservice.view.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yun9.jupiter.repository.RepositoryManager;
import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.main.MainActivity;


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
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
                MainActivity.start(WelcomeActivity.this, null);
				finish();
			}
		}, 1000);
	}
	

	@Override
	public void showToast(String msg) {
		super.showToast(msg);
	}


}
