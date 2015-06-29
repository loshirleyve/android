package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentOrderCommand extends JupiterCommand{

    public static final String SOURCE_ORDER = "so";

    private String source;

    private String sourceValue;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }
}
