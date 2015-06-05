package com.yun9.wservice.func.store;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by xia on 2015/5/27.
 */
public class ProductScrollListView extends JupiterRelativeLayout{

    private ViewPager viewPager;

    public ProductScrollListView(Context context) {
        super(context);
    }

    public ProductScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_store_products_imgscroll;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
       viewPager = (ViewPager)this.findViewById(R.id.productsImgScroll);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
