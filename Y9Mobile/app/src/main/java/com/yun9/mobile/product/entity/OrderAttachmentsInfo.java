package com.yun9.mobile.product.entity;

public class OrderAttachmentsInfo {
	// 访问权限，readonly只读, write可写，disabled禁止访问
	private String access;
	
	// 附件信息的识别key,来自产品需求的sn
	private String attachkey;
	
	// 附件信息的识别名称,来自产品需求的name
	private String attachname;
	
	// 创建人
	private String createby;
	
	// 样板图片的URL地址
	private String demoimageurl;
	
	// 要求说明
	private String inputdesc;
	
	// 用户上传的文件id
	private String inputfileid;
	
	// 要求的附件类型。用文件扩展名表达,多种格式通过“;”隔开。例如：jpg;png;doc，如果为none则不需要文件,初始化内容来自产品需求信息
	private String inputfiletype;
	
	// 是否要求输入内容。none无需要，text输入文字,初始化内容来自产品需求信息
	private String inputtype;
	
	// 用户输入的内容
	private String inputvalue;
	
	// 订单id
	private String orderid;
	
	// 备注
	private String remark;
	
	// 更新人
	private String updateby;
	
	public OrderAttachmentsInfo() {
		
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getAttachkey() {
		return attachkey;
	}

	public void setAttachkey(String attachkey) {
		this.attachkey = attachkey;
	}

	public String getAttachname() {
		return attachname;
	}

	public void setAttachname(String attachname) {
		this.attachname = attachname;
	}

	public String getCreateby() {
		return createby;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public String getDemoimageurl() {
		return demoimageurl;
	}

	public void setDemoimageurl(String demoimageurl) {
		this.demoimageurl = demoimageurl;
	}

	public String getInputdesc() {
		return inputdesc;
	}

	public void setInputdesc(String inputdesc) {
		this.inputdesc = inputdesc;
	}

	public String getInputfileid() {
		return inputfileid;
	}

	public void setInputfileid(String inputfileid) {
		this.inputfileid = inputfileid;
	}

	public String getInputfiletype() {
		return inputfiletype;
	}

	public void setInputfiletype(String inputfiletype) {
		this.inputfiletype = inputfiletype;
	}

	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public String getInputvalue() {
		return inputvalue;
	}

	public void setInputvalue(String inputvalue) {
		this.inputvalue = inputvalue;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateby() {
		return updateby;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

}
