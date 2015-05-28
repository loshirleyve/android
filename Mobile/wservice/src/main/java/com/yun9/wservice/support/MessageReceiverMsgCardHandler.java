package com.yun9.wservice.support;

import android.content.Context;
import android.os.Bundle;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.handler.MessageReceiverHandler;
import com.yun9.wservice.model.PushMessageBean;
import com.yun9.wservice.func.demo.DemoFormActivity;

/**
 * Created by Leon on 15/5/27.
 */
public class MessageReceiverMsgCardHandler implements MessageReceiverHandler {
    @Override
    public void sendMessage(Context context, PushMessageBean message) {
        //TODO 打开消息卡片，通过content的解析获取消息卡片id

        Bundle bundle = new Bundle();
        bundle.putString("title",message.getTitle());
        bundle.putString("desc",message.getDesc());

        if (AssertValue.isNotNull(message.getContent()) && AssertValue.isNotNull(message.getContent().get("value"))){
            bundle.putString("content",message.getContent().get("value").getAsString());
        }

        DemoFormActivity.start(context,bundle);
    }

    @Override
    public String getType() {
        return "msgcard";
    }
}
