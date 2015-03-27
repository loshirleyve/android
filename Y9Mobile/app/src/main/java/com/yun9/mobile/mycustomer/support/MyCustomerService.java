package com.yun9.mobile.mycustomer.support;

import java.util.List;
import java.util.Map;

import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.BizSaleClient;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.mycustomer.callback.AsyncHttpMyCustomerCallback;
import com.yun9.mobile.mycustomer.callback.AsyncHttpSaleClientCallback;

/**
 * 
 * 项目名称：WelcomeActivity 类名称： MyCustomerService 类描述： 创建人： ruanxiaoyu 创建时间：
 * 2015-1-9上午10:04:04 修改人：ruanxiaoyu 修改时间：2015-1-9上午10:04:04 修改备注：
 * 
 * @version
 * 
 */
public class MyCustomerService {

	// 添加客户的回调函数
	public void addCustomerCallBack(Map<String, Object> params,
			final AsyncHttpSaleClientCallback callback) {
		addCustomer(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				BizSaleClient client = (BizSaleClient) response.getPayload();
				callback.handler(client);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 添加客户
	public void addCustomer(Map<String, Object> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource saveClientService = resourceFactory
				.create("SaveClientService");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				saveClientService.param(key, params.get(key));
			}
		}
		try {
			saveClientService.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加客户的回调函数
	public void myCustomerCallBack(Map<String, String> params,
			final AsyncHttpMyCustomerCallback callback) {
		myCustomer(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<BizSaleClient> client = (List<BizSaleClient>) response
						.getPayload();
				callback.handler(client);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 添加客户
	public void myCustomer(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		DataInfoService data = new DataInfoService();

		Resource clientResource = resourceFactory
				.create("BizSaleClientServiceQueryService");
		clientResource.param("instid", data.getUserInst().getId());
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				clientResource.param(key, params.get(key));
			}
		}
		try {
			clientResource.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
