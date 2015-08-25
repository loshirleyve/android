package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 8/25/15.
 */
public class BigInst implements Serializable{

    private String no;
    private String createdate;
    private String simplename;
    private String createby;
    private String hostname;
    private Long updatedate;
    private String updateby;
    private String homepath;
    private String name;
    private String logo;
    private int disabled;
    private String tel;
    private String id;
    private String status;

    public String getNo() {
        return no;
    }

    public BigInst setNo(String no) {
        this.no = no;
        return this;
    }

    public String getCreatedate() {
        return createdate;
    }

    public BigInst setCreatedate(String createdate) {
        this.createdate = createdate;
        return this;
    }

    public String getSimplename() {
        return simplename;
    }

    public BigInst setSimplename(String simplename) {
        this.simplename = simplename;
        return this;
    }

    public String getCreateby() {
        return createby;
    }

    public BigInst setCreateby(String createby) {
        this.createby = createby;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public BigInst setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public BigInst setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public String getUpdateby() {
        return updateby;
    }

    public BigInst setUpdateby(String updateby) {
        this.updateby = updateby;
        return this;
    }

    public String getHomepath() {
        return homepath;
    }

    public BigInst setHomepath(String homepath) {
        this.homepath = homepath;
        return this;
    }

    public String getName() {
        return name;
    }

    public BigInst setName(String name) {
        this.name = name;
        return this;
    }

    public String getLogo() {
        return logo;
    }

    public BigInst setLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public int getDisabled() {
        return disabled;
    }

    public BigInst setDisabled(int disabled) {
        this.disabled = disabled;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public BigInst setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getId() {
        return id;
    }

    public BigInst setId(String id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public BigInst setStatus(String status) {
        this.status = status;
        return this;
    }
}
