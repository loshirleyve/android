package com.yun9.jupiter.push.support;

import com.baidu.frontia.FrontiaApplication;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.push.PushFactory;

/**
 * Created by Leon on 15/5/26.
 */
public class BaiduPushFactory implements PushFactory,Bean,Initialization {
    @Override
    public Class<?> getType() {
        return PushFactory.class;
    }


    @Override
    public void init(BeanManager beanManager) {
        //初始化Push
        FrontiaApplication.initFrontiaApplication(beanManager.getApplicationContext());
    }
}
