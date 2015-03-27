package com.yun9.mobile.product.impl;

import android.app.Activity;
import android.content.Intent;

import com.yun9.mobile.product.activity.ProductActivity;
import com.yun9.mobile.product.interfaces.ProductEntrance;

public class ImplProductEntrance implements ProductEntrance {
	
	private Activity activity;
	
	public ImplProductEntrance(Activity activity) {
		super();
		this.activity = activity;
	}

	@Override
	public void goBrowseProductInfo() {
		Intent intent = new Intent(activity, ProductActivity.class);
		intent.putExtra("MODE", 1);
		activity.startActivity(intent);
	}

}
