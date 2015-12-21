package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/12/21.
 */
public class PayRegisterCollectAnalysisUserDtos implements Serializable {

    private String page;
    private String userid;
    private Double collectAmount;
    private String descr;

    public String getPage() {
        return page;
    }

    public PayRegisterCollectAnalysisUserDtos setPage(String page) {
        this.page = page;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public PayRegisterCollectAnalysisUserDtos setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public Double getCollectAmount() {
        return collectAmount;
    }

    public PayRegisterCollectAnalysisUserDtos setCollectAmount(Double collectAmount) {
        this.collectAmount = collectAmount;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public PayRegisterCollectAnalysisUserDtos setDescr(String descr) {
        this.descr = descr;
        return this;
    }
}
