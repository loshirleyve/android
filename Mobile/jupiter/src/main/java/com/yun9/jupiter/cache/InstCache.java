package com.yun9.jupiter.cache;

import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class InstCache extends AbsCache {

    private static final String CACHE_KEY = "yun9_inst_id";
    private static InstCache instance;

    public static InstCache getInstance() {
        synchronized (InstCache.class) {
            if (instance == null) {
                instance = new InstCache();
            }

        }
        return instance;
    }

    public CacheInst getInst(String instid) {
        if (AssertValue.isNotNullAndNotEmpty(instid)) {
            return this.get(instid, CacheInst.class);
        }else{
            return null;
        }
    }

    public void putInst(String userid, CacheInst cacheInst) {
        this.put(userid, cacheInst);
    }

    private InstCache() {
        super(CACHE_KEY);
    }
}
