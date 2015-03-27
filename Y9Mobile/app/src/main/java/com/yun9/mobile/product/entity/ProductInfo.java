package com.yun9.mobile.product.entity;

/**
 * 产品信息
 * @author Kass
 */
public class ProductInfo {
	// 服务产品的图片ID
	private String imgid;
	
	// 产品ID
	private String id;   
	
	// 提供产品的公司
	private String company;
	
	// 服务名称
	private String name;
	
	// 服务价格
	private String price;
	
	// 业务介绍
	private String introduce;
	
	public ProductInfo() {
		
	}
	
	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String business) {
		this.company = business;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
}
