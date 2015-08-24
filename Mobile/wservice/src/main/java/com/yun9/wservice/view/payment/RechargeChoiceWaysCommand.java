package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.wservice.model.PayModeType;

/**
 * Created by huangbinglong on 15/6/23.
 */
public class RechargeChoiceWaysCommand extends JupiterCommand{

    private PayModeType selectedType;

    public PayModeType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(PayModeType selectedType) {
        this.selectedType = selectedType;
    }
}
