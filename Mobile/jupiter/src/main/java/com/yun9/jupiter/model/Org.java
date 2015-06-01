package com.yun9.jupiter.model;

/**
 * Created by Leon on 15/6/1.
 */
public class Org implements java.io.Serializable{
    private String id;
    private String name;
    private String no;
    private String dimid;
    private String dimno;
    private String dimType;
    private String desc;
    private String parentid;
    private int sort;
    private long createdata;
    private String createby;
    private String remark;

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getCreatedata() {
        return createdata;
    }

    public void setCreatedata(long createdata) {
        this.createdata = createdata;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDimid() {
        return dimid;
    }

    public void setDimid(String dimid) {
        this.dimid = dimid;
    }

    public String getDimno() {
        return dimno;
    }

    public void setDimno(String dimno) {
        this.dimno = dimno;
    }

    public String getDimType() {
        return dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }
}
