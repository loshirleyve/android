package com.yun9.wservice.cache;

import com.yun9.jupiter.cache.AbsCache;
import com.yun9.jupiter.model.Org;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class OrgCache extends AbsCache {

    private static final String CACHE_KEY = "orgs";
    private static OrgCache instance;

    public static OrgCache getInstance() {
        synchronized (OrgCache.class) {
            if (instance == null) {
                instance = new OrgCache();
            }

        }
        return instance;
    }

    public Org getOrg(String id) {
        return this.get(id,Org.class);
    }

    private OrgCache() {
        super(CACHE_KEY);
    }
}
