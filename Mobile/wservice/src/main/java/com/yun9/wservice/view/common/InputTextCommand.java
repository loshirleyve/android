package com.yun9.wservice.view.common;

import com.yun9.jupiter.command.JupiterCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class InputTextCommand extends JupiterCommand {

    private String value;
    private Map<String,String> regularMap;

    public String getValue() {
        return value;
    }

    public InputTextCommand setValue(String value) {
        this.value = value;
        return this;
    }

    public InputTextCommand addRegular(String regular,String warning) {
        if (regularMap == null){
            regularMap = new HashMap<>();
        }
        regularMap.put(regular,warning);
        return this;
    }

    public Map<String, String> getRegularMap() {
        return regularMap;
    }

    public InputTextCommand setRegularMap(Map<String, String> regularMap) {
        this.regularMap = regularMap;
        return this;
    }
}
