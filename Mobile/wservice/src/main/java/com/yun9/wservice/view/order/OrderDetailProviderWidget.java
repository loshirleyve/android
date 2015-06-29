package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailProviderWidget extends JupiterRelativeLayout{

    private OrderProviderWidget providerWidget;

    public OrderDetailProviderWidget(Context context) {
        super(context);
    }

    public OrderDetailProviderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailProviderWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWitdhData(Order order) {
        providerWidget.buildWithData(order);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_provider;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        providerWidget = (OrderProviderWidget) this.findViewById(R.id.provider_widget);
    }
}
