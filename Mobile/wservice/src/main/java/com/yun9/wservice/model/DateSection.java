package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/8/28.
 */
public class DateSection implements Serializable{

    private String id;
    private String label;
    private String descr;
    private String name;
    private String timetype;
    private int offset;
    private int isdefault;
    private int sort;
    private Long begindate;
    private Long enddate;
    private Long createdate;
    private String createby;
    private String remark;

    public String getId() {
        return id;
    }

    public DateSection setId(String id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public DateSection setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public DateSection setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public String getName() {
        return name;
    }

    public DateSection setName(String name) {
        this.name = name;
        return this;
    }

    public String getTimetype() {
        return timetype;
    }

    public DateSection setTimetype(String timetype) {
        this.timetype = timetype;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public DateSection setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public int getIsdefault() {
        return isdefault;
    }

    public DateSection setIsdefault(int isdefault) {
        this.isdefault = isdefault;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public DateSection setSort(int sort) {
        this.sort = sort;
        return this;
    }

    public Long getBegindate() {
        return begindate;
    }

    public DateSection setBegindate(Long begindate) {
        this.begindate = begindate;
        return this;
    }

    public Long getEnddate() {
        return enddate;
    }

    public DateSection setEnddate(Long enddate) {
        this.enddate = enddate;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public DateSection setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public String getCreateby() {
        return createby;
    }

    public DateSection setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public DateSection setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
