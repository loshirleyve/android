package com.yun9.wservice.model;

import com.google.gson.JsonObject;
import com.yun9.jupiter.util.AssertValue;

import java.util.Map;

/**
 * Created by Leon on 15/5/27.
 */
public class PushMessageBean implements java.io.Serializable {


    private String desc;

    private Map<String, String> extra;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    public String getExtra(String key) {
        if (AssertValue.isNotNullAndNotEmpty(this.extra)) {
            return this.extra.get(key);
        } else {
            return null;
        }
    }
}
