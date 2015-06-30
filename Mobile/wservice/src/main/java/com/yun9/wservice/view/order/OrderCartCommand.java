package com.yun9.wservice.view.order;

import com.yun9.jupiter.command.JupiterCommand;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderCartCommand  extends JupiterCommand{

    private List<String> productIds;

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
