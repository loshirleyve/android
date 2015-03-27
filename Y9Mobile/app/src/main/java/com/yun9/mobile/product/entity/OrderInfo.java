package com.yun9.mobile.product.entity;

import java.util.List;

public class OrderInfo {
    // 服务产品id
	private String productid;
	
	// 附加资料
	private List<OrderAttachmentsInfo> attachments;
	
	public OrderInfo() {
		
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public List<OrderAttachmentsInfo> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<OrderAttachmentsInfo> attachments) {
		this.attachments = attachments;
	}

}
