package com.yun9.jupiter.form.model;

import com.google.gson.JsonElement;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class TextFormCellBean extends FormCellBean{

    private String defaultValue;

    private String regular;

    private String errorMessage;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void buildValueFromJson(JsonElement element) {
        if (element != null) {
            this.setValue(element.getAsString());
        }
    }
}
