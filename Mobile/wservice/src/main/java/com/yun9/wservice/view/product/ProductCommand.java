package com.yun9.wservice.view.product;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/26.
 */
public class ProductCommand extends JupiterCommand {
    private String productid;

    public String getProductid() {
        return productid;
    }

    public ProductCommand setProductid(String productid) {
        this.productid = productid;
        return this;
    }
}
