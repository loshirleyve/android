package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/4/15.
 */
public class BizMdInstTransfer implements Serializable{

    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private int disabled;
    private String remark;
    private String id;
    private int createtimestamp;
    private int updatetimestamp;
    private String orderid;
    private String transferid;
    private String inputvalue;

    public String getCreateby() {
        return createby;
    }

    public BizMdInstTransfer setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getUpdateby() {
        return updateby;
    }

    public BizMdInstTransfer setUpdateby(String updateby) {
        this.updateby = updateby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public BizMdInstTransfer setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public BizMdInstTransfer setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public int getDisabled() {
        return disabled;
    }

    public BizMdInstTransfer setDisabled(int disabled) {
        this.disabled = disabled;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public BizMdInstTransfer setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getId() {
        return id;
    }

    public BizMdInstTransfer setId(String id) {
        this.id = id;
        return this;
    }

    public int getCreatetimestamp() {
        return createtimestamp;
    }

    public BizMdInstTransfer setCreatetimestamp(int createtimestamp) {
        this.createtimestamp = createtimestamp;
        return this;
    }

    public int getUpdatetimestamp() {
        return updatetimestamp;
    }

    public BizMdInstTransfer setUpdatetimestamp(int updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
        return this;
    }

    public String getOrderid() {
        return orderid;
    }

    public BizMdInstTransfer setOrderid(String orderid) {
        this.orderid = orderid;
        return this;
    }

    public String getTransferid() {
        return transferid;
    }

    public BizMdInstTransfer setTransferid(String transferid) {
        this.transferid = transferid;
        return this;
    }

    public String getInputvalue() {
        return inputvalue;
    }

    public BizMdInstTransfer setInputvalue(String inputvalue) {
        this.inputvalue = inputvalue;
        return this;
    }
}
