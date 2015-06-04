package com.yun9.wservice.func.store;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;
import com.yun9.wservice.model.ProductCategory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by xia on 2015/5/27.
 */
public class StoreFragment extends JupiterFragment {

    private ListView productListView;
    private ProductCategoryLayout productCategoryLayout;
    private ProductListAdapter productListAdapter;

    private ProductScrollListView productScrollListView;
    private ProductImgAdapter productImgAdapter;

    private LinkedList<Product> products;
    private List<ProductCategory> productCategoryList;
    private ProductCategory currProductCategory;

    private PtrClassicFrameLayout mPtrFrame;

    @ViewInject(id = R.id.category_lll)
    private LinearLayout linearLayout;


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
        productCategoryLayout = (ProductCategoryLayout) view.findViewById(R.id.category_ll);
        productListView = (ListView) view.findViewById(R.id.product_list_ptr);
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);
        productScrollListView = new ProductScrollListView(mContext);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshProduct();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        // the following are default settings
//        mPtrFrame.setResistance(1.7f);
//        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
//        mPtrFrame.setDurationToClose(200);
//        mPtrFrame.setDurationToCloseHeader(1000);
//        // default is false
//        mPtrFrame.setPullToRefresh(false);
//        // default is true
//        mPtrFrame.setKeepHeaderWhenRefresh(true);

        refreshProductCategory();
    }

    private void refreshProduct() {
        //TODO 执行服务器刷新
        if (AssertValue.isNotNull(currProductCategory)){
            new GetDataTask().execute();
        }
    }

    private void completeRefreshProduct(){
        if (!AssertValue.isNotNull(products)){
            products = new LinkedList<>();
        }

        for(int i = 0; i < 3; i++){
            Product product = new Product();
            product.setProductid("" + i);
            product.setProductImg(currProductCategory.getCategoryname() + ";产品图片" + i);
            products.addFirst(product);
        }

        if (!AssertValue.isNotNull(productListAdapter)){
            productListAdapter = new ProductListAdapter(this.getActivity(),products,productScrollListView);
            productListView.setAdapter(productListAdapter);
        }else{
            productListAdapter.notifyDataSetChanged();
        }

//        if (!AssertValue.isNotNull(productImgAdapter)){
//            productImgAdapter = new ProductImgAdapter(this.mContext,products);
//            productScrollListView.getViewPager().setAdapter(productImgAdapter);
//        }else{
//            productImgAdapter.notifyDataSetChanged();
//        }

        mPtrFrame.refreshComplete();

    }

    private void refreshProductCategory(){
        List<ProductCategory> productCategories = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            ProductCategory productCategory = new ProductCategory("分类" + i);
            productCategories.add(productCategory);
        }

        this.onCategoryComplete(productCategories);

    }

    private void onCategoryComplete(List<ProductCategory> productCategories){
        if (AssertValue.isNotNullAndNotEmpty(productCategories)){
            this.productCategoryList = productCategories;
            productCategoryLayout.setOnClickListener(onCategoryClickListener);
            productCategoryLayout.buildWidthData(productCategoryList);
        }
    }

    private void cleanProduct(){
        if (AssertValue.isNotNullAndNotEmpty(products)){
           products.removeAll(products);
        }

    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return new String[1];
        }

        @Override
        protected void onPostExecute(String[] result) {
            completeRefreshProduct();
            super.onPostExecute(result);
        }
    }

   private  View.OnClickListener onCategoryClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currProductCategory = (ProductCategory) v.getTag();
            if (AssertValue.isNotNull(currProductCategory)) {
                cleanProduct();
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.autoRefresh();
                    }
                },100);
            }
        }
    };
}
