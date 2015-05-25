package com.yun9.wservice.func.store;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StoreFragment extends JupiterFragment {

    @ViewInject(id=R.id.category)
    private LinearLayout categoryLayout;

    public static StoreFragment newInstance(Bundle args) {
        StoreFragment fragment = new
                StoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initViews(View view) {

        //设置分类信息
        List<ProductCategory> productCategories = this.builderProductCategory();



    }

    private List<ProductCategory> builderProductCategory(){
        List<ProductCategory> productCategories = new ArrayList<ProductCategory>();

        productCategories.add(new ProductCategory("精选"));
        productCategories.add(new ProductCategory("财务"));
        productCategories.add(new ProductCategory("行政"));
        productCategories.add(new ProductCategory("理财"));

        return productCategories;
    }
}
