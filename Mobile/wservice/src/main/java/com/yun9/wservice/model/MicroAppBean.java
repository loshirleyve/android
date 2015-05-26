package com.yun9.wservice.model;

import java.util.List;

/**
 * Created by xia on 2015/5/22.
 */
public class MicroAppBean  {
    private String type;
    private String parentid;
    private String device;
    private String actiontype;
    private String url;
    private String icopath;
    private int sort;
    private String mainurl;
    private String no;
    private String name;
    private String actionparams;
    List<MicroAppBean> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getActiontype() {
        return actiontype;
    }

    public void setActiontype(String actiontype) {
        this.actiontype = actiontype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcopath() {
        return icopath;
    }

    public void setIcopath(String icopath) {
        this.icopath = icopath;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getMainurl() {
        return mainurl;
    }

    public void setMainurl(String mainurl) {
        this.mainurl = mainurl;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionparams() {
        return actionparams;
    }

    public void setActionparams(String actionparams) {
        this.actionparams = actionparams;
    }

    public List<MicroAppBean> getChildren() {
        return children;
    }

    public void setChildren(List<MicroAppBean> children) {
        this.children = children;
    }
}
