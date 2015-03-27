package com.yun9.mobile.framework.interfaces.sale;

import java.util.List;

import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.product.entity.OrderAttachmentsInfo;

/**
 * 产品服务
 * @author Kass
 */
public interface ProductService {
	/**
	 * 获取产品信息服务
	 * @param callback
	 */
	public void getProductInfo(AsyncHttpResponseCallback callback);
	
	/**
	 * 获取产品详细信息服务
	 * @param id
	 * @param callback
	 */
	public void getProductDetailInfo(String id, AsyncHttpResponseCallback callback);
	
	/**
	 * 提交订单服务
	 * @param orderInfo
	 * @param callback
	 */
	public void saveOrder(String productid, List<OrderAttachmentsInfo> attachments, AsyncHttpResponseCallback callback);

}
