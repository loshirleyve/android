package com.yun9.jupiter.http;

import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.model.CacheUser;

import java.util.Map;

/**
 * Created by Leon on 15/6/17.
 */
public interface ResponseCache {

    public static final String CACHE_TYPE_FILE = "file";

    public static final String CACHE_TYPE_USER = "user";

    public Map<String, CacheFile> getCacheFiles();

    public Map<String, CacheUser> getCacheUsers();

}
