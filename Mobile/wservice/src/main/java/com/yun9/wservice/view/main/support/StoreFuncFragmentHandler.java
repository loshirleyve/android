package com.yun9.wservice.view.main.support;

import android.support.v4.app.FragmentManager;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.func.store.StoreFragment;
import com.yun9.wservice.view.main.FuncFragmentHandler;

/**
 * Created by Leon on 15/6/9.
 */
public class StoreFuncFragmentHandler extends AbstractFuncFragmentHandler {

    private StoreFragment storeFragment;

    public StoreFuncFragmentHandler(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public boolean needLogin() {
        return false;
    }

    @Override
    public boolean support(String type) {
        if (FuncFragmentHandler.FUNC_STORE.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void switchFragment() {
        if (!AssertValue.isNotNull(storeFragment) || this.getRefresh()){
            storeFragment = StoreFragment.newInstance(null);
        }
        this.pushFragment(storeFragment);
    }
}
