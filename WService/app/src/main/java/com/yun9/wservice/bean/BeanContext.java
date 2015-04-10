package com.yun9.wservice.bean;

import android.content.Context;

public interface BeanContext {
	public void put(Object object);

	public <T> T get(Class<T> clazz);

	public Context getApplicationContext();

}
