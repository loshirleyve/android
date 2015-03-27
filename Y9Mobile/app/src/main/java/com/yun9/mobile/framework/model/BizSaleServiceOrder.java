package com.yun9.mobile.framework.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BizSaleServiceOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String instid;

	private String sn;

	private String state;

	private String clientid;

	private Date activatedate;

	private String salesmanid;

	private String productid;

	private BigDecimal saleamount;

	private BigDecimal factsaleamount;

	private String payrequire;

	private List<BizSaleServiceOrderAttachment> attachments = new ArrayList<BizSaleServiceOrderAttachment>();

	private List<BizSaleClient> bizSaleClients = new ArrayList<BizSaleClient>();

	private List<BizSaleServiceProduct> bizSaleServiceProducts = new ArrayList<BizSaleServiceProduct>();

	private List<User> users = new ArrayList<User>();


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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public Date getActivatedate() {
		return activatedate;
	}

	public void setActivatedate(Date activatedate) {
		this.activatedate = activatedate;
	}

	public String getSalesmanid() {
		return salesmanid;
	}

	public void setSalesmanid(String salesmanid) {
		this.salesmanid = salesmanid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public BigDecimal getSaleamount() {
		return saleamount;
	}

	public void setSaleamount(BigDecimal saleamount) {
		this.saleamount = saleamount;
	}

	public BigDecimal getFactsaleamount() {
		return factsaleamount;
	}

	public void setFactsaleamount(BigDecimal factsaleamount) {
		this.factsaleamount = factsaleamount;
	}

	public String getPayrequire() {
		return payrequire;
	}

	public void setPayrequire(String payrequire) {
		this.payrequire = payrequire;
	}

	public List<BizSaleServiceOrderAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<BizSaleServiceOrderAttachment> attachments) {
		this.attachments = attachments;
	}

	public List<BizSaleClient> getBizSaleClients() {
		return bizSaleClients;
	}

	public void setBizSaleClients(List<BizSaleClient> bizSaleClients) {
		this.bizSaleClients = bizSaleClients;
	}

	public List<BizSaleServiceProduct> getBizSaleServiceProducts() {
		return bizSaleServiceProducts;
	}

	public void setBizSaleServiceProducts(
			List<BizSaleServiceProduct> bizSaleServiceProducts) {
		this.bizSaleServiceProducts = bizSaleServiceProducts;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
