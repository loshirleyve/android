package com.yun9.wservice.func.store;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.wservice.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xia on 2015/6/2.
 */
public class ProductImgAdapter extends PagerAdapter{
    private Context context;

    private List<ProductScrollItemView> mViews;

    public ProductImgAdapter(Context context,List<ProductScrollItemView> views){
        this.context = context;
        this.mViews = views;
    }
    @Override
    public int getCount() {
        if(AssertValue.isNotNullAndNotEmpty(mViews)) {
            return mViews.size();
        }else
            return 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView(mViews.get(position));
        ViewPager viewPager = (ViewPager) container;
        viewPager.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*Product product = products.get(position);
        ProductScrollItemView productScrollItemView = new ProductScrollItemView(context);
        productScrollItemView.buildWithData(product);
        container.addView(productScrollItemView);
        productScrollItemViews.add(productScrollItemView);

        return productScrollItemViews.get(position);*/
        container.addView(mViews.get(position));
        return mViews.get(position);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
