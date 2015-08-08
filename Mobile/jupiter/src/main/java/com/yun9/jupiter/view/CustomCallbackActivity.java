package com.yun9.jupiter.view;

import android.content.Intent;

/**
 * Created by huangbinglong on 7/4/15.
 */
public abstract class CustomCallbackActivity extends JupiterFragmentActivity{

    /**
     * 注册回调
     * @param callback
     * @return requestCode
     */
    public abstract int addActivityCallback(IActivityCallback callback);

    /**
     * 注册回调，指定requestCode
     * @param requestCode
     * @param callback
     */
    public abstract void addActivityCallback(int requestCode,IActivityCallback callback);

    /**
     * 回调接口定义
     */
    public interface IActivityCallback {
        void onActivityResult(int resultCode, Intent data);
    }

    protected static final IActivityCallback EMPTY_CALL_BACK = new IActivityCallback() {
        @Override
        public void onActivityResult(int resultCode, Intent data) {

        }
    };
}
