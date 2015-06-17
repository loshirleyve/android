package com.yun9.jupiter.cache;

import com.yun9.jupiter.model.CacheFile;

/**
 * Created by huangbinglong on 15/5/22.
 */
public class FileCache extends AbsCache {
    private static final String CACHE_KEY = "yun9_file_id";
    private static FileCache instance;

    public static FileCache getInstance() {
        synchronized (FileCache.class) {
            if (instance == null) {
                instance = new FileCache();
            }
        }
        return instance;
    }

    private FileCache() {
        super(CACHE_KEY);
    }

    public CacheFile getFile(String fileid) {
        return this.get(fileid, CacheFile.class);
    }

    public void putFile(String fileid, CacheFile cacheFile) {
        this.put(fileid, cacheFile);
    }
}
