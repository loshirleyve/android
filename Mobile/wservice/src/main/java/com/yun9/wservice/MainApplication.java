package com.yun9.wservice;


import android.content.Context;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.wservice.support.MessageReceiverFactory;
import com.yun9.wservice.support.MessageReceiverMsgCardHandler;


public class MainApplication extends JupiterApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        this.regHandler();
    }

    private void regHandler() {
        MessageReceiverFactory messageReceiverFactory = this.getBeanManager().get(MessageReceiverFactory.class);
        messageReceiverFactory.regHandler(new MessageReceiverMsgCardHandler());

    }
}
