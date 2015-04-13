package com.yun9.jupiter.bean;

import android.content.Context;

public interface BeanManager {
	public void put(Object object);

	public <T> T get(Class<T> clazz);

	public Context getApplicationContext();

}
