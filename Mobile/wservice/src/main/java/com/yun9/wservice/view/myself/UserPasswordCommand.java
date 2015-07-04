package com.yun9.wservice.view.myself;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by li on 2015/7/3.
 */
public class UserPasswordCommand extends JupiterCommand {
    public static final String PARAM_PWD = "password";
    private String userId;
    private String oldPwd;
    private String newPwd;
    private String surePwd;


    public String getUserId() {
        return userId;
    }

    public UserPasswordCommand setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public UserPasswordCommand setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
        return this;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public UserPasswordCommand setNewPwd(String newPwd) {
        this.newPwd = newPwd;
        return this;
    }

    public String getSurePwd() {
        return surePwd;
    }

    public void setSurePwd(String surePwd) {
        this.surePwd = surePwd;
    }
}
