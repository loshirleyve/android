package com.yun9.wservice.view.product;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/26.
 */
public class ProductProfileItemWidget extends JupiterRelativeLayout {

    private TextView profileTV;

    public ProductProfileItemWidget(Context context) {
        super(context);
    }

    public ProductProfileItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductProfileItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_product_profile_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        profileTV = (TextView) findViewById(R.id.profile_tv);
    }

    public TextView getProfileTV() {
        return profileTV;
    }
}
