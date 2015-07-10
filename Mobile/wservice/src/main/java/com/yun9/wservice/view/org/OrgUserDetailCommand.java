package com.yun9.wservice.view.org;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by huangbinglong on 7/9/15.
 */
public class OrgUserDetailCommand extends JupiterCommand{

    private String userId;

    private String instId;

    public String getUserId() {
        return userId;
    }

    public OrgUserDetailCommand setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getInstId() {
        return instId;
    }

    public OrgUserDetailCommand setInstId(String instId) {
        this.instId = instId;
        return this;
    }
}
