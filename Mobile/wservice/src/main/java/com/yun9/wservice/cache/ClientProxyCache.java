package com.yun9.wservice.cache;

import com.yun9.jupiter.cache.AbsCache;

/**
 * Created by huangbinglong on 15/6/29.
 */
public class ClientProxyCache extends AbsCache{

    private static final String CACHE_KEY = "client_proxy";

    private static final String CLIENT_PROXY_KEY = "client_proxy";

    private static ClientProxyCache instance;

    public static ClientProxyCache getInstance() {
        synchronized (ClientProxyCache.class) {
            if (instance == null) {
                instance = new ClientProxyCache();
            }
        }
        return instance;
    }

    public boolean isProxy() {
        return this.contains(CLIENT_PROXY_KEY)
                && this.getProxy() != null;
    }

    public CacheClientProxy getProxy() {
        return this.get(CLIENT_PROXY_KEY, CacheClientProxy.class);
    }

    public void putClientProxy(CacheClientProxy clientProxy) {
        if (clientProxy != null){
            this.put(CLIENT_PROXY_KEY,clientProxy);
        } else {
            removeProxy();
        }
    }

    public void removeProxy() {
        this.remove(CLIENT_PROXY_KEY);
    }

    private ClientProxyCache() {
        super(CACHE_KEY);
    }
}
