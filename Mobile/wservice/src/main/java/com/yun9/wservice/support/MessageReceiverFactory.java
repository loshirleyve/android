package com.yun9.wservice.support;

import android.content.Context;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.handler.MessageReceiverHandler;
import com.yun9.wservice.model.PushMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/27.
 */
public class MessageReceiverFactory implements Bean{

    private List<MessageReceiverHandler> messageReceiverHandlerList;

    public void regHandler(MessageReceiverHandler handler){
        if (!AssertValue.isNotNull(messageReceiverHandlerList)){
            this.messageReceiverHandlerList = new ArrayList<MessageReceiverHandler>();
        }
        this.messageReceiverHandlerList.add(handler);
    }

    public void sendMsg(Context context,PushMessageBean message){

        MessageReceiverHandler handler = this.findHandler(message.getType());

        if (AssertValue.isNotNull(handler)){
            handler.sendMessage(context,message);
        }

    }

    private MessageReceiverHandler findHandler(String type){

        if (AssertValue.isNotNullAndNotEmpty(this.messageReceiverHandlerList)){
            for(MessageReceiverHandler handler :this.messageReceiverHandlerList){
                if (handler.getType().equals(type)){
                    return handler;
                }
            }
        }

        return null;
    }

    @Override
    public Class<?> getType() {
        return MessageReceiverFactory.class;
    }
}
