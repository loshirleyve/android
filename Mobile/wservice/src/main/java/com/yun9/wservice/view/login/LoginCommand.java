package com.yun9.wservice.view.login;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/9.
 */
public class LoginCommand extends JupiterCommand{
    public static final int REQUEST_CODE = 5001;

    private boolean demo;

    public boolean isDemo() {
        return demo;
    }

    public LoginCommand setDemo(boolean demo) {
        this.demo = demo;
        return this;
    }
}
