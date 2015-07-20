package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 7/14/15.
 */
public class FinanceCollects implements Serializable{

    private String collectstate;
    private SourceInfo sourceInfo;
    private List<FinanceCollect> financeCollectList;

    public String getCollectstate() {
        return collectstate;
    }

    public void setCollectstate(String collectstate) {
        this.collectstate = collectstate;
    }

    public SourceInfo getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(SourceInfo sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public List<FinanceCollect> getFinanceCollectList() {
        return financeCollectList;
    }

    public void setFinanceCollectList(List<FinanceCollect> financeCollectList) {
        this.financeCollectList = financeCollectList;
    }

    public static class SourceInfo implements Serializable{
        private String id;
        private String orderid;
        private String productname;
        private String productimgid;
        private String ordersn;
        private String state;
        private double orderamount;
        private int commitattachment;
        private String instid;
        private String buyerinstid;
        private String provideinstid;
        private String salesmanid;
        private String adviseruserid;
        private Long createdate;
        private String createby;
        private String paystate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getProductimgid() {
            return productimgid;
        }

        public void setProductimgid(String productimgid) {
            this.productimgid = productimgid;
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

        public String getPaystate() {
            return paystate;
        }

        public void setPaystate(String paystate) {
            this.paystate = paystate;
        }
    }

    public static class FinanceCollect implements Serializable{
        private String id;
        private String source;
        private String sourcevalue;
        private double collectamount;
        private double payamount;
        private double waitpay;
        private Long createdate;
        private String paymodeName;
        private String state;
        private String clientName;
        private String clientarrivestate;
        private String arriveimgid;
        private String arrivetext;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSourcevalue() {
            return sourcevalue;
        }

        public void setSourcevalue(String sourcevalue) {
            this.sourcevalue = sourcevalue;
        }

        public double getCollectamount() {
            return collectamount;
        }

        public void setCollectamount(double collectamount) {
            this.collectamount = collectamount;
        }

        public double getPayamount() {
            return payamount;
        }

        public void setPayamount(double payamount) {
            this.payamount = payamount;
        }

        public double getWaitpay() {
            return waitpay;
        }

        public void setWaitpay(double waitpay) {
            this.waitpay = waitpay;
        }

        public Long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Long createdate) {
            this.createdate = createdate;
        }

        public String getPaymodeName() {
            return paymodeName;
        }

        public void setPaymodeName(String paymodeName) {
            this.paymodeName = paymodeName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientarrivestate() {
            return clientarrivestate;
        }

        public void setClientarrivestate(String clientarrivestate) {
            this.clientarrivestate = clientarrivestate;
        }

        public String getArriveimgid() {
            return arriveimgid;
        }

        public void setArriveimgid(String arriveimgid) {
            this.arriveimgid = arriveimgid;
        }

        public String getArrivetext() {
            return arrivetext;
        }

        public void setArrivetext(String arrivetext) {
            this.arrivetext = arrivetext;
        }
    }
}
