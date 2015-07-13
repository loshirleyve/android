package com.yun9.wservice.view.inst;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;

/**
 * Created by li on 2015/7/10.
 */
public class StaffNumCatItemWidget extends JupiterRelativeLayout{
    public StaffNumCatItemWidget(Context context) {
        super(context);
    }

    public StaffNumCatItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaffNumCatItemWidget(Context context, AttributeSet attrs, int defStyle) {
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
