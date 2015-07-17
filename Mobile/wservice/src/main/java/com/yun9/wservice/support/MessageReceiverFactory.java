package com.yun9.wservice.support;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.wservice.handler.MessageProcessHandler;
import com.yun9.wservice.handler.MessageReceiverHandler;
import com.yun9.wservice.model.PushMessageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/27.
 */
public class MessageReceiverFactory implements Bean, Initialization {

    private MessageReceiverHandler messageReceiverHandlerList;

    private List<MessageProcessHandler> messageProcessHandlers;

    public void regHandler(MessageReceiverHandler handler) {
        messageReceiverHandlerList = handler;
    }

    public void addProcessHandler(MessageProcessHandler handler) {
        if (!AssertValue.isNotNull(messageProcessHandlers)) {
            this.messageProcessHandlers = new ArrayList<>();
        }

        this.messageProcessHandlers.add(handler);
    }

    public void sendMsg(Context context, String pushContent, Map<String, String> extra) {
        if (AssertValue.isNotNull(messageReceiverHandlerList)) {
            messageReceiverHandlerList.sendMessage(context, pushContent, extra);
        }
    }

    public void processMsg(Activity activity, String pushMsg, Map<String, String> extra) {
        PushMessageBean pushMessageBean = this.createMsgBean(pushMsg, extra);
        String targetType = "";

        if (AssertValue.isNotNullAndNotEmpty(extra)) {
            targetType = extra.get("targetType");
        }

        if (AssertValue.isNotNull(pushMessageBean)) {
            MessageProcessHandler messageProcessHandler = this.findMessageProcessHandler(targetType);

            if (AssertValue.isNotNull(messageProcessHandler)) {
                messageProcessHandler.process(activity, pushMessageBean);
            }
        }
    }

    private MessageProcessHandler findMessageProcessHandler(String type) {
        MessageProcessHandler messageProcessHandler = null;

        if (AssertValue.isNotNull(type) && AssertValue.isNotNullAndNotEmpty(messageProcessHandlers)) {
            for (MessageProcessHandler tempHandler : messageProcessHandlers) {
                if (type.equals(tempHandler.getType())) {
                    messageProcessHandler = tempHandler;
                    break;
                }
            }
        }

        return messageProcessHandler;
    }

    private PushMessageBean createMsgBean(String pushContent, Map<String, String> extra) {

        PushMessageBean messageBean = new PushMessageBean();
        messageBean.setDesc(pushContent);
        messageBean.setExtra(extra);
        return messageBean;
    }

    @Override
    public Class<?> getType() {
        return MessageReceiverFactory.class;
    }

    @Override
    public void init(BeanManager beanManager) {
        //注册消息处理器
        this.addProcessHandler(new MsgCardMessageProcessHandler());
    }
}
