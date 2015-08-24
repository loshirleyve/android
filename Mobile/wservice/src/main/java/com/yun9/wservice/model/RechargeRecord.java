package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 7/13/15.
 */
public class RechargeRecord implements Serializable{

    private String id;
    private String own;
    private String accounttype;
    private Double amount;
    private String typeid;
    private String state;
    private Long expirydate;
    private String typeName;
    private String typecode;

    public String getId() {
        return id;
    }

    public RechargeRecord setId(String id) {
        this.id = id;
        return this;
    }

    public String getOwn() {
        return own;
    }

    public RechargeRecord setOwn(String own) {
        this.own = own;
        return this;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public RechargeRecord setAccounttype(String accounttype) {
        this.accounttype = accounttype;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public RechargeRecord setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getTypeid() {
        return typeid;
    }

    public RechargeRecord setTypeid(String typeid) {
        this.typeid = typeid;
        return this;
    }

    public String getState() {
        return state;
    }

    public RechargeRecord setState(String state) {
        this.state = state;
        return this;
    }

    public Long getExpirydate() {
        return expirydate;
    }

    public RechargeRecord setExpirydate(Long expirydate) {
        this.expirydate = expirydate;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public RechargeRecord setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public String getTypecode() {
        return typecode;
    }

    public RechargeRecord setTypecode(String typecode) {
        this.typecode = typecode;
        return this;
    }
}
