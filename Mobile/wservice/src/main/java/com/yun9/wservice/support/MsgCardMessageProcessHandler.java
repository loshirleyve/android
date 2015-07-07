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
 *
 * 消息格式
 * {
 *  "type": "msgcard",
 *  "data": {
 *      "msgcardid": "10000000594150"
 *  }
 *}
 */
public class MsgCardMessageProcessHandler implements MessageProcessHandler {
    @Override
    public void process(Activity activity, PushMessageBean pushMessageBean) {

        //获取消息卡片id
        if (AssertValue.isNotNull(pushMessageBean) && AssertValue.isNotNull(pushMessageBean.getData()) && AssertValue.isNotNull(pushMessageBean.getData().get("msgcardid"))) {
            String msgCardid = pushMessageBean.getData().get("msgcardid").getAsString();

            MsgCardDetailCommand msgCardDetailCommand = new MsgCardDetailCommand();
            msgCardDetailCommand.setMsgCardId(msgCardid);
            MsgCardDetailActivity.start(activity, msgCardDetailCommand);
        }
    }

    @Override
    public String getType() {
        return "msgcard";
    }
}
