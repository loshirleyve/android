package com.yun9.wservice.support;

import android.app.Activity;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.handler.MessageProcessHandler;
import com.yun9.wservice.model.PushMessageBean;
import com.yun9.wservice.view.demo.DemoFormActivity;
import com.yun9.wservice.view.msgcard.MsgCardDetailActivity;
import com.yun9.wservice.view.msgcard.MsgCardDetailCommand;

/**
 * Created by Leon on 15/7/6.
 * <p/>
 * 消息格式
 * {
 * "type": "msgcard",
 * "data": {
 * "msgcardid": "10000000594150"
 * }
 * }
 */
public class MsgCardMessageProcessHandler implements MessageProcessHandler {
    @Override
    public void process(Activity activity, PushMessageBean pushMessageBean) {
        String targetId = pushMessageBean.getExtra("targetId");

        if (AssertValue.isNotNullAndNotEmpty(targetId)) {
            MsgCardDetailCommand msgCardDetailCommand = new MsgCardDetailCommand();
            msgCardDetailCommand.setMsgCardId(targetId);
            MsgCardDetailActivity.start(activity, msgCardDetailCommand);
        }
    }

    @Override
    public String getType() {
        return "msgcard";
    }
}
