package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PayRegisterCollect implements Serializable {

    private String page;
    private String id;
    private String payregisterid;
    private String state;
    private String ptcode;
    private String ptname;
    private String ptdescr;
    private String paymodetypeid;
    private String ptimgid;
    private Double amount;
    private String collectuserid;
    private String createby;
    private Long createdate;
    private String remark;
    private String paymodetype;

    public String getPage() {
        return page;
    }

    public PayRegisterCollect setPage(String page) {
        this.page = page;
        return this;
    }

    public String getId() {
        return id;
    }

    public PayRegisterCollect setId(String id) {
        this.id = id;
        return this;
    }

    public String getPayregisterid() {
        return payregisterid;
    }

    public PayRegisterCollect setPayregisterid(String payregisterid) {
        this.payregisterid = payregisterid;
        return this;
    }

    public String getState() {
        return state;
    }

    public PayRegisterCollect setState(String state) {
        this.state = state;
        return this;
    }

    public String getPtcode() {
        return ptcode;
    }

    public PayRegisterCollect setPtcode(String ptcode) {
        this.ptcode = ptcode;
        return this;
    }

    public String getPtname() {
        return ptname;
    }

    public PayRegisterCollect setPtname(String ptname) {
        this.ptname = ptname;
        return this;
    }

    public String getPtdescr() {
        return ptdescr;
    }

    public PayRegisterCollect setPtdescr(String ptdescr) {
        this.ptdescr = ptdescr;
        return this;
    }

    public String getPaymodetypeid() {
        return paymodetypeid;
    }

    public PayRegisterCollect setPaymodetypeid(String paymodetypeid) {
        this.paymodetypeid = paymodetypeid;
        return this;
    }

    public String getPtimgid() {
        return ptimgid;
    }

    public PayRegisterCollect setPtimgid(String ptimgid) {
        this.ptimgid = ptimgid;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public PayRegisterCollect setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getCollectuserid() {
        return collectuserid;
    }

    public PayRegisterCollect setCollectuserid(String collectuserid) {
        this.collectuserid = collectuserid;
        return this;
    }

    public String getCreateby() {
        return createby;
    }

    public PayRegisterCollect setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public PayRegisterCollect setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PayRegisterCollect setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getPaymodetype() {
        return paymodetype;
    }

    public PayRegisterCollect setPaymodetype(String paymodetype) {
        this.paymodetype = paymodetype;
        return this;
    }
}
