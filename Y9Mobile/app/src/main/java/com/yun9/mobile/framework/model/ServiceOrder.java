package com.yun9.mobile.framework.model;

import java.math.BigDecimal;
import java.util.List;

public class ServiceOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; // 订单的id啊
	private String ordersn;// 订单sn
	private String buyerinstname;// 购买的机构的名字
	private String productimage;// 产品图片
	private String productname;// 产品的名字
	private String productdesc;// 产品描述
	private String orderstate;// 订单的状态
	private String orderstatecodename;// 订单状态对应的code名称
	private BigDecimal salemoney;// 服务价钱
	private BigDecimal servicetimes;// 服务的次数
	private String codename;// 服务的周期数
	private BigDecimal buymoney;// 实际购买所附的金额
	private String moneytimes; // 拼接几百每次或者每月

	private List<BizSaleServiceOrderAttachment> attahchs;

	public List<BizSaleServiceOrderAttachment> getAttahchs() {
		return attahchs;
	}

	public void setAttahchs(List<BizSaleServiceOrderAttachment> attahchs) {
		this.attahchs = attahchs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrdersn() {
		return ordersn;
	}

	public void setOrdersn(String ordersn) {
		this.ordersn = ordersn;
	}

	public String getBuyerinstname() {
		return buyerinstname;
	}

	public void setBuyerinstname(String buyerinstname) {
		this.buyerinstname = buyerinstname;
	}

	public String getProductimage() {
		return productimage;
	}

	public void setProductimage(String productimage) {
		this.productimage = productimage;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductdesc() {
		return productdesc;
	}

	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}

	public String getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}

	public String getOrderstatecodename() {
		return orderstatecodename;
	}

	public void setOrderstatecodename(String orderstatecodename) {
		this.orderstatecodename = orderstatecodename;
	}

	public BigDecimal getSalemoney() {
		return salemoney;
	}

	public void setSalemoney(BigDecimal salemoney) {
		this.salemoney = salemoney;
	}

	public BigDecimal getServicetimes() {
		return servicetimes;
	}

	public void setServicetimes(BigDecimal servicetimes) {
		this.servicetimes = servicetimes;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public BigDecimal getBuymoney() {
		return buymoney;
	}

	public void setBuymoney(BigDecimal buymoney) {
		this.buymoney = buymoney;
	}

	public String getMoneytimes() {
		return moneytimes;
	}

	public void setMoneytimes(String moneytimes) {
		this.moneytimes = moneytimes;
	}

}
