package com.yun9.wservice.model;

import java.util.List;

/**
 * Created by xia on 2015/5/25.
 */
public class Product implements java.io.Serializable {
    private String id;
    private String sn;
    private String name;
    private double saleprice;
    private int sort;
    private String instid;
    private String state;
    private String introduce;
    private String type;
    private String imageid;
    private int daynum;
    private String backgorundimgid;
    private boolean istop;

    private List<ProductPhase> phases;

    private List<ProductProfile> profiles;

    private List<ProductReqirement> reqirements;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public int getDaynum() {
        return daynum;
    }

    public void setDaynum(int daynum) {
        this.daynum = daynum;
    }

    public String getBackgorundimgid() {
        return backgorundimgid;
    }

    public void setBackgorundimgid(String backgorundimgid) {
        this.backgorundimgid = backgorundimgid;
    }

    public boolean istop() {
        return istop;
    }

    public void setIstop(boolean istop) {
        this.istop = istop;
    }

    public List<ProductPhase> getPhases() {
        return phases;
    }

    public void setPhases(List<ProductPhase> phases) {
        this.phases = phases;
    }

    public List<ProductProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProductProfile> profiles) {
        this.profiles = profiles;
    }

    public List<ProductReqirement> getReqirements() {
        return reqirements;
    }

    public void setReqirements(List<ProductReqirement> reqirements) {
        this.reqirements = reqirements;
    }
}
