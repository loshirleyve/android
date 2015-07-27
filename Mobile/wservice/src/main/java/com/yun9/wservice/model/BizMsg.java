package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 7/25/15.
 */
public class BizMsg implements Serializable{

    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private String disabled;
    private String remark;
    private String id;

    private Long createtimestamp;
    private Long updatetimestamp;
    private String touserid;
    private String usertype;
    private String fromuserid;
    private String type;
    private String source;
    private String content;
    private String state;
    private String instid;

    public String getCreateby() {
        return createby;
    }

    public BizMsg setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getUpdateby() {
        return updateby;
    }

    public BizMsg setUpdateby(String updateby) {
        this.updateby = updateby;
        return this;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public BizMsg setCreatedate(Long createdate) {
        this.createdate = createdate;
        return this;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public BizMsg setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public String getDisabled() {
        return disabled;
    }

    public BizMsg setDisabled(String disabled) {
        this.disabled = disabled;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public BizMsg setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getId() {
        return id;
    }

    public BizMsg setId(String id) {
        this.id = id;
        return this;
    }

    public Long getCreatetimestamp() {
        return createtimestamp;
    }

    public BizMsg setCreatetimestamp(Long createtimestamp) {
        this.createtimestamp = createtimestamp;
        return this;
    }

    public Long getUpdatetimestamp() {
        return updatetimestamp;
    }

    public BizMsg setUpdatetimestamp(Long updatetimestamp) {
        this.updatetimestamp = updatetimestamp;
        return this;
    }

    public String getTouserid() {
        return touserid;
    }

    public BizMsg setTouserid(String touserid) {
        this.touserid = touserid;
        return this;
    }

    public String getUsertype() {
        return usertype;
    }

    public BizMsg setUsertype(String usertype) {
        this.usertype = usertype;
        return this;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public BizMsg setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
        return this;
    }

    public String getType() {
        return type;
    }

    public BizMsg setType(String type) {
        this.type = type;
        return this;
    }

    public String getSource() {
        return source;
    }

    public BizMsg setSource(String source) {
        this.source = source;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BizMsg setContent(String content) {
        this.content = content;
        return this;
    }

    public String getState() {
        return state;
    }

    public BizMsg setState(String state) {
        this.state = state;
        return this;
    }

    public String getInstid() {
        return instid;
    }

    public BizMsg setInstid(String instid) {
        this.instid = instid;
        return this;
    }
}
