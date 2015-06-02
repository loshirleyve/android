package com.yun9.wservice.func.store;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.pulltorefresh.PullToRefreshListView;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;
import com.yun9.wservice.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xia on 2015/5/27.
 */
public class StoreFragment extends JupiterFragment {

    private PullToRefreshListView pullToRefreshListView;
    private ProductCategoryLayout productCategoryLayout;
    private ProductListAdapter productListAdapter;

    @ViewInject(id = R.id.category_lll)
    private LinearLayout linearLayout;

    List<ProductCategory> productCategoryList;

    public static StoreFragment newInstance( Bundle args ) {
        StoreFragment fragment = new StoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initViews(View view) {
        productCategoryList = this.builderData();
        productCategoryLayout = (ProductCategoryLayout) view.findViewById(R.id.category_ll);
        productCategoryLayout.buildWidthData(productCategoryList);

        pullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.product_list_ptr);
/*        cast parameter of pullToRefreshListView to productCategoryLayout to
        let productCategoryLayout can dynamic alter the layout of pullToRefreshListView*/
        productCategoryLayout.setPullToRefreshListView(pullToRefreshListView);
        productListAdapter = new ProductListAdapter(view.getContext(),getProducts());
        pullToRefreshListView.setAdapter(productListAdapter);
        productListAdapter.notifyDataSetChanged();
        // 从数据库读取数据，调用productCategoryLayout的buildData
        // 以及为pullToRefreshListView设置Adapter
        //pullToRefreshListView.setAdapter(productListAdapter);
    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            Product product = new Product();
            product.setProductid(""+i);
            product.setProductImg("产品图片" + i);
            products.add(product);
        }
        return products;

    }

    private List<ProductCategory> builderData(){
        productCategoryList = new ArrayList<ProductCategory>();
        for (int i = 0; i < 4; i++){
            ProductCategory productCategory = new ProductCategory("分类" + i);
            productCategoryList.add(productCategory);
        }
        return productCategoryList;
    }
}
