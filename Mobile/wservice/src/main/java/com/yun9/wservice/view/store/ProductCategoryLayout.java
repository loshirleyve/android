package com.yun9.wservice.view.store;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by xia on 2015/5/27.
 */
public class ProductCategoryLayout extends JupiterRelativeLayout {

    private LinearLayout linearLayout;

    private OnClickListener onClickListener;

    private List<TextView> textViews;

    public ProductCategoryLayout(Context context) {
        super(context);
    }

    public ProductCategoryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductCategoryLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public List<TextView> getTextViews() {
        return textViews;
    }

    public void buildWidthData(List<ProductCategory> categoryList) {

        textViews = new ArrayList<TextView>();
        if (AssertValue.isNotNullAndNotEmpty(categoryList)){
            for(int i = 0; i < categoryList.size(); i++)
            {
                TextView textView = new TextView(this.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,MATCH_PARENT,1);
                textView.setLayoutParams(lp);
                textView.setGravity(Gravity.CENTER);
                textView.setText(categoryList.get(i).getCategoryname());
                textView.setTextSize(16);
                textView.setPadding(10, 10, 10, 10);
                textView.setBackgroundResource(R.drawable.productcategory_background);

                textView.setTag(categoryList.get(i));

                textView.setTextColor(getResources().getColor(R.color.whites));
                if (AssertValue.isNotNull(onClickListener)){
                    textView.setOnClickListener(onClickListener);
                }
                textViews.add(textView);
                linearLayout.addView(textView);
            }
            //
            this.textViews.get(0).performClick();

        }

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_product_category;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
       linearLayout = (LinearLayout)this.findViewById(R.id.category_lll);
    }


    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
