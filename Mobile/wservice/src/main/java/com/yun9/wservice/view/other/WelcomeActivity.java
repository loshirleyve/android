package com.yun9.wservice.view.other;

import android.content.Intent;
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
    @ViewInject(id = R.id.iv_welcome, click = "btnClick")
    private ImageView mImageView;

    @BeanInject
    private RepositoryManager repositoryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String pushMsg = intent.getStringExtra("push");
        final String extra = intent.getStringExtra("extra");

        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putString("push", pushMsg);
                bundle.putString("extra", extra);
                MainActivity.start(WelcomeActivity.this, bundle);
                finish();
            }
        }, 1000);
    }
}
