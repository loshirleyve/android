package com.yun9.wservice.view.main.support;

import android.support.v4.app.FragmentManager;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.view.main.FuncFragmentHandler;
import com.yun9.wservice.view.microapp.MicroAppFragment;
import com.yun9.wservice.view.myself.UserFragment;

/**
 * Created by Leon on 15/6/9.
 */
public class UserFuncFragmentHandler extends AbstractFuncFragmentHandler {
    private UserFragment userFragment;

    public UserFuncFragmentHandler(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public boolean needLogin() {
        return true;
    }

    @Override
    public boolean support(String type) {
        if (FuncFragmentHandler.FUNC_USER.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void switchFragment() {
        if (!AssertValue.isNotNull(userFragment) || this.getRefresh()) {
            userFragment = UserFragment.newInstance(null);
        }
        this.pushFragment(userFragment);
    }
}
