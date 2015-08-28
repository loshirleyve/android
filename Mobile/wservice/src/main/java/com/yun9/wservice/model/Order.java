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

    private List<WorkorderDto> workorders;

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

    public List<WorkorderDto> getWorkorders() {
        return workorders;
    }

    public void setWorkorders(List<WorkorderDto> workorders) {
        this.workorders = workorders;
    }

    public static class OrderBase implements Serializable{
        private String id;
        private String name;
        private String imgid;
        private String introduce;
        private String ordersn;
        private String state;
        private Double orderamount;
        private Double factamount;
        private int commitdoc;
        private String instid;
        private String buyerinstid;
        private String proxyinstid;
        private String proxyman;
        private String adviser;
        private String purchase;
        private Long createdate;
        private String createby;
        private String paystate;
        private Long begindate;
        private Long enddate;

        public String getId() {
            return id;
        }

        public OrderBase setId(String id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public OrderBase setName(String name) {
            this.name = name;
            return this;
        }

        public String getImgid() {
            return imgid;
        }

        public OrderBase setImgid(String imgid) {
            this.imgid = imgid;
            return this;
        }

        public String getIntroduce() {
            return introduce;
        }

        public OrderBase setIntroduce(String introduce) {
            this.introduce = introduce;
            return this;
        }

        public String getOrdersn() {
            return ordersn;
        }

        public OrderBase setOrdersn(String ordersn) {
            this.ordersn = ordersn;
            return this;
        }

        public String getState() {
            return state;
        }

        public OrderBase setState(String state) {
            this.state = state;
            return this;
        }

        public Double getOrderamount() {
            return orderamount;
        }

        public OrderBase setOrderamount(Double orderamount) {
            this.orderamount = orderamount;
            return this;
        }

        public Double getFactamount() {
            return factamount;
        }

        public OrderBase setFactamount(Double factamount) {
            this.factamount = factamount;
            return this;
        }

        public int getCommitdoc() {
            return commitdoc;
        }

        public OrderBase setCommitdoc(int commitdoc) {
            this.commitdoc = commitdoc;
            return this;
        }

        public String getInstid() {
            return instid;
        }

        public OrderBase setInstid(String instid) {
            this.instid = instid;
            return this;
        }

        public String getBuyerinstid() {
            return buyerinstid;
        }

        public OrderBase setBuyerinstid(String buyerinstid) {
            this.buyerinstid = buyerinstid;
            return this;
        }

        public String getProxyinstid() {
            return proxyinstid;
        }

        public OrderBase setProxyinstid(String proxyinstid) {
            this.proxyinstid = proxyinstid;
            return this;
        }

        public String getProxyman() {
            return proxyman;
        }

        public OrderBase setProxyman(String proxyman) {
            this.proxyman = proxyman;
            return this;
        }

        public String getAdviser() {
            return adviser;
        }

        public OrderBase setAdviser(String adviser) {
            this.adviser = adviser;
            return this;
        }

        public String getPurchase() {
            return purchase;
        }

        public OrderBase setPurchase(String purchase) {
            this.purchase = purchase;
            return this;
        }

        public Long getCreatedate() {
            return createdate;
        }

        public OrderBase setCreatedate(Long createdate) {
            this.createdate = createdate;
            return this;
        }

        public String getCreateby() {
            return createby;
        }

        public OrderBase setCreateby(String createby) {
            this.createby = createby;
            return this;
        }

        public String getPaystate() {
            return paystate;
        }

        public OrderBase setPaystate(String paystate) {
            this.paystate = paystate;
            return this;
        }

        public Long getBegindate() {
            return begindate;
        }

        public OrderBase setBegindate(Long begindate) {
            this.begindate = begindate;
            return this;
        }

        public Long getEnddate() {
            return enddate;
        }

        public OrderBase setEnddate(Long enddate) {
            this.enddate = enddate;
            return this;
        }
    }

    public static class OrderProduct implements Serializable{
        private double goodsamount;
        private String instid;
        private String productimgid;
        private String productid;
        private String goodsnum;
        private String productname;
        private String productIntroduce;

        public String getProductIntroduce() {
            return productIntroduce;
        }

        public OrderProduct setProductIntroduce(String productIntroduce) {
            this.productIntroduce = productIntroduce;
            return this;
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
        private String duty;
        private String remark;
        private int commentNum;

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

        public String getDuty() {
            return duty;
        }

        public OrderWorkOrder setDuty(String duty) {
            this.duty = duty;
            return this;
        }

        public String getRemark() {
            return remark;
        }

        public OrderWorkOrder setRemark(String remark) {
            this.remark = remark;
            return this;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public OrderWorkOrder setCommentNum(int commentNum) {
            this.commentNum = commentNum;
            return this;
        }
    }
}
