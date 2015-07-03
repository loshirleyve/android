package com.yun9.jupiter.http.support;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yun9.jupiter.http.ResponseCache;
import com.yun9.jupiter.model.CacheCtrlcode;
import com.yun9.jupiter.model.CacheCtrlcodeItem;
import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/6/17.
 */
public class DefaultResponseCache implements ResponseCache {

    private Map<String, CacheFile> cacheFiles = new HashMap<>();

    private Map<String, CacheUser> cacheUsers = new HashMap<>();

    private Map<String, CacheInst> cacheInsts = new HashMap<>();

    private Map<String, CacheCtrlcode> cacheCtrlcodes = new HashMap<>();

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

                if (ResponseCache.CACHE_TYPE_INST.equals(cacheType) && AssertValue.isNotNull(entry) && AssertValue.isNotNull(entry.getValue()) && AssertValue.isNotNull(entry.getValue().getAsJsonObject())) {
                    parseCacheInst(entry.getValue().getAsJsonObject());
                }

                if (ResponseCache.CACHE_TYPE_CTRL_CODE.equals(cacheType) && AssertValue.isNotNull(entry) && AssertValue.isNotNull(entry.getValue()) && AssertValue.isNotNull(entry.getValue().getAsJsonObject())) {
                    parseCacheCtrlcode(entry.getValue().getAsJsonObject());
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
                cacheUsers.put(id, cacheUser);
            }
        }
    }

    private void parseCacheInst(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> itemEntry : jsonObject.getAsJsonObject().entrySet()) {
            if (AssertValue.isNotNull(itemEntry) && AssertValue.isNotNull(itemEntry.getValue()) && AssertValue.isNotNull(itemEntry.getValue().getAsJsonObject())) {
                String id = itemEntry.getKey();
                CacheInst cacheInst = JsonUtil.jsonElementToBean(itemEntry.getValue(), CacheInst.class);
                cacheInsts.put(id, cacheInst);
            }
        }
    }

    private void parseCacheCtrlcode(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> itemEntry : jsonObject.getAsJsonObject().entrySet()) {
            if (AssertValue.isNotNull(itemEntry) && AssertValue.isNotNull(itemEntry.getValue()) && AssertValue.isNotNull(itemEntry.getValue().getAsJsonArray())) {
                String id = itemEntry.getKey();
                List<CacheCtrlcodeItem> ctrlcodes = null;
                try {
                    ctrlcodes = JsonUtil
                            .jsonToBeanList(itemEntry.getValue().getAsJsonArray().toString(), CacheCtrlcodeItem.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cacheCtrlcodes.put(id,new CacheCtrlcode(id,ctrlcodes));
            }
        }
    }

    public Map<String, CacheFile> getCacheFiles() {
        return cacheFiles;
    }

    public Map<String, CacheUser> getCacheUsers() {
        return cacheUsers;
    }

    public Map<String, CacheInst> getCacheInsts() {
        return cacheInsts;
    }

    @Override
    public Map<String, CacheCtrlcode> getCacheCtrlcodes() {
        return cacheCtrlcodes;
    }
}
