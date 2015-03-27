package com.yun9.mobile.framework.model;

/**
 * Created by User on 2014/11/4.
 * 考勤
 */
public class CheckOnWorkAttend {
    private String createby;
    private String updateby;
    private long createdate;
    private long updatedate;
    private int disabled;
    private String remark;
    private String id;
    private String instid;
    private String userid;
    private long  workdate;
    private String shiftid;
    private long checkdatetime;
    private String checklocationx;
    private String checklocationy;
    private String checklocationlabel;


    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public long getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(long updatedate) {
        this.updatedate = updatedate;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getWorkdate() {
        return workdate;
    }

    public void setWorkdate(long workdate) {
        this.workdate = workdate;
    }

    public long getCheckdatetime() {
        return checkdatetime;
    }

    public void setCheckdatetime(long checkdatetime) {
        this.checkdatetime = checkdatetime;
    }

    public String getShiftid() {
        return shiftid;
    }

    public void setShiftid(String shiftid) {
        this.shiftid = shiftid;
    }

    public String getChecklocationy() {
        return checklocationy;
    }

    public void setChecklocationy(String checklocationy) {
        this.checklocationy = checklocationy;
    }

    public String getChecklocationx() {
        return checklocationx;
    }

    public void setChecklocationx(String checklocationx) {
        this.checklocationx = checklocationx;
    }

    public String getChecklocationlabel() {
        return checklocationlabel;
    }

    public void setChecklocationlabel(String checklocationlabel) {
        this.checklocationlabel = checklocationlabel;
    }
}
