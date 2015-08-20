package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class Balance implements Serializable {

    private String page;
    private String id;
    private String accountid;
    private String owner;
    private String state;
    private Double balance;
    private Double issueAmount;
    private Double useAmount;
    private Long expirydate;
    private String createby;
    private Long createdate;
    private String remark;

    public String getPage() {
        return page;
    }

    public Balance setPage(String page) {
        this.page = page;
        return this;
    }

    public String getId() {
        return id;
    }

    public Balance setId(String id) {
        this.id = id;
        return this;
    }

    public String getAccountid() {
        return accountid;
    }

    public Balance setAccountid(String accountid) {
        this.accountid = accountid;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public Balance setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getState() {
        return state;
    }

    public Balance setState(String state) {
        this.state = state;
        return this;
    }

    public Double getBalance() {
        return balance;
    }

    public Balance setBalance(Double balance) {
        this.balance = balance;
        return this;
    }

    public Double getIssueAmount() {
        return issueAmount;
    }

    public Balance setIssueAmount(Double issueAmount) {
        this.issueAmount = issueAmount;
        return this;
    }

    public Double getUseAmount() {
        return useAmount;
    }

    public Balance setUseAmount(Double useAmount) {
        this.useAmount = useAmount;
        return this;
    }

    public Long getExpirydate() {
        return expirydate;
    }

    public Balance setExpirydate(Long expirydate) {
        this.expirydate = expirydate;
        return this;
    }

    public String getCreateby() {
        return createby;
    }

    public Balance setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public Balance setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Balance setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
