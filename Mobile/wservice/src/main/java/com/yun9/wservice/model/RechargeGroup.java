package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/23/15.
 */
public class RechargeGroup implements Serializable {
    private long nums;
    private String state;
    private String statename;

    public long getNums() {
        return nums;
    }

    public RechargeGroup setNums(long nums) {
        this.nums = nums;
        return this;
    }

    public String getState() {
        return state;
    }

    public RechargeGroup setState(String state) {
        this.state = state;
        return this;
    }

    public String getStatename() {
        return statename;
    }

    public RechargeGroup setStatename(String statename) {
        this.statename = statename;
        return this;
    }
}
