package com.yun9.jupiter.cache;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.manager.SessionManager;

/**
 * 用户相关的缓存管理类，
 * 通过它保存/获取的缓存信息都与当前用户相关
 * @author yun9
 *
 */
public class UserDataCache extends AbsCache{

	private static final String PREFIX = "USER_";

	private String userId;

	private static UserDataCache instance;

	public static UserDataCache getInstance() {
		synchronized (UserDataCache.class) {
			JupiterApplication jupiterApplication = JupiterApplication.mInstance;
			BeanManager beanManager =jupiterApplication.getBeanManager();
			SessionManager sessionManger = null;
			String currUserId = sessionManger.getAuthInfo().getUserinfo().getId();
			if (instance == null) {
				instance = new UserDataCache(currUserId);
			} else {
				
				if (!currUserId.equals(instance.getUserId())) {
					instance = new UserDataCache(currUserId);
				}
			}
				
		}
		return instance;
	}

	private UserDataCache(String userId) {
		super(PREFIX+userId);
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

}
