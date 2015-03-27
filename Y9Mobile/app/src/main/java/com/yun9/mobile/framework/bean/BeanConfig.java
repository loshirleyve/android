package com.yun9.mobile.framework.bean;

import android.content.Context;

import com.yun9.mobile.framework.support.DefaultBeanContext;
import com.yun9.mobile.framework.util.AssertValue;

public class BeanConfig {

	private static BeanConfig instance;

	private BeanContext beanContext;

	private BeanConfig() {

	}

	public static BeanConfig getInstance() {
		if (!AssertValue.isNotNull(instance)) {
			instance = new BeanConfig();
			instance.init();
		}

		return instance;
	}

	private void init() {

	}

	public BeanContext load(Context context) {
		beanContext = new DefaultBeanContext(context);
		return beanContext;
	}

	public BeanContext getBeanContext() {
		return this.beanContext;
	}

}
