package com.yun9.mobile.framework.model.server.sale;

import java.io.Serializable;
import java.util.List;

/**
 * 产品详细信息
 * @author Kass
 */
@SuppressWarnings("serial")
public class ModelProductDetailInfo implements Serializable {
	// 创建人
	private String createby;
	
	// 更新人
	private String updateby;
	
	// 创建日期
	private long createdate;
	
	// 更新日期
	private long updatedate;
	
	// 
	private int disabled;
	
	// 备注
	private String remark;
	
	// 产品ID
	private String id;
	
	// 服务产品的图片ID
	private String imgid;
	
	// 机构id
	private String instid; 
	
	// 机构名称
	private String instname; 
	
	// 服务序列号
	private String sn;  
	
	// 服务名称
	private String name; 
	
	// 服务分类
	private String category;
	
	// 产品类型
	private String type;    
	
	// 服务价格
	private int saleprice; 
	
	// 收费类型说明
	private String codename;
	
	// 产品服务次数
	private int times;    
	
	// 办理天数
	private int daynum;   
	
	// 服务周期
	private String cycle;   
	
	// 选择的周期的取值
	private String cyclevalue;
	
	// 付款要求
	private String payrequire;  
	
	// 文字简介
	private String introduce; 
	
	// 简介的链接地址
	private String introduceurl;
	
	// 产品附加信息（集合）
	private List<ModelProductExtras> productsetRequirement;
	
	public ModelProductDetailInfo() {
		
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public String getInstid() {
		return instid;
	}

	public void setInstid(String instid) {
		this.instid = instid;
	}

	public String getInstname() {
		return instname;
	}

	public void setInstname(String instname) {
		this.instname = instname;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getDaynum() {
		return daynum;
	}

	public void setDaynum(int daynum) {
		this.daynum = daynum;
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

	public String getPayrequire() {
		return payrequire;
	}

	public void setPayrequire(String payrequire) {
		this.payrequire = payrequire;
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

	public List<ModelProductExtras> getProductsetRequirement() {
		return productsetRequirement;
	}

	public void setProductsetRequirement(
			List<ModelProductExtras> productsetRequirement) {
		this.productsetRequirement = productsetRequirement;
	}

}
