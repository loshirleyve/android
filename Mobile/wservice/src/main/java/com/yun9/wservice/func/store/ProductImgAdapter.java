package com.yun9.wservice.func.store;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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
    private List<Product> products;

    private List<ProductScrollItemView> productScrollItemViews;

    public ProductImgAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
        productScrollItemViews = new ArrayList<>();
    }
    @Override
    public int getCount() {
        if(AssertValue.isNotNullAndNotEmpty(products)) {
            return products.size();
        }else
            return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(productScrollItemViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Product product = products.get(position);
        ProductScrollItemView productScrollItemView = new ProductScrollItemView(context);
        productScrollItemView.buildWithData(product);
        container.addView(productScrollItemView);
        productScrollItemViews.add(productScrollItemView);

        return productScrollItemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
