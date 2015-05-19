package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/5/19.
 */
public class MsgCardDetailToolbarPanelsPageWidget extends JupiterRelativeLayout{

    public MsgCardDetailToolbarPanelsPageWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarPanelsPageWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarPanelsPageWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_panels_page;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }
}
