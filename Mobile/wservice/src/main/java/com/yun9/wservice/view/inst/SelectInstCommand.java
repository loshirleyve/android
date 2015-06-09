package com.yun9.wservice.view.inst;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.model.User;

/**
 * Created by Leon on 15/6/9.
 */
public class SelectInstCommand extends JupiterCommand {
    public static int REQUEST_CODE = 601;

    public static final String PARAM_INST = "inst";

    private User user;

    public User getUser() {
        return user;
    }

    public SelectInstCommand setUser(User user) {
        this.user = user;
        return this;
    }
}
