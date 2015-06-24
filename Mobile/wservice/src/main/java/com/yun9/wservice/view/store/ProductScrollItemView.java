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
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

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

        textView1 = (TextView)this.findViewById(R.id.productImgItem1);
        textView2 = (TextView)this.findViewById(R.id.productImgItem2);
        textView3 = (TextView)this.findViewById(R.id.productImgItem3);
    }
}
