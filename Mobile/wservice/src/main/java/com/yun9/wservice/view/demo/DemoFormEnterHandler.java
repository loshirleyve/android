package com.yun9.wservice.view.demo;

import android.app.Activity;
import android.os.Bundle;

import com.yun9.jupiter.navigation.FuncEnterHandler;
import com.yun9.jupiter.navigation.NavigationBean;

/**
 * Created by huangbinglong on 15/6/10.
 */
public class DemoFormEnterHandler implements FuncEnterHandler {
    @Override
    public void enter(Activity activity, Bundle bundle, NavigationBean navigationBean) {
        DemoFormActivity.start(activity,bundle);
    }
}
