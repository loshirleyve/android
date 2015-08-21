package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 7/14/15.
 */
public class PaymentResultCommand extends JupiterCommand{

    private String source;

    private String sourceId;

    private String instId;

    private boolean paymentDone;

    public PaymentResultCommand() {
    }

    public PaymentResultCommand(String source, String sourceId) {
        this.source = source;
        this.sourceId = sourceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public PaymentResultCommand setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public boolean isPaymentDone() {
        return paymentDone;
    }

    public PaymentResultCommand setPaymentDone(boolean paymentDone) {
        this.paymentDone = paymentDone;
        return this;
    }

    public String getInstId() {
        return instId;
    }

    public PaymentResultCommand setInstId(String instId) {
        this.instId = instId;
        return this;
    }
}
