package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/15.
 */
public class OrderDetailBaseWidget extends JupiterRelativeLayout{

    private TextView productNameTV;
    private TextView productPriceTV;
    private TextView orderSnTV;
    private ImageView productImgeIV;

    public OrderDetailBaseWidget(Context context) {
        super(context);
    }

    public OrderDetailBaseWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailBaseWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void builWithData(Order order) {
        Order.OrderProduct product = order.getProducts().get(0);
        productNameTV.setText(product.getProductname());
        orderSnTV.setText(order.getOrdersn());
        productPriceTV.setText(product.getGoodsamount() + "元");
        ImageLoaderUtil.getInstance(this.mContext).displayImage(product.getProductimgid(),productImgeIV);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_base;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        productNameTV = (TextView) this.findViewById(R.id.product_name_tv);
        productPriceTV = (TextView) this.findViewById(R.id.product_price_tv);
        orderSnTV = (TextView) this.findViewById(R.id.order_sn_tv);
        productImgeIV = (ImageView) this.findViewById(R.id.product_image_iv);
    }
}
