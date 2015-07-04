package com.yun9.wservice.view.myself;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by li on 2015/7/3.
 */
public class UserPwdCommand extends JupiterCommand {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public UserPwdCommand setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
