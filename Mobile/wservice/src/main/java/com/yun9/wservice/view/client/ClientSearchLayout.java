package com.yun9.wservice.view.client;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;

/**
 * Created by li on 2015/6/11.
 */
public class ClientSearchLayout extends JupiterRelativeLayout {
    public ClientSearchLayout(Context context) {
        super(context);
    }

    public ClientSearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClientSearchLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return 0;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }
}
