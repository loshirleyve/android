package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by rxy on 15/8/20.
 */
public class WorkorderDto implements Serializable {

    private String descr;
    private String inserviceName;
    private int completeNum;
    private int allNum;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getInserviceName() {
        return inserviceName;
    }

    public void setInserviceName(String inserviceName) {
        this.inserviceName = inserviceName;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }
}
