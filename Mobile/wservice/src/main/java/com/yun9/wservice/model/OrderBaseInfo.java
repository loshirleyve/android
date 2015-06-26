package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * 订单基础信息，用于订单列表界面
 * Created by huangbinglong on 15/6/25.
 */
public class OrderBaseInfo implements Serializable{

    private String productimage;
    private String ordersn;
    private double goodsamount;
    private Long createdate;
    private String instid;
    private int goodsnum;
    private String state;
    private String statename;
    private String orderid;
    private String productdesc;
    private String adviseruserid;
    private String productname;
    private String customername;

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public double getGoodsamount() {
        return goodsamount;
    }

    public void setGoodsamount(double goodsamount) {
        this.goodsamount = goodsamount;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public int getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(int goodsnum) {
        this.goodsnum = goodsnum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getAdviseruserid() {
        return adviseruserid;
    }

    public void setAdviseruserid(String adviseruserid) {
        this.adviseruserid = adviseruserid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }
}
