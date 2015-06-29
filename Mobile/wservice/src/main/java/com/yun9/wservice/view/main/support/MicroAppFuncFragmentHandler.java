package com.yun9.wservice.view.main.support;

import android.support.v4.app.FragmentManager;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.view.main.FuncFragmentHandler;
import com.yun9.wservice.view.microapp.MicroAppFragment;

/**
 * Created by Leon on 15/6/9.
 */
public class MicroAppFuncFragmentHandler extends AbstractFuncFragmentHandler {
    private MicroAppFragment microAppFragment;

    public MicroAppFuncFragmentHandler(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public boolean needLogin() {
        return true;
    }

    @Override
    public boolean support(String type) {
        if (FuncFragmentHandler.FUNC_MICROAPP.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void switchFragment() {
        if (!AssertValue.isNotNull(microAppFragment) || this.getRefresh()) {
            microAppFragment = MicroAppFragment.newInstance(null);
        }
        this.pushFragment(microAppFragment);
    }

    @Override
    public JupiterFragment getFragment() {
        return microAppFragment;
    }
}
