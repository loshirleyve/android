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
import com.yun9.mobile.annotation.ViewInject;
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

    private TextView productDescTV;

    private TextView productTipTV;

    private ListView productContentLV;

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
        productDescTV = (TextView) this.findViewById(R.id.product_desc_tv);
        productTipTV = (TextView) this.findViewById(R.id.product_tip_tv);
        productContentLV = (ListView) this.findViewById(R.id.product_detail_content_lv);
        productFeeTV = (TextView) this.findViewById(R.id.product_fee_tv);
        classifyLl = (LinearLayout) this.findViewById(R.id.classify_ll);
    }

    public void buildWithData(OrderCartInfo.OrderProduct orderCartProduct) {
        productFeeTV.setText(orderCartProduct.getGoodsamount() + "元");
        if (AssertValue.isNotNullAndNotEmpty(orderCartProduct.getProductclassifyname())) {
            productDescTV.setText(orderCartProduct.getProductclassifyname());
        } else {
            classifyLl.setVisibility(GONE);
        }
        ImageLoaderUtil.getInstance(this.getContext()).displayImage(orderCartProduct.getProductimgid(), productImageIV);
        productNameTV.setText(orderCartProduct.getProductname());
//        if (orderCartProduct.getProductPhases() != null && orderCartProduct.getProductPhases().size() > 0){
//            StringBuffer tip = new StringBuffer();
//            OrderCartInfo.ProductPhases productPhase;
//            for (int i = 0;i < orderCartProduct.getProductPhases().size();i++){
//                productPhase = orderCartProduct.getProductPhases().get(i);
//                if (i != 0){
//                    tip.append("\n").append(productPhase.getName()).append(": ").append(productPhase.getPhasedescr());
//                } else {
//                    tip.append(productPhase.getName()).append(": ").append(productPhase.getPhasedescr());
//                }
//            }
//            productTipTV.setText(tip);
//        } else {
//            productTipTV.setVisibility(GONE);
//        }


        productContentLV.setAdapter(new ProductContentLVAdapter(orderCartProduct.getProductProfiles()));

    }

    public class ProductContentLVAdapter extends JupiterAdapter {

        private List<ProductProfile> productProfiles;

        public ProductContentLVAdapter(List<ProductProfile> productProfiles) {
            this.productProfiles = productProfiles;
        }

        @Override
        public int getCount() {
            if (AssertValue.isNotNullAndNotEmpty(productProfiles) && productProfiles.size() > 0) {
                return productProfiles.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return productProfiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductProfileItemWidget productProfileItemWidget = null;
            ProductProfile productProfile = productProfiles.get(position);

            if (AssertValue.isNotNull(convertView)) {
                productProfileItemWidget = (ProductProfileItemWidget) convertView;
            } else {
                productProfileItemWidget = new ProductProfileItemWidget(mContext);
            }

            productProfileItemWidget.setTag(productProfile);
            productProfileItemWidget.getProfileTV().setText(productProfile.getSynopsis());

            return productProfileItemWidget;
        }
    }

    ;
}
