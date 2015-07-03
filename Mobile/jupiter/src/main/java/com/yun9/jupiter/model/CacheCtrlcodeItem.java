package com.yun9.jupiter.model;

import java.io.Serializable;

/**
 * Created by huangbinglong on 7/3/15.
 */
public class CacheCtrlcodeItem implements Serializable{

    private String createby;
    private String updateby;
    private Long createdate;
    private Long updatedate;
    private int disabled;
    private String remark;
    private String id;
    private String defno;
    private String name;
    private String defname;
    private String no;
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

    public String getDefno() {
        return defno;
    }

    public void setDefno(String defno) {
        this.defno = defno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefname() {
        return defname;
    }

    public void setDefname(String defname) {
        this.defname = defname;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getSortno() {
        return sortno;
    }

    public void setSortno(int sortno) {
        this.sortno = sortno;
    }
}
