package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/15.
 */
public class OrderItemWidget extends JupiterRelativeLayout{

    private TextView productNameTV;
    private TextView productPriceTV;
    private TextView orderSnTV;
    private TextView orderTimeTV;
    private ImageView productImgeIV;
    private TextView orderStateTV;

    public OrderItemWidget(Context context) {
        super(context);
    }

    public OrderItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void builWithData(Order order) {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_list_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        productNameTV = (TextView) this.findViewById(R.id.product_name_tv);
        productPriceTV = (TextView) this.findViewById(R.id.product_price_tv);
        orderSnTV = (TextView) this.findViewById(R.id.order_sn_tv);
        orderTimeTV = (TextView) this.findViewById(R.id.order_time_tv);
        productImgeIV = (ImageView) this.findViewById(R.id.product_image_iv);
        orderStateTV = (TextView) this.findViewById(R.id.order_state_tv);
    }
}
