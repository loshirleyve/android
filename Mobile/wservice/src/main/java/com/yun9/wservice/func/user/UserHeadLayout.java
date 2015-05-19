package com.yun9.wservice.func.user;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/22.
 */
public class UserHeadLayout extends JupiterRelativeLayout {
    public UserHeadLayout(Context context) {
        super(context);
    }

    public UserHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserHeadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.fragment_user_header;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }

}
