package com.yun9.wservice.conf;

public interface PropertiesFactory {

	public Object get(String key);

	public Object get(String key, Object defaultValue);

	public String getString(String key);

	public String getString(String key, String defaultValue);

	public int getInt(String key);

	public int getInt(String key, int defaultValue);
}
