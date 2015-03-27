package com.yun9.mobile.serviceorder.support;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yun9.mobile.department.support.DataInfoService;
import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.http.AsyncHttpResponseCallback;
import com.yun9.mobile.framework.http.Response;
import com.yun9.mobile.framework.model.BizSaleOrderList;
import com.yun9.mobile.framework.model.ServiceOrder;
import com.yun9.mobile.framework.resources.Resource;
import com.yun9.mobile.framework.resources.ResourceFactory;
import com.yun9.mobile.framework.util.AssertValue;
import com.yun9.mobile.serviceorder.callback.AsyncHttpServiceOrderCallback;
import com.yun9.mobile.serviceorder.callback.AsyncHttpServiceOrderCodeCallback;
import com.yun9.mobile.serviceorder.callback.AsyncHttpServiceOrderListCallback;

public class ServiceOrderService {

	// 获取订单分组后数量 的回调函数
	public void getGroupOrderCallBack(Map<String, Object> params,
			final AsyncHttpServiceOrderCallback callback) {
		getGroupOrder(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				BizSaleOrderList order = (BizSaleOrderList) response
						.getPayload();
				callback.handler(order);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 获取订单分组后数量
	public void getGroupOrder(Map<String, Object> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource grouporderService = resourceFactory
				.create("BizSaleServiceOrderCateGoryQueryService");
		DataInfoService date = new DataInfoService();
		grouporderService.header("buyerinstid", date.getUserInst().getId());
		grouporderService.param("", "");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				grouporderService.param(key, params.get(key));
			}
		}
		try {
			grouporderService.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据机构id和订单状态查询订单详情 的回调函数
	public void getServiceOrderByStateCallBack(Map<String, Object> params,
			final AsyncHttpServiceOrderListCallback callback) {
		getServiceOrderByState(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				List<ServiceOrder> orders = (List<ServiceOrder>) response
						.getPayload();
				callback.handler(orders);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 根据机构id和订单状态查询订单详情
	public void getServiceOrderByState(Map<String, Object> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource getServiceOrderByState = resourceFactory
				.create("GetServiceOrderByState");
		getServiceOrderByState.param("", "");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				getServiceOrderByState.param(key, params.get(key));
			}
		}
		try {
			getServiceOrderByState.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 登记待付款订单 的回调函数
	public void payOrderCallBack(Map<String, String> params,
			final AsyncHttpServiceOrderCodeCallback callback) {
		payOrder(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				String code = (String) response.getCode();
				callback.handler(code);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 登记待付款订单
	public void payOrder(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource bizSaleServiceAddCollectService = resourceFactory
				.create("BizSaleServiceAddCollectService");
		DataInfoService info = new DataInfoService();
		params.put("payerinstid", info.getUserInst().getId());
		params.put("collectdate", String.valueOf(new Date().getTime()));
		params.put("overduerate", "0");

		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				bizSaleServiceAddCollectService.param(key, params.get(key));
			}
		}
		try {
			bizSaleServiceAddCollectService.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 订单服务后的评论 的回调函数
	public void orderCommentCallBack(Map<String, String> params,
			final AsyncHttpServiceOrderCodeCallback callback) {
		orderComment(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				String code = (String) response
						.getCode();
				callback.handler(code);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 订单服务后的评论
	public void orderComment(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource bizSaleServiceOrderCommentTextSaveOrUpService = resourceFactory
				.create("BizSaleServiceOrderCommentTextSaveOrUpService");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				bizSaleServiceOrderCommentTextSaveOrUpService.param(key,
						params.get(key));
			}
		}
		try {
			bizSaleServiceOrderCommentTextSaveOrUpService.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 订单服务后的分数 的回调函数
	public void orderScoreCallBack(Map<String, String> params,
			final AsyncHttpServiceOrderCodeCallback callback) {
		orderScore(params, new AsyncHttpResponseCallback() {
			@Override
			public void onSuccess(Response response) {
				String code = (String) response.getCode();
				callback.handler(code);
			}

			@Override
			public void onFailure(Response response) {
				callback.handler(null);
			}
		}

		);
	}

	// 订单服务后的分数
	public void orderScore(Map<String, String> params,
			AsyncHttpResponseCallback callback) {
		ResourceFactory resourceFactory = BeanConfig.getInstance()
				.getBeanContext().get(ResourceFactory.class);
		Resource bizSaleServiceOrderCommentScoreSaveOrUpService = resourceFactory
				.create("BizSaleServiceOrderCommentScoreSaveOrUpService");
		if (AssertValue.isNotNullAndNotEmpty(params)) {
			for (String key : params.keySet()) {
				bizSaleServiceOrderCommentScoreSaveOrUpService.param(key,
						params.get(key));
			}
		}
		try {
			bizSaleServiceOrderCommentScoreSaveOrUpService.invok(callback);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
