package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/29.
 */
public class MsgCardInDetailWidget extends JupiterRelativeLayout {
    public MsgCardInDetailWidget(Context context) {
        super(context);
    }

    public MsgCardInDetailWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardInDetailWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_in_detail;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }
}
