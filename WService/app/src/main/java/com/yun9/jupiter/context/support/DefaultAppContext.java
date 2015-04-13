package com.yun9.jupiter.context.support;

import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.context.AppContext;

/**
 * Created by Leon on 15/4/13.
 */
public class DefaultAppContext implements AppContext {
    private BeanManager beanManager;

    public BeanManager getBeanManager() {
        return beanManager;
    }

    public void setBeanManager(BeanManager beanManager) {
        this.beanManager = beanManager;
    }
}
