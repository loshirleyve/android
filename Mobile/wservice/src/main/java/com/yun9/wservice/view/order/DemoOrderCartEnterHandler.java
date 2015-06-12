package com.yun9.wservice.view.order;

import android.app.Activity;
import android.os.Bundle;

import com.yun9.jupiter.navigation.FuncEnterHandler;
import com.yun9.jupiter.navigation.NavigationBean;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class DemoOrderCartEnterHandler implements FuncEnterHandler {

    @Override
    public void enter(Activity activity, Bundle bundle, NavigationBean navigationBean) {
        OrderCartActivity.start(activity,new OrderCartCommand());
    }
}
