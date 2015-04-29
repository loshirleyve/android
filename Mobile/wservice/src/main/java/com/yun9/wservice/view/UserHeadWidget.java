package com.yun9.wservice.view;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/22.
 */
public class UserHeadWidget extends JupiterRelativeLayout {
    public UserHeadWidget(Context context) {
        super(context);
    }

    public UserHeadWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserHeadWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_user_header;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }

}
