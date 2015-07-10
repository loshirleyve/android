package com.yun9.wservice.view.inst;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by li on 2015/7/8.
 */
public class InstCommand extends JupiterCommand {
    public static String USERINFO = "command";
    private String userno;
    private String userid;

    public String getUserid() {
        return userid;
    }

    public InstCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getUserno() {
        return userno;
    }

    public InstCommand setUserno(String userno) {
        this.userno = userno;
        return this;
    }
}
