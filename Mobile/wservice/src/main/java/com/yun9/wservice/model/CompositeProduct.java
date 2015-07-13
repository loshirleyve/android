package com.yun9.wservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 7/10/15.
 */
public class CompositeProduct implements Serializable{

    private Product product;
    private List<ProductGroup> productGroups;
    private List<ProductPhase> productPhases;
    private List<Attachment> productRequirements;
    private List<ProductClassify> bizProductClassifies;
    private List<ProductProfile> bizProductProfiles;
    private WorkOrderComment workorderComment;

    public WorkOrderComment getWorkorderComment() {
        return workorderComment;
    }

    public void setWorkorderComment(WorkOrderComment workorderComment) {
        this.workorderComment = workorderComment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductGroup> getProductGroups() {
        return productGroups;
    }

    public void setProductGroups(List<ProductGroup> productGroups) {
        this.productGroups = productGroups;
    }

    public List<ProductPhase> getProductPhases() {
        return productPhases;
    }

    public void setProductPhases(List<ProductPhase> productPhases) {
        this.productPhases = productPhases;
    }

    public List<Attachment> getProductRequirements() {
        return productRequirements;
    }

    public void setProductRequirements(List<Attachment> productRequirements) {
        this.productRequirements = productRequirements;
    }

    public List<ProductClassify> getBizProductClassifies() {
        return bizProductClassifies;
    }

    public void setBizProductClassifies(List<ProductClassify> bizProductClassifies) {
        this.bizProductClassifies = bizProductClassifies;
    }

    public List<ProductProfile> getBizProductProfiles() {
        return bizProductProfiles;
    }

    public void setBizProductProfiles(List<ProductProfile> bizProductProfiles) {
        this.bizProductProfiles = bizProductProfiles;
    }

    public static class Product implements Serializable{
        private String id;
        private String sn;
        private String instid;
        private String name;
        private String state;
        private String type;
        private double saleprice;
        private double maxprice;
        private double minprice;
        private String pricedescr;
        private String productdescr;
        private int daynum;
        private String introduce;
        private String introduceurl;
        private String imgid;
        private Long createdate;

        public String getProductdescr() {
            return productdescr;
        }

        public void setProductdescr(String productdescr) {
            this.productdescr = productdescr;
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

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public double getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(double saleprice) {
            this.saleprice = saleprice;
        }

        public double getMaxprice() {
            return maxprice;
        }

        public void setMaxprice(double maxprice) {
            this.maxprice = maxprice;
        }

        public double getMinprice() {
            return minprice;
        }

        public void setMinprice(double minprice) {
            this.minprice = minprice;
        }

        public String getPricedescr() {
            return pricedescr;
        }

        public void setPricedescr(String pricedescr) {
            this.pricedescr = pricedescr;
        }

        public int getDaynum() {
            return daynum;
        }

        public void setDaynum(int daynum) {
            this.daynum = daynum;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getIntroduceurl() {
            return introduceurl;
        }

        public void setIntroduceurl(String introduceurl) {
            this.introduceurl = introduceurl;
        }

        public String getImgid() {
            return imgid;
        }

        public void setImgid(String imgid) {
            this.imgid = imgid;
        }

        public Long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Long createdate) {
            this.createdate = createdate;
        }
    }

    public static class ProductGroup implements Serializable{
        private String id;
        private String productid;
        private int top;
        private String backgorundimgid;
        private String groupname;
        private String province;
        private String city;
        private String district;
        private int sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public String getBackgorundimgid() {
            return backgorundimgid;
        }

        public void setBackgorundimgid(String backgorundimgid) {
            this.backgorundimgid = backgorundimgid;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
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

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

    public static class ProductPhase implements Serializable{
        private String id;
        private String productid;
        private String name;
        private int times;
        private String cycle;
        private String cyclevalue;
        private int processdays;
        private int sortno;
        private int disabled;
        private String createby;
        private Long createdate;
        private String updateby;
        private Long updatedate;
        private String remark;
        private String phasedescr;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getCyclevalue() {
            return cyclevalue;
        }

        public void setCyclevalue(String cyclevalue) {
            this.cyclevalue = cyclevalue;
        }

        public int getProcessdays() {
            return processdays;
        }

        public void setProcessdays(int processdays) {
            this.processdays = processdays;
        }

        public int getSortno() {
            return sortno;
        }

        public void setSortno(int sortno) {
            this.sortno = sortno;
        }

        public int getDisabled() {
            return disabled;
        }

        public void setDisabled(int disabled) {
            this.disabled = disabled;
        }

        public String getCreateby() {
            return createby;
        }

        public void setCreateby(String createby) {
            this.createby = createby;
        }

        public Long getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Long createdate) {
            this.createdate = createdate;
        }

        public String getUpdateby() {
            return updateby;
        }

        public void setUpdateby(String updateby) {
            this.updateby = updateby;
        }

        public Long getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(Long updatedate) {
            this.updatedate = updatedate;
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

    public static class ProductClassify implements Serializable{
        private String createby;
        private String updateby;
        private Long createdate;
        private Long updatedate;
        private int disabled;
        private String remark;
        private String id;
        private String productid;
        private int sort;
        private String cityid;
        private double price;
        private String classifyno;
        private String classifyname;

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

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getClassifyno() {
            return classifyno;
        }

        public void setClassifyno(String classifyno) {
            this.classifyno = classifyno;
        }

        public String getClassifyname() {
            return classifyname;
        }

        public void setClassifyname(String classifyname) {
            this.classifyname = classifyname;
        }
    }

}
