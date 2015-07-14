package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 7/14/15.
 */
public class PaymentResultCommand extends JupiterCommand{

    private String source;

    private String sourceId;

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

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
