package com.yun9.wservice.model;

import com.yun9.jupiter.util.AssertValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leon on 15/6/23.
 */
public class NewMsgCardActionParam implements java.io.Serializable {
    private String type;
    private Map<String, String> header;
    private String actors;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    private void putHeader(String key, String value) {
        if (!AssertValue.isNotNull(header))
            header = new HashMap<>();

        header.put(key, value);
    }

    private void putActor(String actorid) {
        if (AssertValue.isNotNull(actors)) {
            actors = actors + "," + actorid;
        } else {
            actors = actorid;
        }
    }
}
