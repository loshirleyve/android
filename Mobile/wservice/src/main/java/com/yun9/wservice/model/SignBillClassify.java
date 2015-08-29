package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class SignBillClassify implements Serializable {

    private String page;
    private String classfifyName;
    private int nums;
    private Double amount;

    public String getPage() {
        return page;
    }

    public SignBillClassify setPage(String page) {
        this.page = page;
        return this;
    }

    public String getClassfifyName() {
        return classfifyName;
    }

    public SignBillClassify setClassfifyName(String classfifyName) {
        this.classfifyName = classfifyName;
        return this;
    }

    public int getNums() {
        return nums;
    }

    public SignBillClassify setNums(int nums) {
        this.nums = nums;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public SignBillClassify setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}
