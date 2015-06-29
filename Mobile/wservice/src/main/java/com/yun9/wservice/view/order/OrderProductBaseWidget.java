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
 * Created by huangbinglong on 15/6/17.
 */
public class OrderProductBaseWidget extends JupiterRelativeLayout{

    private ImageView productImageIV;
    private TextView productNameTV;
    private TextView productPriceTV;

    public OrderProductBaseWidget(Context context) {
        super(context);
    }

    public OrderProductBaseWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderProductBaseWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Order.OrderProduct product) {
        ImageLoaderUtil.getInstance(this.getContext()).displayImage(product.getProductimgid(),productImageIV);
        productNameTV.setText(product.getProductname());
        productPriceTV.setText(product.getGoodsamount()+"元");
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_product_base;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        productImageIV = (ImageView) this.findViewById(R.id.product_image_iv);
        productNameTV = (TextView) this.findViewById(R.id.product_name_tv);
        productPriceTV = (TextView) this.findViewById(R.id.product_price_tv);
    }
}
