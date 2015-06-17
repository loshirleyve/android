package com.yun9.wservice;


import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.Y9ImageDownloader;
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
