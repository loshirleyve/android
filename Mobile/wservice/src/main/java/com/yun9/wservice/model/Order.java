package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单实体
 * Created by huangbinglong on 15/6/12.
 */
public class Order implements Serializable{

    private OrderBase order;

    private List<OrderProduct> orderproducts;

    private List<OrderLog> orderLogs;

    private List<OrderWorkOrder> orderWorkorders;

    public OrderBase getOrder() {
        return order;
    }

    public void setOrder(OrderBase order) {
        this.order = order;
    }

    public List<OrderProduct> getOrderproducts() {
        return orderproducts;
    }

    public void setOrderproducts(List<OrderProduct> orderproducts) {
        this.orderproducts = orderproducts;
    }

    public List<OrderLog> getOrderLogs() {
        return orderLogs;
    }

    public void setOrderLogs(List<OrderLog> orderLogs) {
        this.orderLogs = orderLogs;
    }

    public List<OrderWorkOrder> getOrderWorkorders() {
        return orderWorkorders;
    }

    public void setOrderWorkorders(List<OrderWorkOrder> orderWorkorders) {
        this.orderWorkorders = orderWorkorders;
    }

    public static class OrderBase implements Serializable{
        private String orderid;
        private String ordersn;
        private String state;
        private double orderamount;
        private double accountbalance;
        private int commitattachment;
        private String buyerinstid;
        private String provideinstid;
        private String salesmanid;
        private String adviseruserid;
        private Long createdate;
        private String createby;
        private String paystate;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
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

        public double getAccountbalance() {
            return accountbalance;
        }

        public void setAccountbalance(double accountbalance) {
            this.accountbalance = accountbalance;
        }

        public int getCommitattachment() {
            return commitattachment;
        }

        public void setCommitattachment(int commitattachment) {
            this.commitattachment = commitattachment;
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

    public static class OrderProduct implements Serializable{
        private double goodsamount;
        private String instid;
        private String productimgid;
        private String productid;
        private String goodsnum;
        private String productname;

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

        public String getGoodsnum() {
            return goodsnum;
        }

        public void setGoodsnum(String goodsnum) {
            this.goodsnum = goodsnum;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }
    }

    public static class OrderLog implements Serializable{
        private String orderstate;
        private String orderstatecode;
        private Long handledate;

        public String getOrderstate() {
            return orderstate;
        }

        public void setOrderstate(String orderstate) {
            this.orderstate = orderstate;
        }

        public String getOrderstatecode() {
            return orderstatecode;
        }

        public void setOrderstatecode(String orderstatecode) {
            this.orderstatecode = orderstatecode;
        }

        public Long getHandledate() {
            return handledate;
        }

        public void setHandledate(Long handledate) {
            this.handledate = handledate;
        }
    }

    public static class OrderWorkOrder implements Serializable {
        private String orderworkid;
        private String orderworkno;
        private String orderworkname;
        private String orderworkstate;
        private Long processdate;
        private String assignedid;
        private String processid;
        private Long assigneddate;
        private Long expirydate;
        private Long completedate;

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

        public Long getProcessdate() {
            return processdate;
        }

        public void setProcessdate(Long processdate) {
            this.processdate = processdate;
        }

        public String getAssignedid() {
            return assignedid;
        }

        public void setAssignedid(String assignedid) {
            this.assignedid = assignedid;
        }

        public String getProcessid() {
            return processid;
        }

        public void setProcessid(String processid) {
            this.processid = processid;
        }

        public Long getAssigneddate() {
            return assigneddate;
        }

        public void setAssigneddate(Long assigneddate) {
            this.assigneddate = assigneddate;
        }

        public Long getExpirydate() {
            return expirydate;
        }

        public void setExpirydate(Long expirydate) {
            this.expirydate = expirydate;
        }

        public Long getCompletedate() {
            return completedate;
        }

        public void setCompletedate(Long completedate) {
            this.completedate = completedate;
        }
    }
}
