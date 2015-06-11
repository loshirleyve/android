package com.yun9.jupiter.form.model;

import com.google.gson.JsonElement;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class TextFormCellBean extends FormCellBean{

    private String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public void buildValueFromJson(JsonElement element) {
        if (element != null) {
            this.setValue(element.getAsString());
        }
    }
}
