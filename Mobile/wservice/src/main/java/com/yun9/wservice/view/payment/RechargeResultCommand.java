package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 7/14/15.
 */
public class RechargeResultCommand extends JupiterCommand{

    private String id;
    private String stateName;
    private String rechargeTypeName;
    private double amount;

    public RechargeResultCommand() {
    }

    public RechargeResultCommand(String id, String stateName, String rechargeTypeName, double amount) {
        this.id = id;
        this.stateName = stateName;
        this.rechargeTypeName = rechargeTypeName;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getRechargeTypeName() {
        return rechargeTypeName;
    }

    public void setRechargeTypeName(String rechargeTypeName) {
        this.rechargeTypeName = rechargeTypeName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
