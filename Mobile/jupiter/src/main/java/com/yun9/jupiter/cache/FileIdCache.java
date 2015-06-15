package com.yun9.jupiter.cache;

/**
 * Created by huangbinglong on 15/5/22.
 */
public class FileIdCache extends AbsCache{
    private static final String CACHE_KEY = "yun9_file_id";
    private static FileIdCache instance;

    public static FileIdCache getInstance() {
        synchronized (FileIdCache.class) {
            if (instance == null) {
                instance = new FileIdCache();
            }

        }
        return instance;
    }

    private FileIdCache() {
        super(CACHE_KEY);
    }
}
