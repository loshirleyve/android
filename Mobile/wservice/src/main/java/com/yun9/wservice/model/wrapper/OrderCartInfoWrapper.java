package com.yun9.wservice.model.wrapper;

import com.yun9.wservice.model.OrderCartInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 7/7/15.
 */
public class OrderCartInfoWrapper implements Serializable{

    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private Long createtimestamp;
    private Long updatetimestamp;
    private String disabled;
    private String remark;
    private String id;
    private String instid;
    private String buyerinstid;
    private String sn;
    private String mode;
    private String state;
    private Long activatedate;
    private String salesmanid;
    private Double saleamount;
    private Double factamount;
    private int commitdoc;
    private String paystate;
    private int readstate;
    private String proxyinstid;
    private String proxyman;
    private String purchase;
    private String name;
    private String imgid;
    private String introduce;
    private String page;
    private Long begindate;
    private Long enddate;
    private String preferentialInfo;

    public String getCreateby() {
        return createby;
    }

    public OrderCartInfoWrapper setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getUpdateby() {
        return updateby;
    }

    public OrderCartInfoWrapper setUpdateby(String updateby) {
        this.updateby = updateby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public OrderCartInfoWrapper setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public OrderCartInfoWrapper setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public Long getCreatetimestamp() {
        return createtimestamp;
    }

    public OrderCartInfoWrapper setCreatetimestamp(Long createtimestamp) {
        this.createtimestamp = createtimestamp;
        return this;
    }

    public Long getUpdatetimestamp() {
        return updatetimestamp;
    }

    public OrderCartInfoWrapper setUpdatetimestamp(Long updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
        return this;
    }

    public String getDisabled() {
        return disabled;
    }

    public OrderCartInfoWrapper setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public OrderCartInfoWrapper setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getId() {
        return id;
    }

    public OrderCartInfoWrapper setId(String id) {
        this.id = id;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public OrderCartInfoWrapper setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getBuyerinstid() {
        return buyerinstid;
    }

    public OrderCartInfoWrapper setBuyerinstid(String buyerinstid) {
        this.buyerinstid = buyerinstid;
        return this;
    }

    public String getSn() {
        return sn;
    }

    public OrderCartInfoWrapper setSn(String sn) {
        this.sn = sn;
        return this;
    }

    public String getMode() {
        return mode;
    }

    public OrderCartInfoWrapper setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public String getState() {
        return state;
    }

    public OrderCartInfoWrapper setState(String state) {
        this.state = state;
        return this;
    }

    public Long getActivatedate() {
        return activatedate;
    }

    public OrderCartInfoWrapper setActivatedate(Long activatedate) {
        this.activatedate = activatedate;
        return this;
    }

    public String getSalesmanid() {
        return salesmanid;
    }

    public OrderCartInfoWrapper setSalesmanid(String salesmanid) {
        this.salesmanid = salesmanid;
        return this;
    }

    public Double getSaleamount() {
        return saleamount;
    }

    public OrderCartInfoWrapper setSaleamount(Double saleamount) {
        this.saleamount = saleamount;
        return this;
    }

    public Double getFactamount() {
        return factamount;
    }

    public OrderCartInfoWrapper setFactamount(Double factamount) {
        this.factamount = factamount;
        return this;
    }

    public int getCommitdoc() {
        return commitdoc;
    }

    public OrderCartInfoWrapper setCommitdoc(int commitdoc) {
        this.commitdoc = commitdoc;
        return this;
    }

    public String getPaystate() {
        return paystate;
    }

    public OrderCartInfoWrapper setPaystate(String paystate) {
        this.paystate = paystate;
        return this;
    }

    public int getReadstate() {
        return readstate;
    }

    public OrderCartInfoWrapper setReadstate(int readstate) {
        this.readstate = readstate;
        return this;
    }

    public String getProxyinstid() {
        return proxyinstid;
    }

    public OrderCartInfoWrapper setProxyinstid(String proxyinstid) {
        this.proxyinstid = proxyinstid;
        return this;
    }

    public String getProxyman() {
        return proxyman;
    }

    public OrderCartInfoWrapper setProxyman(String proxyman) {
        this.proxyman = proxyman;
        return this;
    }

    public String getPurchase() {
        return purchase;
    }

    public OrderCartInfoWrapper setPurchase(String purchase) {
        this.purchase = purchase;
        return this;
    }

    public String getName() {
        return name;
    }

    public OrderCartInfoWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public String getImgid() {
        return imgid;
    }

    public OrderCartInfoWrapper setImgid(String imgid) {
        this.imgid = imgid;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public OrderCartInfoWrapper setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public String getPage() {
        return page;
    }

    public OrderCartInfoWrapper setPage(String page) {
        this.page = page;
        return this;
    }

    public Long getBegindate() {
        return begindate;
    }

    public OrderCartInfoWrapper setBegindate(Long begindate) {
        this.begindate = begindate;
        return this;
    }

    public Long getEnddate() {
        return enddate;
    }

    public OrderCartInfoWrapper setEnddate(Long enddate) {
        this.enddate = enddate;
        return this;
    }

    public String getPreferentialInfo() {
        return preferentialInfo;
    }

    public OrderCartInfoWrapper setPreferentialInfo(String preferentialInfo) {
        this.preferentialInfo = preferentialInfo;
        return this;
    }
}
