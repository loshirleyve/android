package com.yun9.jupiter.model;

/**
 * Created by Leon on 15/6/10.
 */
public class UserContact implements java.io.Serializable{
    private String id;
    private String userid;
    private String contactkey;
    private String contentlabel;
    private String contactvalue;
    private String remark;
    private int disabled;
    private long createdate;
    private String updateby;
    private String createby;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContactkey() {
        return contactkey;
    }

    public void setContactkey(String contactkey) {
        this.contactkey = contactkey;
    }

    public String getContentlabel() {
        return contentlabel;
    }

    public void setContentlabel(String contentlabel) {
        this.contentlabel = contentlabel;
    }

    public String getContactvalue() {
        return contactvalue;
    }

    public void setContactvalue(String contactvalue) {
        this.contactvalue = contactvalue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }
}

