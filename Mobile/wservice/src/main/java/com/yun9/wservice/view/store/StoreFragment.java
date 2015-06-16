package com.yun9.wservice.view.store;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;
import com.yun9.wservice.model.ProductCategory;
import com.yun9.wservice.model.SampleUser;
import com.yun9.wservice.view.login.LoginMainActivity;

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


    @ViewInject(id = R.id.store_title)
    private JupiterTitleBarLayout titleBar;


    private ListView productListView;
    private ProductCategoryLayout productCategoryLayout;
    private ProductListAdapter productListAdapter;

    private ProductImgAdapter productImgAdapter;
    private List<ProductScrollItemView> productScrollItemViews;
    private ViewPager viewPager;
    private View pageView;
    private CirclePageIndicator circlePageIndicator;

    private LinkedList<Product> products;
    private List<ProductCategory> productCategoryList;
    private TextView pcTV;

    private ProductCategory currProductCategory;

    private PtrClassicFrameLayout mPtrFrame;

    public static StoreFragment newInstance(Bundle args) {
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
        this.titleBar.getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Logged()){
                    Intent gotoLogin = new Intent(mContext, LoginMainActivity.class);
                    startActivity(gotoLogin);
                }
            }
        });

        productCategoryLayout = (ProductCategoryLayout) view.findViewById(R.id.category_ll);
        productListView = (ListView) view.findViewById(R.id.product_list_ptr);
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_list_view_frame);

        pageView = LayoutInflater.from(mContext).inflate(R.layout.widget_store_product_pager,null);
        viewPager = (ViewPager) pageView.findViewById(R.id.productsImgScroll);
        circlePageIndicator = (CirclePageIndicator)pageView.findViewById(R.id.indicator);

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
        if (AssertValue.isNotNull(currProductCategory)) {
            new GetDataTask().execute();
        }
    }

    private void completeRefreshProduct() {
        if (!AssertValue.isNotNull(products)) {
            products = new LinkedList<>();
        }

        LinkedList<Product> topProducts = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setProductid("" + i);
            product.setProductImg(currProductCategory.getCategoryname() + ";产品图片" + i);
            products.addFirst(product);
            topProducts.addLast(product);

        }

        this.builderViewPages(topProducts);

        if (!AssertValue.isNotNull(productImgAdapter)) {
            productImgAdapter = new ProductImgAdapter(mContext, productScrollItemViews);
            //viewPager.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 200));
            viewPager.setAdapter(productImgAdapter);
            circlePageIndicator.setViewPager(viewPager);
        } else {
            productImgAdapter.notifyDataSetChanged();
        }

        if (!AssertValue.isNotNull(productListAdapter)) {
            productListView.addHeaderView(pageView, null, false);

            productListAdapter = new ProductListAdapter(this.getActivity(), products);
            productListView.setAdapter(productListAdapter);

        } else {
            productListAdapter.notifyDataSetChanged();
        }


        mPtrFrame.refreshComplete();

    }

    private void builderViewPages(LinkedList<Product> topProducts) {
        if (!AssertValue.isNotNull(productScrollItemViews)) {
            this.productScrollItemViews = new ArrayList<>();
        }

        if (AssertValue.isNotNull(topProducts)) {
            for (Product product : topProducts) {
                ProductScrollItemView view = new ProductScrollItemView(mContext);

                view.buildWithData(product);
                view.setTag(product);
                this.productScrollItemViews.add(view);
            }
        }
    }

    private void refreshProductCategory() {
        List<ProductCategory> productCategories = new ArrayList<>();

        ProductCategory productCategory1 = new ProductCategory("精选");
        ProductCategory productCategory2 = new ProductCategory("财务");
        ProductCategory productCategory3 = new ProductCategory("行政");
        ProductCategory productCategory4 = new ProductCategory("理财");

        productCategories.add(productCategory1);
        productCategories.add(productCategory2);
        productCategories.add(productCategory3);
        productCategories.add(productCategory4);

        this.onCategoryComplete(productCategories);

    }

    private void onCategoryComplete(List<ProductCategory> productCategories) {
        if (AssertValue.isNotNullAndNotEmpty(productCategories)) {
            this.productCategoryList = productCategories;
            productCategoryLayout.setOnClickListener(onCategoryClickListener);
            productCategoryLayout.buildWidthData(productCategoryList);
        }
    }

    private void cleanProduct() {
        if (AssertValue.isNotNullAndNotEmpty(products)) {
            products.clear();
        }

        if (AssertValue.isNotNullAndNotEmpty(productScrollItemViews)) {
            productScrollItemViews.clear();
            if (AssertValue.isNotNull(productImgAdapter)) {
                productImgAdapter.notifyDataSetChanged();
            }
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

    private View.OnClickListener onCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currProductCategory = (ProductCategory) v.getTag();

            List<TextView> textViewList;
            textViewList = productCategoryLayout.getTextViews();
            for (int i = 0; i < textViewList.size(); i++){
                if(textViewList.get(i).getTag() != currProductCategory){
                    textViewList.get(i).setBackgroundResource(R.drawable.productcategory_background);
                }else {
                    textViewList.get(i).setBackgroundResource(R.color.title_color);
                }
            }

            if (AssertValue.isNotNull(currProductCategory)) {
                cleanProduct();
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.autoRefresh();
                    }
                }, 100);
            }
        }
    };

    //
    //
    //
    public boolean Logged(){

        SampleUser sampleUser = new SampleUser();
        String no = sampleUser.getNo().toString();
        String name = sampleUser.getName().toString();
        String id = sampleUser.getId().toString();

        if(no.equals("") || name.equals("") || id.equals("")){
            return false;
        }else{
            return true;
        }
    }
    //
    //
    //


}

