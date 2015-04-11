package com.yun9.wservice.sys;

import android.content.Context;

import com.yun9.wservice.bean.BeanContext;
import com.yun9.wservice.bean.support.DefaultBeanContext;
import com.yun9.wservice.util.AssertValue;

/**
 * 应用基础入口,单例对象。
 * 通过命令模式初始化
 *
 * Created by Leon on 15/4/11.
 */
public class AppConfig {

    private static AppConfig instance;

    private boolean inited = false;

    private Context context;

    private BeanContext beanContext;

    private AppConfig(){

    }

    public static AppConfig getInstance(){
        if (!AssertValue.isNotNull(instance)) {
            instance = new AppConfig();
        }
        return instance;
    }

    public void init(Context context){

        //如果还未执行初始化，则执行。
        if (!inited){
            this.context = context;

            //初始化Bean
            beanContext = new DefaultBeanContext(context);
            //初始化Repository


            //其他注入的初始化动作

        }

    }
}
