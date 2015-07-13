package com.yun9.wservice.view.product;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/10/15.
 */
public class ProductClassifyPopLayout extends JupiterRelativeLayout{

    private ImageView producImage;

    private TextView productPriceTv;

    private TextView classifyNameTv;

    private ImageView closeIv;

    private LinearLayout confirmLl;

    private ListView classifyLv;

    public ProductClassifyPopLayout(Context context) {
        super(context);
    }

    public ProductClassifyPopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductClassifyPopLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.popup_product_classify;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        producImage = (ImageView) this.findViewById(R.id.product_image_iv);
        productPriceTv = (TextView) this.findViewById(R.id.product_price_tv);
        classifyNameTv = (TextView) this.findViewById(R.id.classify_name_tv);
        classifyLv = (ListView) this.findViewById(R.id.classify_lv);
        closeIv = (ImageView) this.findViewById(R.id.close_iv);
        confirmLl = (LinearLayout) this.findViewById(R.id.confirm_ll);
    }

    public ImageView getProducImage() {
        return producImage;
    }

    public TextView getProductPriceTv() {
        return productPriceTv;
    }

    public TextView getClassifyNameTv() {
        return classifyNameTv;
    }

    public ImageView getCloseIv() {
        return closeIv;
    }

    public LinearLayout getConfirmLl() {
        return confirmLl;
    }

    public ListView getClassifyLv() {
        return classifyLv;
    }
}
