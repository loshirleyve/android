package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/30.
 */
public class WorkorderAnalysisUser implements Serializable {

    private String page;
    private String userid;
    private String id;
    private int allNums;
    private int completeNums;
    private int waitNums;
    private int comleterate;
    private int laterate;

    public String getPage() {
        return page;
    }

    public WorkorderAnalysisUser setPage(String page) {
        this.page = page;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public WorkorderAnalysisUser setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getId() {
        return id;
    }

    public WorkorderAnalysisUser setId(String id) {
        this.id = id;
        return this;
    }

    public int getAllNums() {
        return allNums;
    }

    public WorkorderAnalysisUser setAllNums(int allNums) {
        this.allNums = allNums;
        return this;
    }

    public int getCompleteNums() {
        return completeNums;
    }

    public WorkorderAnalysisUser setCompleteNums(int completeNums) {
        this.completeNums = completeNums;
        return this;
    }

    public int getWaitNums() {
        return waitNums;
    }

    public WorkorderAnalysisUser setWaitNums(int waitNums) {
        this.waitNums = waitNums;
        return this;
    }

    public int getComleterate() {
        return comleterate;
    }

    public WorkorderAnalysisUser setComleterate(int comleterate) {
        this.comleterate = comleterate;
        return this;
    }

    public int getLaterate() {
        return laterate;
    }

    public WorkorderAnalysisUser setLaterate(int laterate) {
        this.laterate = laterate;
        return this;
    }
}
