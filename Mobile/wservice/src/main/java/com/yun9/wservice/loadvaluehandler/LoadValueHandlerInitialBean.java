package com.yun9.wservice.loadvaluehandler;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.form.FormUtilFactory;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class LoadValueHandlerInitialBean implements Initialization,Bean {

    @Override
    public Class<?> getType() {
        return LoadValueHandlerInitialBean.class;
    }

    @Override
    public void init(BeanManager beanManager) {
        FormUtilFactory.getInstance()
                .registerLoadValueHandler(FormUtilFactory.LoadValueHandler.TYPE_USER,
                        new LoadUserValueHandler());
        FormUtilFactory.getInstance()
                .registerLoadValueHandler(FormUtilFactory.LoadValueHandler.TYPE_ORG,
                        new LoadOrgValueHandler());
    }
}
