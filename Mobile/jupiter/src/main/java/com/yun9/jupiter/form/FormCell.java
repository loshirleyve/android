package com.yun9.jupiter.form;

import android.content.Context;
import android.view.View;

/**
 * Created by Leon on 15/5/25.
 */
public abstract class FormCell implements java.io.Serializable{

    private String label;
    private String key;
    private boolean readonly;
    private boolean required;
    private Class<? extends FormCell> type;

    public void init(){

    };

    public abstract View getCellView(Context context);

    public abstract void edit(boolean edit);

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

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Class<? extends FormCell> getType() {
        return type;
    }

    public void setType(Class<? extends FormCell> type) {
        this.type = type;
    }
}
