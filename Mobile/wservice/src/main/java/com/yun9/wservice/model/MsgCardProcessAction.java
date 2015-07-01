package com.yun9.wservice.model;

/**
 * Created by Leon on 15/6/29.
 */
public class MsgCardProcessAction implements java.io.Serializable {

    public MsgCardProcessAction(String name, String actionType) {
        this.name = name;
        this.actionType = actionType;
    }

    private String name;

    private String actionType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
