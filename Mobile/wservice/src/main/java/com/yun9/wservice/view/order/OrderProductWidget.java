package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrderCartInfo;
import com.yun9.wservice.model.ProductProfile;
import com.yun9.wservice.view.product.ProductProfileItemWidget;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderProductWidget extends JupiterRelativeLayout {

    private ImageView productImageIV;

    private TextView productNameTV;

    private TextView productClassifyTV;

    private TextView productDescTV;

    private TextView productFeeTV;

    private LinearLayout classifyLl;

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
        productImageIV = (ImageView) this.findViewById(R.id.product_image_iv);
        productNameTV = (TextView) this.findViewById(R.id.product_name_tv);
        productClassifyTV = (TextView) this.findViewById(R.id.product_classify_tv);
        productDescTV = (TextView) this.findViewById(R.id.product_desc_tv);
        productFeeTV = (TextView) this.findViewById(R.id.product_fee_tv);
        classifyLl = (LinearLayout) this.findViewById(R.id.classify_ll);
    }

    public void buildWithData(OrderCartInfo orderCartProduct) {
        productFeeTV.setText(orderCartProduct.getGoodsamount() + "元");
        if (AssertValue.isNotNullAndNotEmpty(orderCartProduct.getClassifyname())) {
            productClassifyTV.setText(orderCartProduct.getClassifyname());
        } else {
            classifyLl.setVisibility(GONE);
        }
        ImageLoaderUtil.getInstance(this.getContext()).displayImage(orderCartProduct.getImgid(), productImageIV);
        productNameTV.setText(orderCartProduct.getProductname());
        productDescTV.setText(orderCartProduct.getIntroduce());

    }
}
