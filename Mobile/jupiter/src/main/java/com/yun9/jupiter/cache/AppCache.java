package com.yun9.jupiter.cache;


/**
 * 系统级的缓存，与具体用户无关；
 * 保存/获取的数据不同用户都是一致的
 * @author yun9
 *
 */
public class AppCache extends AbsCache{

	private static final String CACHE_KEY = "com.yun9.wservice";
	private static AppCache instance;

	public static AppCache getInstance() {
		synchronized (AppCache.class) {
			if (instance == null) {
				instance = new AppCache();
			}
		}
		return instance;
	}
	
	private AppCache() {
		super(CACHE_KEY);
	}

}
