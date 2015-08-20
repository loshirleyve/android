package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.wservice.model.PayMode;

import java.util.List;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentOrderChoicePayWayCommand extends JupiterCommand {

    private PayMode payMode;

    private List<PayMode> payModes;

    public PayMode getPayMode() {
        return payMode;
    }

    public PaymentOrderChoicePayWayCommand setPayMode(PayMode payMode) {
        this.payMode = payMode;
        return this;
    }

    public List<PayMode> getPayModes() {
        return payModes;
    }

    public PaymentOrderChoicePayWayCommand setPayModes(List<PayMode> payModes) {
        this.payModes = payModes;
        return this;
    }
}
