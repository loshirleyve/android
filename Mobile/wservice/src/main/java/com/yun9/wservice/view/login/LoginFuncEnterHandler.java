package com.yun9.wservice.view.login;

import android.content.Context;
import android.os.Bundle;

import com.yun9.jupiter.navigation.FuncEnterHandler;
import com.yun9.jupiter.navigation.NavigationBean;

/**
 * Created by Leon on 15/6/8.
 */
public class LoginFuncEnterHandler implements FuncEnterHandler {
    @Override
    public void enter(Context context, Bundle bundle, NavigationBean navigationBean) {
        LoginMainActivity.start(context,bundle);
    }
}
