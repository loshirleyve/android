package com.yun9.mobile.serviceorder.callback;

import java.util.List;

import com.yun9.mobile.framework.model.ServiceOrder;

public interface AsyncHttpServiceOrderListCallback {
	public void handler(List<ServiceOrder> orders);

}
