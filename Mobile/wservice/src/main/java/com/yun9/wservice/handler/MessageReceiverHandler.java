package com.yun9.wservice.handler;

import android.content.Context;

import com.yun9.wservice.model.PushMessageBean;

import java.util.Map;

/**
 * Created by Leon on 15/5/27.
 */
public interface MessageReceiverHandler {

    public void sendMessage(Context context, String pushContent,Map<String,String> extra);

}
