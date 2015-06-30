package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单预览实体
 * Created by huangbinglong on 15/6/13.
 */
public class OrderCartInfo implements Serializable{

    private String proxyinstid;
    private double total;
    private String purchase;
    private String proxyman;
    private Provider provideinstinfo;
    private List<OrderCartProduct> productinfos;

    public String getProxyinstid() {
        return proxyinstid;
    }

    public void setProxyinstid(String proxyinstid) {
        this.proxyinstid = proxyinstid;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getProxyman() {
        return proxyman;
    }

    public void setProxyman(String proxyman) {
        this.proxyman = proxyman;
    }

    public Provider getProvideinstinfo() {
        return provideinstinfo;
    }

    public void setProvideinstinfo(Provider provideinstinfo) {
        this.provideinstinfo = provideinstinfo;
    }

    public List<OrderCartProduct> getProductinfos() {
        return productinfos;
    }

    public void setProductinfos(List<OrderCartProduct> productinfos) {
        this.productinfos = productinfos;
    }

    public static class OrderCartProduct implements Serializable {
        private String imgid;
        private String introduce;
        private double saleprice;
        private List<ProductProfile> profiles;
        private String type;
        private String introduceurl;
        private String instid;
        private String name;
        private String id;
        private String sn;
        private String state;
        private int daynum;
        private List<ProductPhase> phases;

        public String getImgid() {
            return imgid;
        }

        public void setImgid(String imgid) {
            this.imgid = imgid;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public double getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(double saleprice) {
            this.saleprice = saleprice;
        }

        public List<ProductProfile> getProfiles() {
            return profiles;
        }

        public void setProfiles(List<ProductProfile> profiles) {
            this.profiles = profiles;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIntroduceurl() {
            return introduceurl;
        }

        public void setIntroduceurl(String introduceurl) {
            this.introduceurl = introduceurl;
        }

        public String getInstid() {
            return instid;
        }

        public void setInstid(String instid) {
            this.instid = instid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getDaynum() {
            return daynum;
        }

        public void setDaynum(int daynum) {
            this.daynum = daynum;
        }

        public List<ProductPhase> getPhases() {
            return phases;
        }

        public void setPhases(List<ProductPhase> phases) {
            this.phases = phases;
        }
    }

    public static class ProductProfile {
        private String productid;
        private String id;
        private String synopsis;
        private int sort;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class ProductPhase {
        private String productid;
        private String name;
        private String remark;
        private String phasedescr;

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPhasedescr() {
            return phasedescr;
        }

        public void setPhasedescr(String phasedescr) {
            this.phasedescr = phasedescr;
        }
    }

    public static class Provider{
        private String template;
        private String id;
        private String no_;
        private String name_;

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNo_() {
            return no_;
        }

        public void setNo_(String no_) {
            this.no_ = no_;
        }

        public String getName_() {
            return name_;
        }

        public void setName_(String name_) {
            this.name_ = name_;
        }
    }
}
