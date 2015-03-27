package com.yun9.mobile.framework.model.server.sale;

/**
 * 产品附加信息
 * @author Kass
 */
public class ModelProductExtras {
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
	
	// 产品附加信息ID
	private String productsetid;
	
	// 附属信息编号
	private String sn;
	
	// 附属信息名称
	private String name;
	
	// 是否要求输入内容。none无需要，text输入文字
	private String inputtype;
	
	// 要求的附件类型。用文件扩展名表达,多种格式通过“;”隔开。例如：jpg;png;doc，如果为none则不需要文件
	private String inputfiletype;
	
	// 要求说明
	private String inputdesc;
	
	// 样板图片的URL地址
	private String demoimageurl;

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

	public String getProductsetid() {
		return productsetid;
	}

	public void setProductsetid(String productsetid) {
		this.productsetid = productsetid;
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
