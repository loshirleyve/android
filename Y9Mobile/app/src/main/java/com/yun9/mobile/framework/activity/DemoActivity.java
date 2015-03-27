package com.yun9.mobile.framework.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yun9.mobile.R;
import com.yun9.mobile.framework.base.activity.BaseActivity;

public class DemoActivity extends BaseActivity {

	private Button demoButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_demo);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initWidget() {
		this.demoButton = (Button) this.findViewById(R.id.loginSubmitButton);

	}

	@Override
	protected void bindEvent() {
		this.demoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

}
