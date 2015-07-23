package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单预览实体
 * Created by huangbinglong on 15/6/13.
 */
public class OrderCartInfo implements Serializable{

    private String productname;
    private String ordersn;
    private String state;
    private String orderamount;
    private String commitattachment;
    private String instid;
    private String buyerinstid;
    private String salesmanid;
    private String adviseruserid;
    private Long createdate;
    private String createby;
    private String paystate;
    private String purchase;
    private List<OrderProduct> orderproducts;
    private List<OrderAttachment> orderAttachments;

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

    public String getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(String orderamount) {
        this.orderamount = orderamount;
    }

    public String getCommitattachment() {
        return commitattachment;
    }

    public void setCommitattachment(String commitattachment) {
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

    public List<OrderProduct> getOrderproducts() {
        return orderproducts;
    }

    public void setOrderproducts(List<OrderProduct> orderproducts) {
        this.orderproducts = orderproducts;
    }

    public List<OrderAttachment> getOrderAttachments() {
        return orderAttachments;
    }

    public void setOrderAttachments(List<OrderAttachment> orderAttachments) {
        this.orderAttachments = orderAttachments;
    }

    public String getPurchase() {
        return purchase;
    }

    public OrderCartInfo setPurchase(String purchase) {
        this.purchase = purchase;
        return this;
    }

    public static class OrderProduct implements Serializable {

        private double goodsamount;
        private String instid;
        private String productimgid;
        private String productid;
        private String productclassifyid;
        private String productclassifyname;
        private int goodsnum;
        private String productname;
        private List<ProductPhases> productPhases;

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

        public String getProductimgid() {
            return productimgid;
        }

        public void setProductimgid(String productimgid) {
            this.productimgid = productimgid;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getProductclassifyid() {
            return productclassifyid;
        }

        public void setProductclassifyid(String productclassifyid) {
            this.productclassifyid = productclassifyid;
        }

        public String getProductclassifyname() {
            return productclassifyname;
        }

        public void setProductclassifyname(String productclassifyname) {
            this.productclassifyname = productclassifyname;
        }

        public int getGoodsnum() {
            return goodsnum;
        }

        public void setGoodsnum(int goodsnum) {
            this.goodsnum = goodsnum;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public List<ProductPhases> getProductPhases() {
            return productPhases;
        }

        public void setProductPhases(List<ProductPhases> productPhases) {
            this.productPhases = productPhases;
        }
    }

    public static class ProductPhases implements Serializable{
        private String id;
        private String productid;
        private String name;
        private int times;
        private String cycle;
        private String cyclevalue;
        private int processdays;
        private int sortno;
        private int disabled;
        private String createby;
        private Long createdate;
        private String updateby;
        private Long updatedate;
        private String remark;
        private String phasedescr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getCyclevalue() {
            return cyclevalue;
        }

        public void setCyclevalue(String cyclevalue) {
            this.cyclevalue = cyclevalue;
        }

        public int getProcessdays() {
            return processdays;
        }

        public void setProcessdays(int processdays) {
            this.processdays = processdays;
        }

        public int getSortno() {
            return sortno;
        }

        public void setSortno(int sortno) {
            this.sortno = sortno;
        }

        public int getDisabled() {
            return disabled;
        }

        public void setDisabled(int disabled) {
            this.disabled = disabled;
        }

        public String getCreateby() {
            return createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public Long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Long createdate) {
            this.createdate = createdate;
        }

        public String getUpdateby() {
            return updateby;
        }

        public void setUpdateby(String updateby) {
            this.updateby = updateby;
        }

        public Long getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(Long updatedate) {
            this.updatedate = updatedate;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPhasedescr() {
            return phasedescr;
        }

        public void setPhasedescr(String phasedescr) {
            this.phasedescr = phasedescr;
        }
    }

    public static class OrderAttachment implements Serializable {
        private String id;
        private String attachkey;
        private String attachname;
        private String inputtype;
        private String inputdesc;
        private String demovalue;
        private String transfertype;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAttachkey() {
            return attachkey;
        }

        public void setAttachkey(String attachkey) {
            this.attachkey = attachkey;
        }

        public String getAttachname() {
            return attachname;
        }

        public void setAttachname(String attachname) {
            this.attachname = attachname;
        }

        public String getInputtype() {
            return inputtype;
        }

        public void setInputtype(String inputtype) {
            this.inputtype = inputtype;
        }

        public String getInputdesc() {
            return inputdesc;
        }

        public void setInputdesc(String inputdesc) {
            this.inputdesc = inputdesc;
        }

        public String getDemovalue() {
            return demovalue;
        }

        public void setDemovalue(String demovalue) {
            this.demovalue = demovalue;
        }

        public String getTransfertype() {
            return transfertype;
        }

        public void setTransfertype(String transfertype) {
            this.transfertype = transfertype;
        }
    }
}
