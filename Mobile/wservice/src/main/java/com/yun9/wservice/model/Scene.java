package com.yun9.wservice.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 7/10/15.
 */
public class Scene implements Serializable{
    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private int disabled;
    private String remark;
    private String id;
    private String no;
    private String name;
    private int sortno;

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public Long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Long createdate) {
        this.createdate = createdate;
    }

    public Long getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Long updatedate) {
        this.updatedate = updatedate;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSortno() {
        return sortno;
    }

    public void setSortno(int sortno) {
        this.sortno = sortno;
    }
}
