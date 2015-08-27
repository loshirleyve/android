package com.yun9.wservice.view.order;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.WordOrder;

/**
 * Created by huangbinglong on 7/16/15.
 */
public class OrderCommentCommand extends JupiterCommand{

    private String orderId;
    private WordOrder workOrder;

    public String getOrderId() {
        return orderId;
    }

    public OrderCommentCommand setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public WordOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WordOrder workOrder) {
        this.workOrder = workOrder;
    }
}
