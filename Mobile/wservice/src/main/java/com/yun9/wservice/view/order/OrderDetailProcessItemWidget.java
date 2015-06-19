package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/19.
 */
public class OrderDetailProcessItemWidget extends JupiterRelativeLayout{

    private LinearLayout container;

    public OrderDetailProcessItemWidget(Context context) {
        super(context);
    }

    public OrderDetailProcessItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailProcessItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_process_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        container = (LinearLayout) this.findViewById(R.id.container);
    }

    public LinearLayout getContainer() {
        return container;
    }
}
