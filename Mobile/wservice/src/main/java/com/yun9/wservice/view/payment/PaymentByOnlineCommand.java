package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentByOnlineCommand extends JupiterCommand {

    private String payRegisterId;
    private String source;
    private String sourceid;
    private double amount;
    private String instId;
    private String createBy;

    public String getPayRegisterId() {
        return payRegisterId;
    }

    public PaymentByOnlineCommand setPayRegisterId(String payRegisterId) {
        this.payRegisterId = payRegisterId;
        return this;
    }

    public String getSource() {
        return source;
    }

    public PaymentByOnlineCommand setSource(String source) {
        this.source = source;
        return this;
    }

    public String getSourceid() {
        return sourceid;
    }

    public PaymentByOnlineCommand setSourceid(String sourceid) {
        this.sourceid = sourceid;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentByOnlineCommand setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getInstId() {
        return instId;
    }

    public PaymentByOnlineCommand setInstId(String instId) {
        this.instId = instId;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public PaymentByOnlineCommand setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }
}
