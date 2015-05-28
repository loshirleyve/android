package com.yun9.jupiter.app;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.util.Logger;


public class JupiterApplication extends Application implements Bean {

    public static JupiterApplication mInstance;

    private static BeanManager beanManager;

    private static final Logger logger = Logger
            .getLogger(JupiterApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        logger.d("onCreate");
        // 初始化
        this.init();
    }

    private void init() {
        //设置日志级别
        Logger.setDebug(true);
        //初始化应用程序上下文
        beanManager = JupiterApplicationConfigure.getInstance().createBeanManager(this.getApplicationContext());
    }

    public static BeanManager getBeanManager() {
        return beanManager;
    }


    @Override
    public Class<?> getType() {
        return JupiterApplication.class;
    }
}
