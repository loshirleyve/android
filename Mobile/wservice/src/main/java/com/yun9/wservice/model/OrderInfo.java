package com.yun9.wservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by rxy on 15/8/20.
 */
public class OrderInfo implements Serializable {

    private String orderid;
    private String name;
    private String imgid;
    private String introduce;
    private String ordersn;
    private String state;
    private double orderamount;
    private double factamount;
    private int commitdoc = 0;
    private String instid;
    private String buyerinstid;
    private String proxyinstid;
    private String proxyman;
    private String salesmanid;
    private String purchase;
    private long createdate;
    private String createby;
    private String paystate;
    private long begindate;
    private long enddate;
    private List<WorkorderDto> workorders;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public double getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(double orderamount) {
        this.orderamount = orderamount;
    }

    public double getFactamount() {
        return factamount;
    }

    public void setFactamount(double factamount) {
        this.factamount = factamount;
    }

    public int getCommitdoc() {
        return commitdoc;
    }

    public void setCommitdoc(int commitdoc) {
        this.commitdoc = commitdoc;
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

    public String getProxyinstid() {
        return proxyinstid;
    }

    public void setProxyinstid(String proxyinstid) {
        this.proxyinstid = proxyinstid;
    }

    public String getProxyman() {
        return proxyman;
    }

    public void setProxyman(String proxyman) {
        this.proxyman = proxyman;
    }

    public String getSalesmanid() {
        return salesmanid;
    }

    public void setSalesmanid(String salesmanid) {
        this.salesmanid = salesmanid;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    public long getBegindate() {
        return begindate;
    }

    public void setBegindate(long begindate) {
        this.begindate = begindate;
    }

    public long getEnddate() {
        return enddate;
    }

    public void setEnddate(long enddate) {
        this.enddate = enddate;
    }

    public List<WorkorderDto> getWorkorders() {
        return workorders;
    }

    public void setWorkorders(List<WorkorderDto> workorders) {
        this.workorders = workorders;
    }
}
