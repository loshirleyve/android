package com.yun9.mobile.framework.impls.sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.interfaces.sale.ProductService;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.session.SessionManager;
import com.yun9.mobile.product.entity.OrderAttachmentsInfo;

public class ImplProductService implements ProductService {
	@Override
	public void getProductInfo(AsyncHttpResponseCallback callback) {
		String name = "BizSaleServiceProductQueryService";
        Map<String,Object> data = new HashMap<String, Object>();
        
		data.put(null, null);
		
		doProductService(name, data, callback);
	}
	
	@Override
	public void getProductDetailInfo(String id, AsyncHttpResponseCallback callback) {
		String name = "BizSaleServiceProductDetailInfoQueryService";
        Map<String,Object> data = new HashMap<String, Object>();
        
		data.put("id", id);
		
		doProductService(name, data, callback);
	}
	
	@Override
	public void saveOrder(String productid, List<OrderAttachmentsInfo> attachments, AsyncHttpResponseCallback callback) {
		String name = "BizSaleServiceOrderSaveService";
        Map<String,Object> data = new HashMap<String, Object>();
        
		data.put("productid", productid);
		data.put("attachments", attachments);
		
		doProductService(name, data, callback);
	}
	
	private void doProductService(String name, Map<String,Object> data, AsyncHttpResponseCallback callback) {	
		Map<String, Object> header = new HashMap<String, Object>();
		SessionManager sessionManager = BeanConfig.getInstance().getBeanContext().get(SessionManager.class);
        System.out.println(sessionManager);
        // 机构ID
        String  instid = sessionManager.getAuthInfo().getInstinfo().getId();
        // 用户ID
        String  userid = sessionManager.getAuthInfo().getUserinfo().getId();
		header.put("instid", instid);
		header.put("userid", userid);
		
		ResourceFactory resourceFactory = BeanConfig.getInstance().getBeanContext().get(ResourceFactory.class);
		Resource service = resourceFactory.create(name);
        service.setHeader(header);
        service.setParams(data);
        service.invok(callback);
	}

}
