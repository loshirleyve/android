package com.yun9.wservice.view.store;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;

/**
 * Created by xia on 2015/5/27.
 */
public class ProductMainItemWidget extends JupiterRelativeLayout {

    private JupiterRowStyleSutitleLayout jupiterRowStyleSutitleLayout;

    public ProductMainItemWidget(Context context) {
        super(context);
    }

    public ProductMainItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductMainItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected int getContextView() {
        return R.layout.widget_store_product_main_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        jupiterRowStyleSutitleLayout = (JupiterRowStyleSutitleLayout)this
                .findViewById(R.id.store_product_main_item);

    }

    public JupiterRowStyleSutitleLayout getJupiterRowStyleSutitleLayout() {
        return jupiterRowStyleSutitleLayout;
    }

}
