package com.yun9.jupiter.form.model;

import android.content.Context;
import android.view.View;

import com.yun9.jupiter.form.FormCell;

import java.util.Objects;

/**
 * Created by huangbinglong on 15/5/28.
 */
public class FormCellBean implements java.io.Serializable{
    private String label;
    private String key;
    private boolean readonly;
    private boolean required;
    private Object value;
    private String type;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
