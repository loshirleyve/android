package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class TimeLineItemWidget extends JupiterRelativeLayout {

    private TextView lable;

    public TimeLineItemWidget(Context context) {
        super(context);
    }

    public TimeLineItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeLineItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_time_line_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        lable = (TextView) this.findViewById(R.id.lable);
    }

    public TextView getLable() {
        return lable;
    }
}
