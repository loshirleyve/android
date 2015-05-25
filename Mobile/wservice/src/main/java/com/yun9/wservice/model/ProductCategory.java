package com.yun9.wservice.model;

/**
 * Created by xia on 2015/5/25.
 */
public class ProductCategory {

    private String categoryname;

    public ProductCategory(String categoryname){
        this.categoryname = categoryname;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
