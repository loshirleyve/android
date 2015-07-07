package com.yun9.wservice.view.client;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by huangbinglong on 7/7/15.
 */
public class EditClientCommand extends JupiterCommand{

    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * 是否是编辑客户信息
     * 如果clientId不为空则为编辑
     * @return
     */
    public boolean isEdit() {
        return AssertValue.isNotNullAndNotEmpty(this.clientId);
    }
}
