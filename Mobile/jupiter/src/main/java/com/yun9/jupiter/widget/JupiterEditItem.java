package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Leon on 15/6/2.
 */
public class JupiterEditItem extends JupiterRelativeLayout {
    public JupiterEditItem(Context context) {
        super(context);
    }

    public JupiterEditItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterEditItem(Context context, AttributeSet attrs, int defStyle) {
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
