package com.yun9.wservice.view.order;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 7/16/15.
 */
public class OrderCommentCommand extends JupiterCommand{

    private String orderId;
    private Order.OrderWorkOrder workOrder;

    public String getOrderId() {
        return orderId;
    }

    public OrderCommentCommand setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Order.OrderWorkOrder getWorkOrder() {
        return workOrder;
    }

    public OrderCommentCommand setWorkOrder(Order.OrderWorkOrder workOrder) {
        this.workOrder = workOrder;
        return this;
    }
}
