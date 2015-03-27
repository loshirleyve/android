package com.yun9.mobile.framework.bean;

import android.content.Context;

public interface BeanContext {
	public void put(Object object);

	public <T> T get(Class<T> clazz);

	public Context getApplicationContext();

}
