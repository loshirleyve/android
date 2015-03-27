package com.yun9.mobile.framework.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseFragmentActivity extends FragmentActivity {

	protected Context context;

	protected abstract void initWidget();

	protected abstract void bindEvent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		this.initWidget();
		this.bindEvent();
	}

}
