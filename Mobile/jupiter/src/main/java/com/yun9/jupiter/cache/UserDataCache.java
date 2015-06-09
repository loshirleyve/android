package com.yun9.jupiter.cache;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertValue;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关的缓存管理类，
 * 通过它保存/获取的缓存信息都与当前用户相关
 *
 * @author yun9
 */
public class UserDataCache extends AbsCache {

    private static final String PREFIX = "com.yun9.wservice.user";

    private String userId;

    private static UserDataCache instance;

    private static Map<String, UserDataCache> instances = new HashMap<>();

    public static UserDataCache getInstance() {
        return getInstance(null);
    }

    public static UserDataCache getInstance(String userid) {
        UserDataCache instance = null;

        if (!AssertValue.isNotNullAndNotEmpty(userid)){
            JupiterApplication jupiterApplication = JupiterApplication.mInstance;
            SessionManager sessionManager = jupiterApplication.getBeanManager().get(SessionManager.class);
            if(AssertValue.isNotNullAndNotEmpty(sessionManager.getUser().getId())){
                userid = sessionManager.getUser().getId();
            }else{
                return instance;
            }
        }

        synchronized (instances) {
            instance = instances.get(userid);
            if (!AssertValue.isNotNull(instance)) {
                instance = new UserDataCache(userid);
                instances.put(userid, instance);
            }
        }

        return instance;
    }

    private UserDataCache(String userId) {
        super(PREFIX +"."+ userId);
        this.userId = userId;
    }

}
