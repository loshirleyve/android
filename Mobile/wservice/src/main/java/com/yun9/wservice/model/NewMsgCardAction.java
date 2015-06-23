package com.yun9.wservice.model;

/**
 * Created by Leon on 15/6/23.
 */
public class NewMsgCardAction implements java.io.Serializable {
    private String label;
    private String name;

    private NewMsgCardActionParam params = new NewMsgCardActionParam();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NewMsgCardActionParam getParams() {
        return params;
    }

    public void setParams(NewMsgCardActionParam params) {
        this.params = params;
    }
}
