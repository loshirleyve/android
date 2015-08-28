package com.yun9.wservice.view.order;

import com.yun9.jupiter.command.JupiterCommand;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class WorkOrderCommand extends JupiterCommand {

    private String orderid;
    private String workorderno;
    private String source;

    public String getOrderid() {
        return orderid;
    }

    public WorkOrderCommand setOrderid(String orderid) {
        this.orderid = orderid;
        return this;
    }

    public String getWorkorderno() {
        return workorderno;
    }

    public WorkOrderCommand setWorkorderno(String workorderno) {
        this.workorderno = workorderno;
        return this;
    }

    public String getSource() {
        return source;
    }

    public WorkOrderCommand setSource(String source) {
        this.source = source;
        return this;
    }

}
