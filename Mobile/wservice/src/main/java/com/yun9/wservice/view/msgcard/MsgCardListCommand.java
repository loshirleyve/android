package com.yun9.wservice.view.msgcard;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/23.
 */
public class MsgCardListCommand extends JupiterCommand {

    public static final String TYPE_TOUSER = "touser";

    public static final String TYPE_USER_GIVEME = "usergiveme";

    private String type;
    private String userid;
    private String fromuserid;

    public String getType() {
        return type;
    }

    public MsgCardListCommand setType(String type) {
        this.type = type;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public MsgCardListCommand setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getFromuserid() {
        return fromuserid;
    }

    public MsgCardListCommand setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
        return this;
    }
}
