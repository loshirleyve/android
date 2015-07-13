package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderBuyManagerInfo implements Serializable{

    /**
     * 余额
     */
    private double balance;

    /**
     * 订单分类列表
     */
    private List<OrderGroup> orderGroups;

    /**
     * 充值记录分类列表
     */
    private List<RechargeGroup> recharegeGroups;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<OrderGroup> getOrderGroups() {
        return orderGroups;
    }

    public void setOrderGroups(List<OrderGroup> orderGroups) {
        this.orderGroups = orderGroups;
    }

    public List<RechargeGroup> getRecharegeGroups() {
        return recharegeGroups;
    }

    public void setRecharegeGroups(List<RechargeGroup> recharegeGroups) {
        this.recharegeGroups = recharegeGroups;
    }

    /**
     * 订单分类
     */
    public static class OrderGroup implements Serializable {

        private long nums;
        private String state;
        private String statename;

        public OrderGroup() {
        }

        public OrderGroup(long nums, String state, String statename) {
            this.nums = nums;
            this.state = state;
            this.statename = statename;
        }

        public long getNums() {
            return nums;
        }

        public void setNums(long nums) {
            this.nums = nums;
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
    }

    public static class RechargeGroup implements Serializable{

        private long nums;
        private String stateno;
        private String statename;

        public RechargeGroup() {
        }

        public RechargeGroup(long nums, String stateno, String statename) {
            this.nums = nums;
            this.stateno = stateno;
            this.statename = statename;
        }

        public long getNums() {
            return nums;
        }

        public void setNums(long nums) {
            this.nums = nums;
        }

        public String getStateno() {
            return stateno;
        }

        public void setStateno(String stateno) {
            this.stateno = stateno;
        }

        public String getStatename() {
            return statename;
        }

        public void setStatename(String statename) {
            this.statename = statename;
        }
    }
}
