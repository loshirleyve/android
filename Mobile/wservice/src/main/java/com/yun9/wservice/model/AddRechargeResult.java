package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 7/2/15.
 */
public class AddRechargeResult implements Serializable{

    private String rechargeid;
    private String rechargetypeid;
    private Double amount;
    private String callbackid;

    // 逻辑字段，非DB
    private String stateName;
    private String recharegeTypeName;

    public String getRechargeid() {
        return rechargeid;
    }

    public void setRechargeid(String rechargeid) {
        this.rechargeid = rechargeid;
    }

    public String getRechargetypeid() {
        return rechargetypeid;
    }

    public void setRechargetypeid(String rechargetypeid) {
        this.rechargetypeid = rechargetypeid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCallbackid() {
        return callbackid;
    }

    public void setCallbackid(String callbackid) {
        this.callbackid = callbackid;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getRecharegeTypeName() {
        return recharegeTypeName;
    }

    public void setRecharegeTypeName(String recharegeTypeName) {
        this.recharegeTypeName = recharegeTypeName;
    }
}
