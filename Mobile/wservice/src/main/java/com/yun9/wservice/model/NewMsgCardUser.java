package com.yun9.wservice.model;

/**
 * Created by Leon on 15/6/23.
 */
public class NewMsgCardUser implements java.io.Serializable {
    public static final String TYPE_USER= "user";
    public static final String TYPE_ORG= "org";

    private String userid;
    private String type;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
