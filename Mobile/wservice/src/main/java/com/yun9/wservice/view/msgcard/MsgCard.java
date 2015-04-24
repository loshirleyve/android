package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/24.
 */
public class MsgCard extends JupiterRelativeLayout {
    public MsgCard(Context context) {
        super(context);
    }

    public MsgCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msgcard;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }


}
