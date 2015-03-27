package com.yun9.mobile.framework.cache;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.session.SessionManager;

/**
 * 用户相关的缓存管理类，
 * 通过它保存/获取的缓存信息都与当前用户相关
 * @author yun9
 *
 */
public class UserInfoCache extends AbsCache{

	private static final String PREFIX = "USER_";

	private String userId;

	private static UserInfoCache instance;

	public static UserInfoCache getInstance() {
		synchronized (UserInfoCache.class) {
			SessionManager sessionManger = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
			String currUserId = sessionManger.getAuthInfo().getUserinfo().getId();
			if (instance == null) {
				instance = new UserInfoCache(currUserId);
			} else {
				
				if (!currUserId.equals(instance.getUserId())) {
					instance = new UserInfoCache(currUserId);
				}
			}
				
		}
		return instance;
	}

	private UserInfoCache(String userId) {
		super(PREFIX+userId);
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

}
