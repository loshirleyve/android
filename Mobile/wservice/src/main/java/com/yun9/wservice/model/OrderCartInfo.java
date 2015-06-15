package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * 订单预览实体
 * Created by huangbinglong on 15/6/13.
 */
public class OrderCartInfo implements Serializable{

    private String proxyinstid;
    private long total;
    private String purchase;
    private String proxyman;
    private Provider provideinstinfo;
    private List<OrderCartProduct> productinfos;


    public static class OrderCartProduct implements Serializable {
        private String imgid;
        private String introduce;
        private long saleprice;
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
    }

    public static class ProductProfile {
        private String productid;
        private String id;
        private String synopsis;
        private int sort;
    }

    public static class ProductPhase {
        private String productid;
        private String name;
        private String remark;
    }

    public static class Provider{
        private String template;
        private String id;
        private String no_;
        private String name_;
    }
}
