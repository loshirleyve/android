package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by li on 2015/7/11.
 */
public class MdInstScale implements Serializable {

    private String createby;
    private String updateby;
    private String createdate;
    private String updatedate;
    private String createtimestamp;
    private String updatetimestamp;
    private String disabled;
    private String remark;
    private String id;
    private String tmplateinstid;
    private String name;
    private String type;

    public String getTmplateinstid() {
        return tmplateinstid;
    }

    public MdInstScale setTmplateinstid(String tmplateinstid) {
        this.tmplateinstid = tmplateinstid;
        return this;
    }

    public String getType() {
        return type;
    }

    public MdInstScale setType(String type) {
        this.type = type;
        return this;
    }

    public String getCreateby() {
        return createby;
    }

    public MdInstScale setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getUpdateby() {
        return updateby;
    }

    public MdInstScale setUpdateby(String updateby) {
        this.updateby = updateby;
        return this;
    }

    public String getCreatedate() {
        return createdate;
    }

    public MdInstScale setCreatedate(String createdate) {
        this.createdate = createdate;
        return this;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public MdInstScale setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public String getCreatetimestamp() {
        return createtimestamp;
    }

    public MdInstScale setCreatetimestamp(String createtimestamp) {
        this.createtimestamp = createtimestamp;
        return this;
    }

    public String getUpdatetimestamp() {
        return updatetimestamp;
    }

    public MdInstScale setUpdatetimestamp(String updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
        return this;
    }

    public String getDisabled() {
        return disabled;
    }

    public MdInstScale setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public MdInstScale setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getId() {
        return id;
    }

    public MdInstScale setId(String id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public MdInstScale setName(String name) {
        this.name = name;
        return this;
    }

}
