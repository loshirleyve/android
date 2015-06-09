package com.yun9.wservice.cache;

import com.yun9.jupiter.cache.AbsCache;
import com.yun9.jupiter.model.User;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class UserCache extends AbsCache {

    private static final String CACHE_KEY = "users";
    private static UserCache instance;

    public static UserCache getInstance() {
        synchronized (UserCache.class) {
            if (instance == null) {
                instance = new UserCache();
            }

        }
        return instance;
    }

    public User getUser(String id) {
        return this.get(id,User.class);
    }

    private UserCache() {
        super(CACHE_KEY);
    }
}
