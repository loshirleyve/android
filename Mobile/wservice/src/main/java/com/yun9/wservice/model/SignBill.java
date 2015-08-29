package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class SignBill implements Serializable {

    private String id;
    private String page;
    private String userid;
    private int sortno;
    private int nums;
    private Double amount;
    private Long beginDate;
    private Long endDate;
    private List<SignBillClassify> classifys;

    public String getPage() {
        return page;
    }

    public SignBill setPage(String page) {
        this.page = page;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public SignBill setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public int getSortno() {
        return sortno;
    }

    public SignBill setSortno(int sortno) {
        this.sortno = sortno;
        return this;
    }

    public int getNums() {
        return nums;
    }

    public SignBill setNums(int nums) {
        this.nums = nums;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public SignBill setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Long getBeginDate() {
        return beginDate;
    }

    public SignBill setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public Long getEndDate() {
        return endDate;
    }

    public SignBill setEndDate(Long endDate) {
        this.endDate = endDate;
        return this;
    }

    public List<SignBillClassify> getClassifys() {
        return classifys;
    }

    public SignBill setClassifys(List<SignBillClassify> classifys) {
        this.classifys = classifys;
        return this;
    }

    public String getId() {
        return id;
    }

    public SignBill setId(String id) {
        this.id = id;
        return this;
    }
}
