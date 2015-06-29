package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单实体
 * Created by huangbinglong on 15/6/12.
 */
public class Order implements Serializable{

    private Integer paystate;
    private String state;
    private Long createdate;
    private Double orderamount;
    private Integer commitattachment;
    private String ordersn;
    private String buyerinstid;
    private String provideinstid;
    private String statename;
    private String orderid;
    private String adviseruserid;
    private Double accountbalance;
    private List<WorkOrder> workorders;
    private List<OrderLog> orderlogs;
    private List<OrderProduct> products;

    public Integer getPaystate() {
        return paystate;
    }

    public void setPaystate(Integer paystate) {
        this.paystate = paystate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public Double getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(Double orderamount) {
        this.orderamount = orderamount;
    }

    public Integer getCommitattachment() {
        return commitattachment;
    }

    public void setCommitattachment(Integer commitattachment) {
        this.commitattachment = commitattachment;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
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

    public String getAdviseruserid() {
        return adviseruserid;
    }

    public void setAdviseruserid(String adviseruserid) {
        this.adviseruserid = adviseruserid;
    }

    public List<WorkOrder> getWorkorders() {
        return workorders;
    }

    public void setWorkorders(List<WorkOrder> workorders) {
        this.workorders = workorders;
    }

    public List<OrderLog> getOrderlogs() {
        return orderlogs;
    }

    public void setOrderlogs(List<OrderLog> orderlogs) {
        this.orderlogs = orderlogs;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public Double getAccountbalance() {
        return accountbalance;
    }

    public void setAccountbalance(Double accountbalance) {
        this.accountbalance = accountbalance;
    }

    public static class OrderLog implements Serializable{
        private String orderstate;
        private Long handledate;
        private String orderstatecode;

        public String getOrderstate() {
            return orderstate;
        }

        public void setOrderstate(String orderstate) {
            this.orderstate = orderstate;
        }

        public Long getHandledate() {
            return handledate;
        }

        public void setHandledate(Long handledate) {
            this.handledate = handledate;
        }

        public String getOrderstatecode() {
            return orderstatecode;
        }

        public void setOrderstatecode(String orderstatecode) {
            this.orderstatecode = orderstatecode;
        }
    }

    public static class WorkOrder implements Serializable{
        private String orderworkid;
        private String orderworkno;
        private String orderworkname;
        private String orderworkstate;
        private String orderworkstatecode;

        public String getOrderworkid() {
            return orderworkid;
        }

        public void setOrderworkid(String orderworkid) {
            this.orderworkid = orderworkid;
        }

        public String getOrderworkno() {
            return orderworkno;
        }

        public void setOrderworkno(String orderworkno) {
            this.orderworkno = orderworkno;
        }

        public String getOrderworkname() {
            return orderworkname;
        }

        public void setOrderworkname(String orderworkname) {
            this.orderworkname = orderworkname;
        }

        public String getOrderworkstate() {
            return orderworkstate;
        }

        public void setOrderworkstate(String orderworkstate) {
            this.orderworkstate = orderworkstate;
        }

        public String getOrderworkstatecode() {
            return orderworkstatecode;
        }

        public void setOrderworkstatecode(String orderworkstatecode) {
            this.orderworkstatecode = orderworkstatecode;
        }
    }

    public static class OrderProduct implements Serializable {
        private Double goodsamount;
        private String instid;
        private String productimgid;
        private String productid;
        private Integer goodsnum;
        private String productname;

        public Double getGoodsamount() {
            return goodsamount;
        }

        public void setGoodsamount(Double goodsamount) {
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

        public Integer getGoodsnum() {
            return goodsnum;
        }

        public void setGoodsnum(Integer goodsnum) {
            this.goodsnum = goodsnum;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }
    }

}
