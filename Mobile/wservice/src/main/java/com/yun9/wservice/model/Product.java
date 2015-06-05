package com.yun9.wservice.model;

/**
 * Created by xia on 2015/5/25.
 */
public class Product {
    private String id;
    private String inputdesc;
    private String productid;
    private String transfertype;
    private String inputtype;
    private String attachname;
    private String attachkey;
    private String productImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputdesc() {
        return inputdesc;
    }

    public void setInputdesc(String inputdesc) {
        this.inputdesc = inputdesc;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(String transfertype) {
        this.transfertype = transfertype;
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype;
    }

    public String getAttachname() {
        return attachname;
    }

    public void setAttachname(String attachname) {
        this.attachname = attachname;
    }

    public String getAttachkey() {
        return attachkey;
    }

    public void setAttachkey(String attachkey) {
        this.attachkey = attachkey;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
