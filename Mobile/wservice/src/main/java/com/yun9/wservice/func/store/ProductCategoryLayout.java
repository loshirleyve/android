package com.yun9.wservice.func.store;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.pulltorefresh.PullToRefreshListView;
import com.yun9.wservice.R;
import com.yun9.wservice.model.ProductCategory;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by xia on 2015/5/27.
 */
public class ProductCategoryLayout extends JupiterRelativeLayout {

    private PullToRefreshListView pullToRefreshListView;
    LinearLayout linearLayout;


    public ProductCategoryLayout(Context context) {
        super(context);
    }

    public ProductCategoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductCategoryLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWidthData(List<ProductCategory> categoryList) {
        for(int i = 0; i < categoryList.size(); i++)
        {
            TextView textView = new TextView(this.getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,MATCH_PARENT,1);
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            textView.setText(categoryList.get(i).getCategoryname());
            textView.setTextSize(20);
            textView.setPadding(10, 10, 10, 10);
            textView.setTextColor(getResources().getColor(R.color.whites));
            linearLayout.addView(textView);
        }

        //TextView textView = new TextView(getContext());
        //textView.setText("");

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_product_category;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
       linearLayout = (LinearLayout)this.findViewById(R.id.category_lll);
    }

    public PullToRefreshListView getPullToRefreshListView() {
        return pullToRefreshListView;
    }

    public void setPullToRefreshListView(PullToRefreshListView pullToRefreshListView) {
        this.pullToRefreshListView = pullToRefreshListView;
    }
}
