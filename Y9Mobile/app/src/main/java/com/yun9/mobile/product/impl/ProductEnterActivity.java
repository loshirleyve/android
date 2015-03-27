package com.yun9.mobile.product.impl;

import java.util.Map;

import android.app.Activity;

import com.yun9.mobile.framework.base.activity.EnterActivity;
import com.yun9.mobile.product.interfaces.ProductEntrance;

public class ProductEnterActivity implements EnterActivity {
	
	public Activity context ;
	private Map<String, Object> params;

	@Override
	public void enter(Activity context, Map<String, Object> params) {
		this.context= context;
		this.params = params;
		ProductEntrance product = new ImplProductEntrance(context);
		product.goBrowseProductInfo();
	}

}
