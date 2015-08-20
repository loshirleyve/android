package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PayMode implements Serializable {

    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private Long createtimestamp;
    private Long updatetimestamp;
    private String disabled;
    private String remark;
    private String id;
    private String instid;
    private String code;
    private String name;
    private String descr;
    private String type;
    private String imgid;
    private int sort;

    public String getCreateby() {
        return createby;
    }

    public PayMode setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getUpdateby() {
        return updateby;
    }

    public PayMode setUpdateby(String updateby) {
        this.updateby = updateby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public PayMode setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public PayMode setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public Long getCreatetimestamp() {
        return createtimestamp;
    }

    public PayMode setCreatetimestamp(Long createtimestamp) {
        this.createtimestamp = createtimestamp;
        return this;
    }

    public Long getUpdatetimestamp() {
        return updatetimestamp;
    }

    public PayMode setUpdatetimestamp(Long updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
        return this;
    }

    public String getDisabled() {
        return disabled;
    }

    public PayMode setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PayMode setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getId() {
        return id;
    }

    public PayMode setId(String id) {
        this.id = id;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public PayMode setInstid(String instid) {
        this.instid = instid;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PayMode setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public PayMode setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescr() {
        return descr;
    }

    public PayMode setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public String getType() {
        return type;
    }

    public PayMode setType(String type) {
        this.type = type;
        return this;
    }

    public String getImgid() {
        return imgid;
    }

    public PayMode setImgid(String imgid) {
        this.imgid = imgid;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public PayMode setSort(int sort) {
        this.sort = sort;
        return this;
    }
}
