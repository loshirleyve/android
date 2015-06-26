package com.yun9.jupiter.cache;

import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class UserCache extends AbsCache {

    private static final String CACHE_KEY = "yun9_user_id";
    private static UserCache instance;

    public static UserCache getInstance() {
        synchronized (UserCache.class) {
            if (instance == null) {
                instance = new UserCache();
            }

        }
        return instance;
    }

    public CacheUser getUser(String userid) {
        if (AssertValue.isNotNullAndNotEmpty(userid)) {
            return this.get(userid, CacheUser.class);
        }else{
            return null;
        }
    }

    public void putUser(String userid, CacheUser cacheUser) {
        this.put(userid, cacheUser);
    }

    private UserCache() {
        super(CACHE_KEY);
    }
}
