package com.yun9.mobile.framework.cache;

import java.util.List;

import org.json.JSONException;
import android.util.Log;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.framework.util.JsonUtil;

/**
 * 抽取一些缓存的公共方法，属性
 * @author yun9
 *
 */
public abstract class AbsCache {
	
	private ACache mCache;
	
	public AbsCache(String cacheKey) {
		mCache = ACache.get(BeanConfig.getInstance().getBeanContext().getApplicationContext(),cacheKey);
	}
	
	/**
	 * 加入缓存，会将value转换为json字符串
	 * @param key
	 * @param value
	 */
	public void put(String key,Object value) {
		mCache.put(key, JsonUtil.beanToJson(value));
	}
	
	/**
	 * 加入缓存，会将value转换为json字符串
	 * @param key
	 * @param value
	 * @param saveTime 缓存时间，单位为秒，可以从ACache获取一些常用的时间
	 */
	public void put(String key,Object value,int saveTime) {
		mCache.put(key, JsonUtil.beanToJson(value),saveTime);
	}
	
	/**
	 * 获取缓存，转换为指定类型
	 * @param key
	 * @param c
	 * @return
	 */
	public <T> T get(String key,Class<T> c) {
		String value = mCache.getAsString(key);
		if (AssertValue.isNotNullAndNotEmpty(value)) {
			return JsonUtil.jsonToBean(value, c);
		}
		return null;
	}
	
	/**
	 * 获取缓存，转换为指定类型
	 * @param key
	 * @param c
	 * @return
	 */
	public <T> List<T> getList(String key,Class<T> c) {
		String value = mCache.getAsString(key);
		if (AssertValue.isNotNullAndNotEmpty(value)) {
			try {
				return JsonUtil.jsonToBeanList(value, c);
			} catch (JSONException e) {
				Log.e(this.getClass().getSimpleName(), "转换JSON失败，"+key+","+c, e);
			}
		}
		return null;
	}
	
	public Object get(String key) {
		return mCache.getAsObject(key);
	}
	
	public boolean getAsBoolean(String key) {
		return Boolean.valueOf(getAsString(key));
	}
	
	public String getAsString(String key) {
		return mCache.getAsString(key);
	}
	
	public boolean contains(String key) {
		return mCache.file(key) != null;
	}
	
	public void remove(String key) {
		mCache.remove(key);
	}
	
	public void clean() {
		mCache.clear();
	}
}
