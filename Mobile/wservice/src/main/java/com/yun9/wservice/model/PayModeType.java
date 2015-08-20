package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PayModeType implements Serializable {

    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private Long createtimestamp;
    private Long updatetimestamp;
    private int disabled;
    private String remark;
    private String id;
    private String code;
    private String name;
    private String paymodeid;
    private String descr;
    private String imgid;
    private int timeoutsec;
    private int sort;

    public String getCreateby() {
        return createby;
    }

    public PayModeType setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getUpdateby() {
        return updateby;
    }

    public PayModeType setUpdateby(String updateby) {
        this.updateby = updateby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public PayModeType setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public PayModeType setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public Long getCreatetimestamp() {
        return createtimestamp;
    }

    public PayModeType setCreatetimestamp(Long createtimestamp) {
        this.createtimestamp = createtimestamp;
        return this;
    }

    public Long getUpdatetimestamp() {
        return updatetimestamp;
    }

    public PayModeType setUpdatetimestamp(Long updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
        return this;
    }

    public int getDisabled() {
        return disabled;
    }

    public PayModeType setDisabled(int disabled) {
        this.disabled = disabled;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PayModeType setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getId() {
        return id;
    }

    public PayModeType setId(String id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PayModeType setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public PayModeType setName(String name) {
        this.name = name;
        return this;
    }

    public String getPaymodeid() {
        return paymodeid;
    }

    public PayModeType setPaymodeid(String paymodeid) {
        this.paymodeid = paymodeid;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public PayModeType setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public String getImgid() {
        return imgid;
    }

    public PayModeType setImgid(String imgid) {
        this.imgid = imgid;
        return this;
    }

    public int getTimeoutsec() {
        return timeoutsec;
    }

    public PayModeType setTimeoutsec(int timeoutsec) {
        this.timeoutsec = timeoutsec;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public PayModeType setSort(int sort) {
        this.sort = sort;
        return this;
    }
}
