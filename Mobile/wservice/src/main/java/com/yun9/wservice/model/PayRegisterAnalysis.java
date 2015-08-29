package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class PayRegisterAnalysis implements Serializable {

    private String page;
    private Double amount;
    private Double payAmount;
    private Double unPayAmount;

    public String getPage() {
        return page;
    }

    public PayRegisterAnalysis setPage(String page) {
        this.page = page;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public PayRegisterAnalysis setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public PayRegisterAnalysis setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
        return this;
    }

    public Double getUnPayAmount() {
        return unPayAmount;
    }

    public PayRegisterAnalysis setUnPayAmount(Double unPayAmount) {
        this.unPayAmount = unPayAmount;
        return this;
    }
}
