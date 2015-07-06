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

    public void sendMsg(Context context, String pushContent) {
        if (AssertValue.isNotNull(messageReceiverHandlerList)) {
            messageReceiverHandlerList.sendMessage(context, pushContent);
        }
    }

    public void processMsg(Activity activity, String pushMsg) {
        PushMessageBean pushMessageBean = this.createMsgBean(pushMsg);

        if (AssertValue.isNotNull(pushMessageBean)) {
            MessageProcessHandler messageProcessHandler = this.findMessageProcessHandler(pushMessageBean.getType());

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

    private PushMessageBean createMsgBean(String pushContent) {
        JsonObject obj = null;
        JsonObject data = null;
        String type = null;
        try {
            obj = JsonUtil.fromString(pushContent);
            type = obj.get("type").getAsString();
            data = obj.getAsJsonObject("data");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AssertValue.isNotNull(obj) && AssertValue.isNotNullAndNotEmpty(type)) {
            PushMessageBean messageBean = new PushMessageBean();
            messageBean.setContent(obj);
            messageBean.setType(type.trim());
            messageBean.setData(data);
            return messageBean;
        } else {
            return null;
        }
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
