package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class PayRegisterCollectAnalysis implements Serializable {

    private String id;
    private String page;
    private String userid;
    private Double collectAmount;
    private String descr;

    public String getPage() {
        return page;
    }

    public PayRegisterCollectAnalysis setPage(String page) {
        this.page = page;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public PayRegisterCollectAnalysis setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public Double getCollectAmount() {
        return collectAmount;
    }

    public PayRegisterCollectAnalysis setCollectAmount(Double collectAmount) {
        this.collectAmount = collectAmount;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public PayRegisterCollectAnalysis setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public String getId() {
        return id;
    }

    public PayRegisterCollectAnalysis setId(String id) {
        this.id = id;
        return this;
    }
}
