package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * 订单基础信息，用于订单列表界面
 * Created by huangbinglong on 15/6/25.
 */
public class OrderBaseInfo implements Serializable{

    private String id;
    private String productname;
    private String ordersn;
    private String state;
    private Double orderamount;
    private int commitattachment;
    private String instid;
    private String buyerinstid;
    private String provideinstid;
    private String salesmanid;
    private String adviseruserid;
    private Long createdate;
    private String createby;
    private int paystate;
    private String productimgid;
    private String productIntroduce;

    public String getProductIntroduce() {
        return productIntroduce;
    }

    public OrderBaseInfo setProductIntroduce(String productIntroduce) {
        this.productIntroduce = productIntroduce;
        return this;
    }

    public String getId() {
        return id;
    }

    public OrderBaseInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Double getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(Double orderamount) {
        this.orderamount = orderamount;
    }

    public int getCommitattachment() {
        return commitattachment;
    }

    public void setCommitattachment(int commitattachment) {
        this.commitattachment = commitattachment;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getBuyerinstid() {
        return buyerinstid;
    }

    public void setBuyerinstid(String buyerinstid) {
        this.buyerinstid = buyerinstid;
    }

    public String getProvideinstid() {
        return provideinstid;
    }

    public void setProvideinstid(String provideinstid) {
        this.provideinstid = provideinstid;
    }

    public String getSalesmanid() {
        return salesmanid;
    }

    public void setSalesmanid(String salesmanid) {
        this.salesmanid = salesmanid;
    }

    public String getAdviseruserid() {
        return adviseruserid;
    }

    public void setAdviseruserid(String adviseruserid) {
        this.adviseruserid = adviseruserid;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public int getPaystate() {
        return paystate;
    }

    public void setPaystate(int paystate) {
        this.paystate = paystate;
    }

    public String getProductimgid() {
        return productimgid;
    }

    public void setProductimgid(String productimgid) {
        this.productimgid = productimgid;
    }
}
