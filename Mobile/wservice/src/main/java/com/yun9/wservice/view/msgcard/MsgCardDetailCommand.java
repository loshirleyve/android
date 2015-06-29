package com.yun9.wservice.view.msgcard;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/28.
 */
public class MsgCardDetailCommand extends JupiterCommand{
    private String msgCardId;

    private String title;

    public String getMsgCardId() {
        return msgCardId;
    }

    public MsgCardDetailCommand setMsgCardId(String msgCardId) {
        this.msgCardId = msgCardId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MsgCardDetailCommand setTitle(String title) {
        this.title = title;
        return this;
    }
}
