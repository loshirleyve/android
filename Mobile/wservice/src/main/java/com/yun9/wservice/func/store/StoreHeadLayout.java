package com.yun9.wservice.func.store;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;

/**
 * Created by xia on 2015/5/19.
 */
public class StoreHeadLayout extends JupiterRelativeLayout {
    public StoreHeadLayout(Context context) {
        super(context);
    }

    public StoreHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreHeadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return 0;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }

    private void initView(){
    }
}
