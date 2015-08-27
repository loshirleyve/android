package com.yun9.wservice.cache;

import com.yun9.jupiter.model.CacheInst;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/27.
 */
public interface InstCacheExtensionCallback extends Serializable {

    void callback(CacheInst cacheInst);
}
