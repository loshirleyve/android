package com.yun9.wservice.view.main.support;

import android.support.v4.app.FragmentManager;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.view.dynamic.DynamicSessionFragment;
import com.yun9.wservice.view.main.FuncFragmentHandler;

/**
 * Created by Leon on 15/6/9.
 */
public class DynamicFuncFragmentHandler extends AbstractFuncFragmentHandler {

    private DynamicSessionFragment dynamicSessionFragment;

    public DynamicFuncFragmentHandler(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public boolean needLogin() {
        return true;
    }

    @Override
    public boolean support(String type) {
        if (FuncFragmentHandler.FUNC_DYNAMIC.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void switchFragment() {
        if (!AssertValue.isNotNull(dynamicSessionFragment) || this.getRefresh()) {
            dynamicSessionFragment = DynamicSessionFragment.newInstance(null);
        }



        this.pushFragment(dynamicSessionFragment);
    }
}
