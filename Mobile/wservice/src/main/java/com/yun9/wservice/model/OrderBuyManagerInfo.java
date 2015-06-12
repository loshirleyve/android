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
    private long balance;

    /**
     * 订单分类列表
     */
    private List<OrderGroup> orderGroups;

    /**
     * 充值记录分类列表
     */
    private List<RechargeGroup> recharegeGroups;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
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

        private long num;
        private String state;
        private String statename;

        public OrderGroup() {
        }

        public OrderGroup(long num, String state, String statename) {
            this.num = num;
            this.state = state;
            this.statename = statename;
        }

        public long getNum() {
            return num;
        }

        public void setNum(long num) {
            this.num = num;
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

        private long count;
        private String state;
        private String statename;

        public RechargeGroup() {
        }

        public RechargeGroup(long count, String state, String statename) {
            this.count = count;
            this.state = state;
            this.statename = statename;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
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
}
