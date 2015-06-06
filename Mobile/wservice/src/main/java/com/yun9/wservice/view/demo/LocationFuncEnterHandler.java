package com.yun9.wservice.view.demo;

import android.content.Context;
import android.os.Bundle;

import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.navigation.FuncEnterHandler;
import com.yun9.jupiter.navigation.NavigationBean;
import com.yun9.jupiter.navigation.NavigationHandler;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/6/6.
 */
public class LocationFuncEnterHandler implements FuncEnterHandler,Initialization {


    @Override
    public void init(BeanManager beanManager) {

    }

    @Override
    public void enter(Context context,Bundle bundle,NavigationBean navigationBean) {
        LocationActivity.start(context,bundle);
    }
}
