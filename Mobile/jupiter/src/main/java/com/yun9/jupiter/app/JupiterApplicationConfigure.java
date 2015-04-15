package com.yun9.jupiter.app;

import android.content.Context;

import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.support.DefaultBeanManager;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/4/13.
 */
public class JupiterApplicationConfigure  {

    private  static JupiterApplicationConfigure instance;

    private JupiterApplicationConfigure(){

    }

    public static JupiterApplicationConfigure getInstance(){

        if (!AssertValue.isNotNull(instance)){
           instance = new JupiterApplicationConfigure();
        }

        return instance;
    }

    public BeanManager createBeanManager(Context context) {
        //初始化beanManager,其他初始化由bean的init周期完成初始化
        DefaultBeanManager beanManager = new DefaultBeanManager(context);
        return beanManager;
    }
}
