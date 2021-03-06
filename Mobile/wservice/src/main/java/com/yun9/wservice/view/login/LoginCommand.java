package com.yun9.wservice.view.login;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/9.
 */
public class LoginCommand extends JupiterCommand{
    private boolean demo;
    private String userno;
    private boolean newRegisterUser;

    public boolean isNewRegisterUser() {
        return newRegisterUser;
    }

    public LoginCommand setNewRegisterUser(boolean newRegisterUser) {
        this.newRegisterUser = newRegisterUser;
        return this;
    }

    public String getUserno() {
        return userno;
    }

    public LoginCommand setUserno(String userno) {
        this.userno = userno;
        return this;
    }

    public boolean isDemo() {
        return demo;
    }

    public LoginCommand setDemo(boolean demo) {
        this.demo = demo;
        return this;
    }
}
