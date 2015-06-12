package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderProductWidget extends JupiterRelativeLayout{

    public OrderProductWidget(Context context) {
        super(context);
    }

    public OrderProductWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderProductWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_product;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }
}
