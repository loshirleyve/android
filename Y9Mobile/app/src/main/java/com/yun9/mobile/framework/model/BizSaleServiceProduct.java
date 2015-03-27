package com.yun9.mobile.framework.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BizSaleServiceProduct implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String instid;

	private String sn;

	private String name;

	private String category;

	private String type;

	private BigDecimal saleprice;

	private BigDecimal times;

	private int daynum;

	private String cycle;

	private String cyclevalue;

	private String payrequire;

	private String introduce;

	private String introduceurl;

	private String imgid;

	private String instname;

	private String codename;

	private String no;

	private List<BizSaleServiceProductsetRequirement> bizSaleServiceProductsetRequirement = new ArrayList<BizSaleServiceProductsetRequirement>();

	public List<BizSaleServiceProductsetRequirement> getBizSaleServiceProductsetRequirement() {
		return bizSaleServiceProductsetRequirement;
	}

	public void setBizSaleServiceProductsetRequirement(
			List<BizSaleServiceProductsetRequirement> bizSaleServiceProductsetRequirement) {
		this.bizSaleServiceProductsetRequirement = bizSaleServiceProductsetRequirement;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getInstname() {
		return instname;
	}

	public void setInstname(String instname) {
		this.instname = instname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstid() {
		return instid;
	}

	public void setInstid(String instid) {
		this.instid = instid;
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

	public BigDecimal getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}

	public BigDecimal getTimes() {
		return times;
	}

	public void setTimes(BigDecimal times) {
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

	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

}
