package com.yun9.wservice.model.wrapper;

import com.yun9.wservice.model.OrderCartInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 7/7/15.
 */
public class OrderCartInfoWrapper implements Serializable{

    private List<OrderCartInfo> orderViews;

    public List<OrderCartInfo> getOrderViews() {
        return orderViews;
    }

    public void setOrderViews(List<OrderCartInfo> orderViews) {
        this.orderViews = orderViews;
    }
}
