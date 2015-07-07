package com.yun9.wservice.handler;

import android.app.Activity;

import com.yun9.wservice.model.PushMessageBean;

/**
 * Created by Leon on 15/7/6.
 */
public interface MessageProcessHandler {
    public void process(Activity activity, PushMessageBean pushMessageBean);

    public String getType();

}
