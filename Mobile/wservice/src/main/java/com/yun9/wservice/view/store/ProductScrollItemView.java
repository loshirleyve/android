package com.yun9.wservice.view.store;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;

import java.util.List;

/**
 * Created by xia on 2015/6/2.
 */
public class ProductScrollItemView extends JupiterRelativeLayout{
    private TextView instTV;
    private TextView productTV;
    private TextView productDescTV;

    public ProductScrollItemView(Context context) {
        super(context);
    }

    public ProductScrollItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductScrollItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected int getContextView() {
        return R.layout.widget_product_scroll_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

        instTV = (TextView)this.findViewById(R.id.inst_tv);
        productTV = (TextView)this.findViewById(R.id.product_tv);
        productDescTV = (TextView)this.findViewById(R.id.product_desc_tv);
    }

    public TextView getInstTV() {
        return instTV;
    }

    public TextView getProductTV() {
        return productTV;
    }

    public TextView getProductDescTV() {
        return productDescTV;
    }
}
