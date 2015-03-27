package com.yun9.mobile.framework.model;

public class BizSaleServiceProductsetRequirement implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String productid;

	private String sn;

	private String name;

	private String inputtype;

	private String inputfiletype;

	private String inputdesc;

	private String demoimageurl;

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

	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public String getInputfiletype() {
		return inputfiletype;
	}

	public void setInputfiletype(String inputfiletype) {
		this.inputfiletype = inputfiletype;
	}

	public String getInputdesc() {
		return inputdesc;
	}

	public void setInputdesc(String inputdesc) {
		this.inputdesc = inputdesc;
	}

	public String getDemoimageurl() {
		return demoimageurl;
	}

	public void setDemoimageurl(String demoimageurl) {
		this.demoimageurl = demoimageurl;
	}

}
