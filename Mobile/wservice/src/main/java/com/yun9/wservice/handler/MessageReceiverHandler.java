package com.yun9.wservice.handler;

import android.content.Context;

import com.yun9.wservice.model.PushMessageBean;

/**
 * Created by Leon on 15/5/27.
 */
public interface MessageReceiverHandler {

    public void sendMessage(Context context,PushMessageBean message);

    public String getType();

}
