package com.yun9.wservice.view.order;

import android.app.Activity;
import android.os.Bundle;

import com.yun9.jupiter.navigation.FuncEnterHandler;
import com.yun9.jupiter.navigation.NavigationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class DemoOrderCartEnterHandler implements FuncEnterHandler {

    @Override
    public void enter(Activity activity, Bundle bundle, NavigationBean navigationBean) {
        OrderCartCommand command = new OrderCartCommand();
        List<String> productIds = new ArrayList<>();
        productIds.add("45950000000010107");
        command.setProductIds(productIds);
        OrderCartActivity.start(activity,command);
    }
}
