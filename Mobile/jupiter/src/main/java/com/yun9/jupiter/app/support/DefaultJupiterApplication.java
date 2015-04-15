package com.yun9.jupiter.app.support;

import android.app.Application;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.app.JupiterApplicationConfigure;
import com.yun9.jupiter.util.Logger;

/**
 * Created by Leon on 15/4/15.
 */
public class DefaultJupiterApplication extends Application implements JupiterApplication,Bean {

    public static DefaultJupiterApplication mInstance;

    private BeanManager beanManager;

    private static final Logger logger = Logger
            .getLogger(DefaultJupiterApplication.class);

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
        this.beanManager = JupiterApplicationConfigure.getInstance().createBeanManager(this.getApplicationContext());

    }

    public BeanManager getBeanManager(){
        return this.beanManager;
    }


    @Override
    public Class<?> getType() {
        return JupiterApplication.class;
    }
}
