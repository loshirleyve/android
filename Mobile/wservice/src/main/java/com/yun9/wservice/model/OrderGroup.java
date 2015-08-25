package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * 订单分类
 * Created by rxy on 15/8/20.
 */
public class OrderGroup implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderGroup that = (OrderGroup) o;

        if (!state.equals(that.state)) return false;
        return statename.equals(that.statename);

    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + statename.hashCode();
        return result;
    }
}

