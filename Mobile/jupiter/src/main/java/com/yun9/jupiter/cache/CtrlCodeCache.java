package com.yun9.jupiter.cache;

import com.yun9.jupiter.model.CacheCtrlcode;
import com.yun9.jupiter.model.CacheCtrlcodeItem;

/**
 * Created by huangbinglong on 7/3/15.
 */
public class CtrlCodeCache extends AbsCache {

    private static final String CACHE_KEY = "yun9_ctrl_code";
    private static CtrlCodeCache instance;

    public static CtrlCodeCache getInstance() {
        synchronized (CtrlCodeCache.class) {
            if (instance == null) {
                instance = new CtrlCodeCache();
            }
        }
        return instance;
    }

    public void put(String defNo,CacheCtrlcode ctrlcode) {
        this.put(defNo,(Object)ctrlcode);
    }

    public CacheCtrlcode getCtrlCode(String defNo) {
        return this.get(defNo,CacheCtrlcode.class);
    }

    public CacheCtrlcodeItem getCtrlCodeItem(String defNo,String no) {
        CacheCtrlcode ctrlcode = getCtrlCode(defNo);
        if (ctrlcode != null){
            return ctrlcode.getItem(no);
        }
        return null;
    }

    public String getCtrlcodeName(String defNo,String no) {
        CacheCtrlcodeItem item = getCtrlCodeItem(defNo,no);
        if (item != null){
            return item.getName();
        }
        return null;
    }

    private CtrlCodeCache() {
        super(CACHE_KEY);
    }
}
