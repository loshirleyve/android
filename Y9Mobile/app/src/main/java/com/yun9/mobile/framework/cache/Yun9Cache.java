package com.yun9.mobile.framework.cache;


/**
 * 系统级的缓存，与具体用户无关；
 * 保存/获取的数据不同用户都是一致的
 * @author yun9
 *
 */
public class Yun9Cache extends AbsCache{
	
	private static final String CACHE_KEY = "yun9";
	private static Yun9Cache instance;

	public static Yun9Cache getInstance() {
		synchronized (Yun9Cache.class) {
			if (instance == null) {
				instance = new Yun9Cache();
			}
				
		}
		return instance;
	}
	
	public Yun9Cache() {
		super(CACHE_KEY);
	}

}
