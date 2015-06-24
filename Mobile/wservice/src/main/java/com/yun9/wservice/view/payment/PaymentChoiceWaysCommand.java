package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.wservice.model.RechargeType;

/**
 * Created by huangbinglong on 15/6/23.
 */
public class PaymentChoiceWaysCommand extends JupiterCommand{

    private RechargeType selectedType;

    public RechargeType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(RechargeType selectedType) {
        this.selectedType = selectedType;
    }
}
