package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrderCartInfo;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderProductWidget extends JupiterRelativeLayout{

    private ImageView productImageIV;

    private TextView productNameTV;

    private TextView productDescTV;

    private TextView productTipTV;

    private TextView productFeeTV;

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
        productDescTV = (TextView) this.findViewById(R.id.product_desc_tv);
        productTipTV = (TextView) this.findViewById(R.id.product_tip_tv);
        productFeeTV = (TextView) this.findViewById(R.id.product_fee_tv);
    }

    public void buildWithData(OrderCartInfo.OrderCartProduct orderCartProduct) {
        productFeeTV.setText(orderCartProduct.getSaleprice() + "元");
        productDescTV.setText(orderCartProduct.getIntroduce());
        ImageLoaderUtil.getInstance(this.getContext()).displayImage(orderCartProduct.getImgid(), productImageIV);
        productNameTV.setText(orderCartProduct.getName());
        if (orderCartProduct.getPhases() != null && orderCartProduct.getPhases().size() > 0){
            StringBuffer tip = new StringBuffer();
            OrderCartInfo.ProductPhase productPhase;
            for (int i = 0;i < orderCartProduct.getPhases().size();i++){
                productPhase = orderCartProduct.getPhases().get(i);
                if (i != 0){
                    tip.append("\n").append(productPhase.getPhasedescr());
                } else {
                    tip.append(productPhase.getPhasedescr());
                }
            }
            productTipTV.setText(tip);
        } else {
            productTipTV.setVisibility(GONE);
        }
    }
}
