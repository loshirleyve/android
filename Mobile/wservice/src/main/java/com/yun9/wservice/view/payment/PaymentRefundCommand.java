package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 7/14/15.
 */
public class PaymentRefundCommand extends JupiterCommand{

    private String orderId;
    private Double refundamount;

    public String getOrderId() {
        return orderId;
    }

    public PaymentRefundCommand setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Double getRefundamount() {
        return refundamount;
    }

    public PaymentRefundCommand setRefundamount(Double refundamount) {
        this.refundamount = refundamount;
        return this;
    }
}
