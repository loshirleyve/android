package com.yun9.wservice.model.wrapper;

import com.yun9.wservice.model.OrderBaseInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 7/6/15.
 */
public class OrderBaseInfoWrapper implements Serializable{
    private List<OrderBaseInfo> orderList;

    public List<OrderBaseInfo> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderBaseInfo> orderList) {
        this.orderList = orderList;
    }
}
