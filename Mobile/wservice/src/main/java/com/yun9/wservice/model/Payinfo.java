package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/24.
 */
public class Payinfo implements Serializable{

    private String source;
    private String sourceValue;
    private String instId;
    private String title;
    private String subtitle;
    private double payableAmount;
    private double useBalance;
    private double unpayAmount;
    private String sourceSn;
    private Balance balance;
    private PayMode payMode;
    private List<PayMode> paymodes;

    public String getSource() {
        return source;
    }

    public Payinfo setSource(String source) {
        this.source = source;
        return this;
    }

    public String getSourceValue() {
        return sourceValue;
    }

    public Payinfo setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
        return this;
    }

    public String getInstId() {
        return instId;
    }

    public Payinfo setInstId(String instId) {
        this.instId = instId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Payinfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public Payinfo setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public double getPayableAmount() {
        return payableAmount;
    }

    public Payinfo setPayableAmount(double payableAmount) {
        this.payableAmount = payableAmount;
        return this;
    }

    public double getUseBalance() {
        return useBalance;
    }

    public Payinfo setUseBalance(double useBalance) {
        this.useBalance = useBalance;
        return this;
    }

    public double getUnpayAmount() {
        return unpayAmount;
    }

    public Payinfo setUnpayAmount(double unpayAmount) {
        this.unpayAmount = unpayAmount;
        return this;
    }

    public String getSourceSn() {
        return sourceSn;
    }

    public Payinfo setSourceSn(String sourceSn) {
        this.sourceSn = sourceSn;
        return this;
    }

    public Balance getBalance() {
        return balance;
    }

    public Payinfo setBalance(Balance balance) {
        this.balance = balance;
        return this;
    }

    public PayMode getPayMode() {
        return payMode;
    }

    public Payinfo setPayMode(PayMode payMode) {
        this.payMode = payMode;
        return this;
    }

    public List<PayMode> getPaymodes() {
        return paymodes;
    }

    public Payinfo setPaymodes(List<PayMode> paymodes) {
        this.paymodes = paymodes;
        return this;
    }
}
