package com.yun9.wservice.bizexecutor;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.form.FormUtilFactory;

/**
 * Created by huangbinglong on 15/6/9.
 */
public class BizExecutorInitialBean implements Initialization,Bean {

    @Override
    public void init(BeanManager beanManager) {
        FormUtilFactory.getInstance()
                .registerBizExecutor(FormUtilFactory.BizExecutor.TYPE_VIEW_IMAGE,
                        new ViewImageBizExecutor());
        FormUtilFactory.getInstance()
                .registerBizExecutor(FormUtilFactory.BizExecutor.TYPE_MULTI_SELECT,
                        new MultiSelectBizExcutor());
        FormUtilFactory.getInstance()
                .registerBizExecutor(FormUtilFactory.BizExecutor.TYPE_SELECT_USER_OR_DEPT,
                        new SelectUserBizExecutor());
    }

    @Override
    public Class<?> getType() {
        return BizExecutorInitialBean.class;
    }
}
