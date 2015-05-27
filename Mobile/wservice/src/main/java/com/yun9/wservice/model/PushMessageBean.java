package com.yun9.wservice.model;

import com.google.gson.JsonObject;

/**
 * Created by Leon on 15/5/27.
 */
public class PushMessageBean {

    private String title;

    private String desc;

    private JsonObject content;

    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public JsonObject getContent() {
        return content;
    }

    public void setContent(JsonObject content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
