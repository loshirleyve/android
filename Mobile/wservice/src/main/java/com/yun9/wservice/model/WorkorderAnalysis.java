package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/30.
 */
public class WorkorderAnalysis implements Serializable {

    private String page;
    private int allNums;
    private int completeNums;
    private int waitNums;
    private int inserviceNums;
    private double comleterate;
    private double laterate;

    public String getPage() {
        return page;
    }

    public WorkorderAnalysis setPage(String page) {
        this.page = page;
        return this;
    }

    public int getAllNums() {
        return allNums;
    }

    public WorkorderAnalysis setAllNums(int allNums) {
        this.allNums = allNums;
        return this;
    }

    public int getCompleteNums() {
        return completeNums;
    }

    public WorkorderAnalysis setCompleteNums(int completeNums) {
        this.completeNums = completeNums;
        return this;
    }

    public int getWaitNums() {
        return waitNums;
    }

    public WorkorderAnalysis setWaitNums(int waitNums) {
        this.waitNums = waitNums;
        return this;
    }

    public int getInserviceNums() {
        return inserviceNums;
    }

    public WorkorderAnalysis setInserviceNums(int inserviceNums) {
        this.inserviceNums = inserviceNums;
        return this;
    }

    public double getComleterate() {
        return comleterate;
    }

    public WorkorderAnalysis setComleterate(double comleterate) {
        this.comleterate = comleterate;
        return this;
    }

    public double getLaterate() {
        return laterate;
    }

    public WorkorderAnalysis setLaterate(double laterate) {
        this.laterate = laterate;
        return this;
    }
}
