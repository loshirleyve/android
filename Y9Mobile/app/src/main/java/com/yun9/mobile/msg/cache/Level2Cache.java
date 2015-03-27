package com.yun9.mobile.msg.cache;

public interface Level2Cache {

	
	/**
	 * 设置缓存数据
	 * @param data
	 * @return
	 */
	public Boolean setCacheData(Object data);
	
	
	
	/**
	 * 获取缓存数据
	 * @return
	 */
	public Object getCacheData();
	
	
	/**
	 * 清理缓存
	 * @return
	 */
	public Boolean clearCacheData();
}
