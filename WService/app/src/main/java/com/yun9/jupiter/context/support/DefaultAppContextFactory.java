package com.yun9.jupiter.context.support;

import android.content.Context;

import com.yun9.jupiter.bean.support.DefaultBeanManager;
import com.yun9.jupiter.context.AppContext;
import com.yun9.jupiter.context.AppContextFactory;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/4/13.
 */
public class DefaultAppContextFactory implements AppContextFactory {

    private  static AppContextFactory instance;

    private DefaultAppContextFactory (){

    }

    public static AppContextFactory getInstance(){

        if (!AssertValue.isNotNull(instance)){
           instance = new DefaultAppContextFactory();
        }

        return instance;
    }

    @Override
    public AppContext create(Context context) {
        //创建应用程序上下文
        DefaultAppContext appContext = new DefaultAppContext();
        //初始化beanManager,其他初始化由bean的init周期完成初始化
        DefaultBeanManager beanManager = new DefaultBeanManager(context);
        appContext.setBeanManager(beanManager);

        return appContext;
    }
}
