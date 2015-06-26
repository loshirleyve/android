package com.yun9.jupiter.cache;

import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.util.AssertValue;

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
        if (AssertValue.isNotNullAndNotEmpty(fileid)) {
            return this.get(fileid, CacheFile.class);
        }else{
            return null;
        }
    }

    public void putFile(String fileid, CacheFile cacheFile) {
        this.put(fileid, cacheFile);
    }

    public String getFileUrl(String fileid) {
        CacheFile cacheFile = this.getFile(fileid);
        if (AssertValue.isNotNull(cacheFile)) {
            return cacheFile.getUrl();
        }

        return null;
    }


    public String getFileThumbnailUrl(String fileid) {
        CacheFile cacheFile = this.getFile(fileid);
        if (AssertValue.isNotNull(cacheFile)) {
            return cacheFile.getThumbnailUrl();
        }

        return null;
    }
}
