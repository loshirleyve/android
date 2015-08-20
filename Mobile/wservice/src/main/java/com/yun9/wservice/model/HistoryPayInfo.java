package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class HistoryPayInfo implements Serializable{

    private String page;
    private String id;
    private String instid;
    private String businessKey;
    private String paymodeid;
    private String paymodeName;
    private String paymodeCode;
    private String paymodeDescr;
    private String paymodeType;
    private int complete;
    private String source;
    private String sourceid;
    private Double amount;
    private Double payamount;
    private Double lockamount;
    private Double unPayamount;
    private Long expirydate;
    private String createby;
    private Long createdate;
    private String remark;
    private List<PayRegisterCollect> payRegisterCollects;

    public String getPage() {
        return page;
    }

    public HistoryPayInfo setPage(String page) {
        this.page = page;
        return this;
    }

    public String getId() {
        return id;
    }

    public HistoryPayInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public HistoryPayInfo setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public HistoryPayInfo setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public String getPaymodeid() {
        return paymodeid;
    }

    public HistoryPayInfo setPaymodeid(String paymodeid) {
        this.paymodeid = paymodeid;
        return this;
    }

    public String getPaymodeName() {
        return paymodeName;
    }

    public HistoryPayInfo setPaymodeName(String paymodeName) {
        this.paymodeName = paymodeName;
        return this;
    }

    public String getPaymodeCode() {
        return paymodeCode;
    }

    public HistoryPayInfo setPaymodeCode(String paymodeCode) {
        this.paymodeCode = paymodeCode;
        return this;
    }

    public String getPaymodeDescr() {
        return paymodeDescr;
    }

    public HistoryPayInfo setPaymodeDescr(String paymodeDescr) {
        this.paymodeDescr = paymodeDescr;
        return this;
    }

    public String getPaymodeType() {
        return paymodeType;
    }

    public HistoryPayInfo setPaymodeType(String paymodeType) {
        this.paymodeType = paymodeType;
        return this;
    }

    public int getComplete() {
        return complete;
    }

    public HistoryPayInfo setComplete(int complete) {
        this.complete = complete;
        return this;
    }

    public String getSource() {
        return source;
    }

    public HistoryPayInfo setSource(String source) {
        this.source = source;
        return this;
    }

    public String getSourceid() {
        return sourceid;
    }

    public HistoryPayInfo setSourceid(String sourceid) {
        this.sourceid = sourceid;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public HistoryPayInfo setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Double getPayamount() {
        return payamount;
    }

    public HistoryPayInfo setPayamount(Double payamount) {
        this.payamount = payamount;
        return this;
    }

    public Double getLockamount() {
        return lockamount;
    }

    public HistoryPayInfo setLockamount(Double lockamount) {
        this.lockamount = lockamount;
        return this;
    }

    public Double getUnPayamount() {
        return unPayamount;
    }

    public HistoryPayInfo setUnPayamount(Double unPayamount) {
        this.unPayamount = unPayamount;
        return this;
    }

    public Long getExpirydate() {
        return expirydate;
    }

    public HistoryPayInfo setExpirydate(Long expirydate) {
        this.expirydate = expirydate;
        return this;
    }

    public String getCreateby() {
        return createby;
    }

    public HistoryPayInfo setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public HistoryPayInfo setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public HistoryPayInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public List<PayRegisterCollect> getPayRegisterCollects() {
        return payRegisterCollects;
    }

    public HistoryPayInfo setPayRegisterCollects(List<PayRegisterCollect> payRegisterCollects) {
        this.payRegisterCollects = payRegisterCollects;
        return this;
    }
}
