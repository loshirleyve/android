package com.yun9.wservice.model;

/**
 * Created by Leon on 15/6/28.
 */
public class MsgCardActor implements java.io.Serializable {

    private String msgCardId;
    private String userId;
    private String userType;

    public String getMsgCardId() {
        return msgCardId;
    }

    public void setMsgCardId(String msgCardId) {
        this.msgCardId = msgCardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
