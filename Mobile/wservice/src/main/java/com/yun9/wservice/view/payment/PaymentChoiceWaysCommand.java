package com.yun9.wservice.view.payment;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.wservice.model.Payinfo;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentChoiceWaysCommand extends JupiterCommand{

    public static final String RETURN_PARAM_PAYMODE = "paymode";
    public static final String RETURN_PARAM_HAS_TICKET = "has_ticket";
    public static final String RETURN_PARAM_SELECTED_TICKET_INDEX = "selected_ticket_index";

    private Payinfo.PaymodeCategory category;

    public Payinfo.PaymodeCategory getCategory() {
        return category;
    }

    public void setCategory(Payinfo.PaymodeCategory category) {
        this.category = category;
    }
}
