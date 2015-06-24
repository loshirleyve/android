package com.yun9.wservice.model;

/**
 * Created by Leon on 15/6/23.
 */
public class NewMsgCardAttachment implements java.io.Serializable {

    private String fileid;
    private String desc;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
