package com.yun9.wservice.model;

/**
 * Created by xia on 2015/5/25.
 */
public class ProductCategory implements java.io.Serializable {

    private String id;
    private String isglobal;
    private String categoryname;
    private String province;
    private String district;
    private String city;
    private String state;
    private int sort;

    private String createby;
    private String updateby;
    private long createdate;
    private long updatedate;
    private int disabled;
    private String remark;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsglobal() {
        return isglobal;
    }

    public void setIsglobal(String isglobal) {
        this.isglobal = isglobal;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

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

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public long getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(long updatedate) {
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
}
