package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 7/14/15.
 */
public class RechargeCommand extends JupiterCommand{

    private double amount;

    public double getAmount() {
        return amount;
    }

    public RechargeCommand setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
