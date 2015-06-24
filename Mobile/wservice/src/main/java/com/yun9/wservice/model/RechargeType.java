package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/6/24.
 */
public class RechargeType implements Serializable{

    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private int disabled;
    private String remark;
    private String id;
    private String instid;
    private String rechargeno;
    private String rechargename;
    private int timeoutoffset;
    private String confirmtype;
    private String warmthwarning;

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

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Long updatedate) {
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

    public String getRechargeno() {
        return rechargeno;
    }

    public void setRechargeno(String rechargeno) {
        this.rechargeno = rechargeno;
    }

    public String getRechargename() {
        return rechargename;
    }

    public void setRechargename(String rechargename) {
        this.rechargename = rechargename;
    }

    public int getTimeoutoffset() {
        return timeoutoffset;
    }

    public void setTimeoutoffset(int timeoutoffset) {
        this.timeoutoffset = timeoutoffset;
    }

    public String getConfirmtype() {
        return confirmtype;
    }

    public void setConfirmtype(String confirmtype) {
        this.confirmtype = confirmtype;
    }

    public String getWarmthwarning() {
        return warmthwarning;
    }

    public void setWarmthwarning(String warmthwarning) {
        this.warmthwarning = warmthwarning;
    }
}
