package com.yun9.wservice.view.msgcard;

import com.yun9.jupiter.command.JupiterCommand;

/**
 * Created by Leon on 15/6/28.
 */
public class MsgCardDetailCommand extends JupiterCommand {
    private String msgCardId;

    private String title;

    private boolean scrollComment;

    private String orderId;

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

    public boolean isScrollComment() {
        return scrollComment;
    }

    public MsgCardDetailCommand setScrollComment(boolean scrollComment) {
        this.scrollComment = scrollComment;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public MsgCardDetailCommand setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
}
