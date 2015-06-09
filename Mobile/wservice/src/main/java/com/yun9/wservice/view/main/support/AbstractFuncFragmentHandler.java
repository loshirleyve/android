package com.yun9.wservice.view.main.support;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.R;
import com.yun9.wservice.view.main.FuncFragmentHandler;
import com.yun9.wservice.view.main.MainActivity;

/**
 * Created by Leon on 15/6/9.
 */
public abstract class AbstractFuncFragmentHandler implements FuncFragmentHandler {
    private FragmentManager mFragmentManager;

    private boolean mRefresh;

    public AbstractFuncFragmentHandler(FragmentManager fragmentManager) {
        this.setFragmentManager(fragmentManager);
    }

    protected FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    protected void setFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    protected void pushFragment(JupiterFragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.main_fl_content, fragment,
                MainActivity.class.getName());
        ft.commit();
        this.setRefresh(false);
    }

    @Override
    public void setRefresh(boolean refresh) {
        this.mRefresh = refresh;
    }

    protected boolean getRefresh(){
        return this.mRefresh;
    }

}
