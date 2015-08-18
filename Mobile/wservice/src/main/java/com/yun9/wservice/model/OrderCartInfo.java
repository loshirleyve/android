package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单预览实体
 * Created by huangbinglong on 15/6/13.
 */
public class OrderCartInfo implements Serializable{

    private String id;
    private String productid;
    private String productname;
    private String imgid;
    private String introduce;
    private String classifyname;
    private String productclassifyid;
    private Double goodsamount;

    public String getId() {
        return id;
    }

    public OrderCartInfo setId(String id) {
        this.id = id;
        return this;
    }

    public String getProductid() {
        return productid;
    }

    public OrderCartInfo setProductid(String productid) {
        this.productid = productid;
        return this;
    }

    public String getProductname() {
        return productname;
    }

    public OrderCartInfo setProductname(String productname) {
        this.productname = productname;
        return this;
    }

    public String getImgid() {
        return imgid;
    }

    public OrderCartInfo setImgid(String imgid) {
        this.imgid = imgid;
        return this;
    }

    public String getIntroduce() {
        return introduce;
    }

    public OrderCartInfo setIntroduce(String introduce) {
        this.introduce = introduce;
        return this;
    }

    public String getClassifyname() {
        return classifyname;
    }

    public OrderCartInfo setClassifyname(String classifyname) {
        this.classifyname = classifyname;
        return this;
    }

    public String getProductclassifyid() {
        return productclassifyid;
    }

    public OrderCartInfo setProductclassifyid(String productclassifyid) {
        this.productclassifyid = productclassifyid;
        return this;
    }

    public Double getGoodsamount() {
        return goodsamount;
    }

    public OrderCartInfo setGoodsamount(Double goodsamount) {
        this.goodsamount = goodsamount;
        return this;
    }
}
