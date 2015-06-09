package com.yun9.wservice;


import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.form.FormUtilFactory;
import com.yun9.wservice.bizexecutor.MultiSelectBizExcutor;
import com.yun9.wservice.bizexecutor.ViewImageBizExecutor;
import com.yun9.wservice.imageloader.Y9ImageDownloader;
import com.yun9.wservice.support.MessageReceiverFactory;
import com.yun9.wservice.support.MessageReceiverMsgCardHandler;


public class MainApplication extends JupiterApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        this.regHandler();
    }

    private void regHandler() {
        MessageReceiverFactory messageReceiverFactory = this.getBeanManager().get(MessageReceiverFactory.class);
        messageReceiverFactory.regHandler(new MessageReceiverMsgCardHandler());

    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        config.imageDownloader(new Y9ImageDownloader(context)); // 设置我们自己的imageDownloader，支持文件ID格式：y9fileid://1234

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

}
