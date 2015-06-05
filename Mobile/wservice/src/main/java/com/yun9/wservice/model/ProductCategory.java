package com.yun9.wservice.model;

/**
 * Created by xia on 2015/5/25.
 */
public class ProductCategory {

    private String categoryname;
    private String province;
    private String city;

    public ProductCategory(String categoryname){
        this.categoryname = categoryname;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
