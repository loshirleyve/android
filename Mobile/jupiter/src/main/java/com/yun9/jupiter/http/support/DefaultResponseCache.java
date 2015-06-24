package com.yun9.jupiter.http.support;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yun9.jupiter.http.ResponseCache;
import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leon on 15/6/17.
 */
public class DefaultResponseCache implements ResponseCache {

    private Map<String, CacheFile> cacheFiles = new HashMap<>();

    private Map<String,CacheUser> cacheUsers = new HashMap<>();

    public DefaultResponseCache(String cacheJson) {
        JsonObject jsonObject = JsonUtil.fromString(cacheJson);

        if (AssertValue.isNotNull(jsonObject)) {
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                String cacheType = entry.getKey();
                if (ResponseCache.CACHE_TYPE_FILE.equals(cacheType) && AssertValue.isNotNull(entry) && AssertValue.isNotNull(entry.getValue()) && AssertValue.isNotNull(entry.getValue().getAsJsonObject())) {
                    parseCacheFile(entry.getValue().getAsJsonObject());
                }

                if (ResponseCache.CACHE_TYPE_USER.equals(cacheType) && AssertValue.isNotNull(entry) && AssertValue.isNotNull(entry.getValue()) && AssertValue.isNotNull(entry.getValue().getAsJsonObject())) {
                    parseCacheUser(entry.getValue().getAsJsonObject());
                }
            }
        }
    }

    private void parseCacheFile(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> itemEntry : jsonObject.getAsJsonObject().entrySet()) {
            if (AssertValue.isNotNull(itemEntry) && AssertValue.isNotNull(itemEntry.getValue()) && AssertValue.isNotNull(itemEntry.getValue().getAsJsonObject())) {
                String id = itemEntry.getKey();
                CacheFile cacheFile = JsonUtil.jsonElementToBean(itemEntry.getValue(), CacheFile.class);
                cacheFiles.put(id, cacheFile);
            }
        }
    }

    private void parseCacheUser(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> itemEntry : jsonObject.getAsJsonObject().entrySet()) {
            if (AssertValue.isNotNull(itemEntry) && AssertValue.isNotNull(itemEntry.getValue()) && AssertValue.isNotNull(itemEntry.getValue().getAsJsonObject())) {
                String id = itemEntry.getKey();
                CacheUser cacheUser = JsonUtil.jsonElementToBean(itemEntry.getValue(), CacheUser.class);
                cacheUsers.put(id,cacheUser);
            }
        }
    }

    public Map<String, CacheFile> getCacheFiles() {
        return cacheFiles;
    }

    public Map<String, CacheUser> getCacheUsers() {
        return cacheUsers;
    }
}
