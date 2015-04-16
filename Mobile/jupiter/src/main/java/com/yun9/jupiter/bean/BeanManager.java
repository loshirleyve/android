package com.yun9.jupiter.bean;

import android.content.Context;

public interface BeanManager {
	public void put(Object object);

	public <T> T get(Class<T> clazz);

    public BeanWrapper getBeanWrapper(Class<?> clazz);

	public Context getApplicationContext();

    public void initInjected(Object bean) throws IllegalAccessException;

}
