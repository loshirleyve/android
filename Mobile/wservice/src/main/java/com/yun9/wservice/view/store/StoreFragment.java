package com.yun9.wservice.view.store;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;
import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.location.LocationBean;
import com.yun9.jupiter.location.LocationFactory;
import com.yun9.jupiter.location.OnLocationListener;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.repository.Page;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.jupiter.widget.paging.listview.PagingListView;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;
import com.yun9.wservice.model.ProductGroup;
import com.yun9.wservice.model.ServiceCity;
import com.yun9.wservice.view.login.LoginCommand;
import com.yun9.wservice.view.login.LoginMainActivity;
import com.yun9.wservice.view.product.ProductActivity;
import com.yun9.wservice.view.product.ProductCommand;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by xia on 2015/5/27.
 */
public class StoreFragment extends JupiterFragment {

    public static final String CURR_CITY = "curr_city";

    @ViewInject(id = R.id.store_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrFrame;

    @ViewInject(id = R.id.category_segmented)
    private SegmentedGroup segmentedGroup;

    @ViewInject(id = R.id.product_lv)
    private PagingListView productLV;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private SelectCityLayout selectCityLayout;

    private PopupWindow selectCityPopupW;

    private boolean switchAlertDialogShowing = false;

    private List<ServiceCity> serviceCities = new ArrayList<>();

    private ServiceCity defaultServiceCity;

    private ServiceCity currServiceCity;

    private ProductGroup currProductGroup;

    private List<ServiceCity> currServiceCities = new ArrayList<>();

    private List<ServiceCity> currDistricts = new ArrayList<>();

    private ViewPager viewPager;
    private View pageView;
    private CirclePageIndicator circlePageIndicator;

    private LinkedList<Product> products = new LinkedList<>();

    private LinkedList<Product> topProducts = new LinkedList<>();

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
        pageView = LayoutInflater.from(mContext).inflate(R.layout.widget_store_product_pager, null);
        viewPager = (ViewPager) pageView.findViewById(R.id.productsImgScroll);
        circlePageIndicator = (CirclePageIndicator) pageView.findViewById(R.id.indicator);
        viewPager.setAdapter(topProductViewPageAdapter);
        circlePageIndicator.setViewPager(viewPager);
        //将pageView加入到list header
        productLV.addHeaderView(pageView);

        titleBar.getTitleLeft().setOnClickListener(onLocationClickListener);

        //检查是否已经登录了
        if (sessionManager.isLogin()) {
            titleBar.getTitleRight().setVisibility(View.GONE);
        } else {
            titleBar.getTitleRight().setVisibility(View.VISIBLE);
            titleBar.getTitleRight().setOnClickListener(onLoginClickListener);
        }

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

                if (!AssertValue.isNotNullAndNotEmpty(serviceCities)) {
                    refresh();
                    mPtrFrame.refreshComplete();
                } else {
                    if (AssertValue.isNotNullAndNotEmpty(products)) {
                        refreshProduct(currProductGroup, products.get(0).getId(), Page.PAGE_DIR_PULL);
                    } else {
                        refreshProduct(currProductGroup, null, Page.PAGE_DIR_PULL);
                    }
                }
            }
        });

        productLV.setAdapter(productListViewAdapter);
        productLV.setOnItemClickListener(onProductItemClickListener);
        productLV.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (AssertValue.isNotNullAndNotEmpty(products)) {
                    Product product = products.get(products.size() - 1);
                    refreshProduct(currProductGroup, product.getId(), Page.PAGE_DIR_PUSH);
                } else {
                    productLV.onFinishLoading(true);
                }
            }
        });

        //读取缓存的当前城市
        currServiceCity = AppCache.getInstance().get(CURR_CITY, ServiceCity.class);

        //初始化城市选择窗口
        initPopWSelectCity();

        this.refresh();

        //自动滚动
        autoScrollHandler.sendEmptyMessageDelayed(1, 5000);
    }

    private void initPopWSelectCity() {
        selectCityLayout = new SelectCityLayout(mContext);
        selectCityLayout.getCityGV().setAdapter(selectCityGVadapter);
        selectCityLayout.getDistrictGV().setAdapter(selectDistrictGVadapter);
        selectCityPopupW = new PopupWindow(selectCityLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        selectCityPopupW.setOnDismissListener(onDismissListener);
        selectCityPopupW.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        selectCityPopupW.setOutsideTouchable(true);
        selectCityPopupW.setFocusable(true);
        int maxHeight = PublicHelp.getDeviceHeightPixels(getActivity());
        selectCityPopupW.setHeight(maxHeight / 2);
        selectCityPopupW.setAnimationStyle(R.style.top2bottom_bottom2top);
    }

    private void addCategory(ProductGroup productGroup) {
        RadioButton radioButton = (RadioButton) getActivity().getLayoutInflater().inflate(R.layout.radio_button_item, null);
        radioButton.setText(productGroup.getName());
        radioButton.setTag(productGroup);
        radioButton.setOnClickListener(onCategoryClickListener);
        segmentedGroup.addView(radioButton);
        segmentedGroup.updateBackground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.setOnLocationListener(onLocationListener);
        locationFactory.start();
    }

    private void reset() {
        products.clear();
        topProducts.clear();
        topProductViewPageAdapter.notifyDataSetChanged();
        productListViewAdapter.notifyDataSetChanged();
        circlePageIndicator.notifyDataSetChanged();
        productLV.setHasMoreItems(true);

    }

    private void cleanCategory() {
        segmentedGroup.removeAllViews();
    }

    private void refresh() {
        this.refreshCity();
    }

    private void refreshCity() {
        final Resource resource = resourceFactory.create("QueryCities");
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, mContext.getResources().getString(R.string.app_wating), true);

        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<ServiceCity> tempServiceCities = (List<ServiceCity>) response.getPayload();
                serviceCities.clear();
                currServiceCities.clear();
                if (AssertValue.isNotNullAndNotEmpty(tempServiceCities)) {
                    //检查当前缓存的城市是否在支持城市范围内
                    boolean supportCity = false;

                    for (ServiceCity serviceCity : tempServiceCities) {
                        if (serviceCity.getIsdefault() == 1)
                            defaultServiceCity = serviceCity;

                        serviceCities.add(serviceCity);

                        if (AssertValue.isNotNull(currServiceCity) && serviceCity.getId().equals(currServiceCity.getId())) {
                            supportCity = true;
                        }

                        boolean exits = false;
                        for (ServiceCity tempSC : currServiceCities) {
                            if (tempSC.getProvince().equals(serviceCity.getProvince()) && tempSC.getCity().equals(serviceCity.getCity())) {
                                exits = true;
                                break;
                            }
                        }

                        if (!exits) {
                            currServiceCities.add(serviceCity);
                        }

                    }

                    //如果当前城市为空，且指定了默认城市则检索分类
                    if (AssertValue.isNotNull(defaultServiceCity) && !AssertValue.isNotNull(currServiceCity)) {
                        switchLocation(defaultServiceCity);
                    }

                    if (supportCity && AssertValue.isNotNull(currServiceCity)) {
                        switchLocation(currServiceCity);
                    }

                    //激活地理位置请求
                    LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                    locationFactory.requestLocation();

                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(getActivity(), response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                progressDialog.dismiss();
            }
        });
    }

    private void refreshProductTop(final LinkedList<Product> topProducts){
        final Resource resource = resourceFactory.create("QueryProducts");
        resource.param("top", 1).param("groupid", "1");
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Product product = (Product) response.getPayload();
                topProducts.addLast(product);
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }
    private void refreshProduct(ProductGroup productGroup, String rowid, final String dir) {
        if (!AssertValue.isNotNull(productGroup)) {
            mPtrFrame.refreshComplete();
            return;
        }

        Resource resource = resourceFactory.create("QueryProducts");
        resource.param("groupid", productGroup.getId());
        resource.page().setRowid(rowid).setDir(dir);

        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<Product> tempProducts = (List<Product>) response.getPayload();

                if (AssertValue.isNotNullAndNotEmpty(tempProducts) && Page.PAGE_DIR_PULL.equals(dir)) {
                    for (int i = tempProducts.size(); i > 0; i--) {
                        Product tempProduct = tempProducts.get(i - 1);
                        products.addFirst(tempProduct);
                        if (tempProduct.istop()) {
                            topProducts.addFirst(tempProduct);
                        }
                    }
                }

                if (AssertValue.isNotNullAndNotEmpty(tempProducts) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    for (Product product : tempProducts) {
                        products.addLast(product);
                        /*if (product.istop()) {
                            topProducts.addLast(product);
                        }*/
                    }
                }

                if (!AssertValue.isNotNullAndNotEmpty(tempProducts) && Page.PAGE_DIR_PUSH.equals(dir)) {
                    Toast.makeText(mContext, R.string.app_no_more_data, Toast.LENGTH_SHORT).show();
                    productLV.onFinishLoading(false);
                }

                productListViewAdapter.notifyDataSetChanged();
                topProductViewPageAdapter.notifyDataSetChanged();
                circlePageIndicator.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(getActivity(), response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                mPtrFrame.refreshComplete();
                productLV.onFinishLoading(true);
                refreshProductTop(topProducts);
            }
        });
    }


    private void switchLocation(ServiceCity serviceCity) {
        if (!AssertValue.isNotNull(serviceCity))
            return;

        reset();

        currServiceCity = serviceCity;
        AppCache.getInstance().put(CURR_CITY, currServiceCity);

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, mContext.getResources().getString(R.string.app_wating), true);

        //设置界面当前城市
        titleBar.getTitleLeftTv().setText(currServiceCity.getCity());

        Resource resource = resourceFactory.create("QueryMdProductGroupBylocation");
        resource.param("province", serviceCity.getProvince());
        resource.param("city", serviceCity.getCity());
        resource.param("district", serviceCity.getDistrict());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<ProductGroup> productCategories = (List<ProductGroup>) response.getPayload();
                cleanCategory();
                if (AssertValue.isNotNullAndNotEmpty(productCategories)) {
                    for (ProductGroup productGroup : productCategories) {
                        addCategory(productGroup);
                    }

                    //触发第一个分类点击
                    if (segmentedGroup.getChildCount() > 0) {
                        segmentedGroup.getChildAt(0).performClick();
                    }
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(getActivity(), response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                progressDialog.dismiss();
            }
        });

    }

    private void builderCurrDistricts(ServiceCity serviceCity) {
        if (AssertValue.isNotNull(serviceCity) && AssertValue.isNotNullAndNotEmpty(serviceCities)) {
            currDistricts.clear();

            for (ServiceCity tempSC : serviceCities) {
                if (tempSC.getProvince().equals(serviceCity.getProvince()) && tempSC.getCity().equals(serviceCity.getCity())) {
                    ServiceCity district = new ServiceCity();
                    district.setId(tempSC.getId());
                    if (tempSC.getId().equals(currServiceCity.getId())) {
                        district.setSelected(true);
                    }
                    district.setCity(tempSC.getCity());
                    district.setProvince(tempSC.getProvince());
                    district.setDistrict(tempSC.getDistrict());
                    district.setCityno(tempSC.getCityno());
                    district.setIsdefault(tempSC.getIsdefault());
                    district.setSortno(tempSC.getSortno());
                    currDistricts.add(district);
                }
            }
        }

    }

    private ServiceCity findCity(String province, String city, String district) {
        ServiceCity serviceCity = null;

        //查找是否有对于当前省、市、区支持的城市
        if (AssertValue.isNotNullAndNotEmpty(province) && AssertValue.isNotNullAndNotEmpty(city) && AssertValue.isNotNullAndNotEmpty(district) && AssertValue.isNotNullAndNotEmpty(serviceCities)) {

            for (ServiceCity tempSC : serviceCities) {
                if (city.equals(tempSC.getCity()) && province.equals(tempSC.getProvince()) && district.equals(tempSC.getDistrict())) {
                    serviceCity = tempSC;
                    break;
                }
            }
        }

        //如果没有找到省市区匹配的则查找是否有对于当前省、市支持的城市
        if (!AssertValue.isNotNull(serviceCity) && AssertValue.isNotNullAndNotEmpty(province) && AssertValue.isNotNullAndNotEmpty(city) && AssertValue.isNotNullAndNotEmpty(serviceCities)) {

            for (ServiceCity tempSC : serviceCities) {
                if (tempSC.getCity().equals(city) && tempSC.getProvince().equals(province)) {
                    serviceCity = tempSC;
                    break;
                }
            }
        }
        return serviceCity;
    }

    private View.OnClickListener onCategoryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProductGroup productGroup = (ProductGroup) v.getTag();

            if (AssertValue.isNotNull(productGroup)) {
                reset();
                currProductGroup = productGroup;
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPtrFrame.autoRefresh();
                    }
                }, 100);

            }
        }
    };

    private View.OnClickListener onLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(currServiceCity)) {
                builderCurrDistricts(currServiceCity);
            }

            //将当前选中的城市区域标记
            if (AssertValue.isNotNullAndNotEmpty(currServiceCities) && AssertValue.isNotNull(currServiceCity)) {
                for (ServiceCity serviceCity : currServiceCities) {
                    if (serviceCity.getProvince().equals(currServiceCity.getProvince()) && serviceCity.getCity().equals(currServiceCity.getCity())) {
                        serviceCity.setSelected(true);
                    } else {
                        serviceCity.setSelected(false);
                    }
                }
            }

            if (AssertValue.isNotNullAndNotEmpty(currDistricts) && AssertValue.isNotNull(currServiceCity)) {
                for (ServiceCity serviceCity : currDistricts) {
                    if (serviceCity.getProvince().equals(currServiceCity.getProvince()) && serviceCity.getCity().equals(currServiceCity.getCity()) && serviceCity.getDistrict().equals(currServiceCity.getDistrict())) {
                        serviceCity.setSelected(true);
                    } else {
                        serviceCity.setSelected(false);
                    }
                }
            }

            //设置弹出选择城市界面数据
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 0.4f;
            getActivity().getWindow().setAttributes(lp);
            selectCityGVadapter.notifyDataSetChanged();
            selectDistrictGVadapter.notifyDataSetChanged();
            selectCityPopupW.showAsDropDown(titleBar);
        }
    };

    private View.OnClickListener onLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginMainActivity.start(getActivity(), new LoginCommand());
        }
    };

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 1f;
            getActivity().getWindow().setAttributes(lp);
        }
    };

    private OnLocationListener onLocationListener = new OnLocationListener() {
        @Override
        public void onReceiveLocation(LocationBean locationBean) {
            //检查是否被支持的城市
            final ServiceCity serviceCity = findCity(locationBean.getProvince(), locationBean.getCity(), locationBean.getDistrict());
            final String key = "com.yun9.wservice.store.switchcity.notice";
            boolean noticeSwitchCity = AppCache.getInstance().getAsBoolean(key);
            //当前定位城市是被支持的
            if (AssertValue.isNotNull(serviceCity) && !noticeSwitchCity && !switchAlertDialogShowing) {
                //当前城市还没有确定或者当前城市与定位城市不一致
                if (!AssertValue.isNotNull(currServiceCity) || (AssertValue.isNotNull(currServiceCity) && !currServiceCity.getId().equals(serviceCity.getId()) && !"all".equals(currServiceCity.getCityno()))) {
                    CharSequence content = getResources()
                            .getString(R.string.store_change_location_dialog_content, serviceCity.getCity(), serviceCity.getCity());
                    switchAlertDialogShowing = true;
                    new AlertDialog.Builder(getActivity()).setTitle(R.string.store_change_location_dialog_title).setMessage(content).setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switchLocation(serviceCity);
                            switchAlertDialogShowing = false;
                        }
                    }).setNegativeButton(R.string.app_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switchAlertDialogShowing = false;
                            //用户点击取消后，7天内不再提示
                            AppCache.getInstance().put(key, true, 60 * 60 * 24 * 7);
                        }
                    }).show();
                }
            }

        }
    };

    private View.OnClickListener onSelectCityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ServiceCity serviceCity = (ServiceCity) v.getTag();
            if (AssertValue.isNotNull(serviceCity)) {

                for (ServiceCity tempSc : currServiceCities) {
                    tempSc.setSelected(false);
                    if (tempSc.getId().equals(serviceCity.getId())) {
                        tempSc.setSelected(true);
                    }
                }
                //设置当前显示的区域
                builderCurrDistricts(serviceCity);

                selectCityGVadapter.notifyDataSetChanged();
                selectDistrictGVadapter.notifyDataSetChanged();
            }
        }
    };

    private View.OnClickListener onSelectDistrictClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ServiceCity serviceCity = (ServiceCity) v.getTag();
            if (AssertValue.isNotNull(serviceCity)) {
                switchLocation(serviceCity);
            }
            selectCityPopupW.dismiss();
        }
    };

    private AdapterView.OnItemClickListener onProductItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Product product = (Product) view.getTag();

            if (AssertValue.isNotNull(product)) {
                ProductActivity.start(getActivity(), new ProductCommand().setProductid(product.getId()));
            }
        }
    };

    private JupiterAdapter productListViewAdapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductItemLayout productItemLayout = null;
            Product product = products.get(position);

            if (AssertValue.isNotNull(convertView)) {
                productItemLayout = (ProductItemLayout) convertView;
            } else {
                productItemLayout = new ProductItemLayout(getActivity());
            }

            productItemLayout.getTitleTV().setText(product.getName());
            productItemLayout.getSutitleTV().setText(product.getIntroduce());
            ImageLoaderUtil.getInstance(getActivity()).displayImage(product.getImageid(), productItemLayout.getMainIV());
            productItemLayout.getHotnoticeTV().setText(product.getPricedescr());
            productItemLayout.setTag(product);

            return productItemLayout;
        }
    };

    private JupiterAdapter selectCityGVadapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return currServiceCities.size();
        }

        @Override
        public Object getItem(int position) {
            return currServiceCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SelectCityItemLayout selectCityItemLayout = null;
            ServiceCity serviceCity = currServiceCities.get(position);

            if (AssertValue.isNotNull(convertView)) {
                selectCityItemLayout = (SelectCityItemLayout) convertView;
            } else {
                selectCityItemLayout = new SelectCityItemLayout(mContext);
            }

            selectCityItemLayout.setTag(serviceCity);
            selectCityItemLayout.getCityTV().setText(serviceCity.getCity());
            selectCityItemLayout.getItemLL().setOnClickListener(onSelectCityClickListener);
            selectCityItemLayout.getItemLL().setTag(serviceCity);
            selectCityItemLayout.selected(serviceCity.isSelected());

            return selectCityItemLayout;
        }
    };

    private JupiterAdapter selectDistrictGVadapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return currDistricts.size();
        }

        @Override
        public Object getItem(int position) {
            return currDistricts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SelectCityItemLayout selectCityItemLayout = null;
            ServiceCity serviceCity = currDistricts.get(position);

            if (AssertValue.isNotNull(convertView)) {
                selectCityItemLayout = (SelectCityItemLayout) convertView;
            } else {
                selectCityItemLayout = new SelectCityItemLayout(mContext);
            }

            selectCityItemLayout.setTag(serviceCity);
            selectCityItemLayout.getCityTV().setText(serviceCity.getDistrict());
            selectCityItemLayout.getItemLL().setOnClickListener(onSelectDistrictClickListener);
            selectCityItemLayout.getItemLL().setTag(serviceCity);
            selectCityItemLayout.selected(serviceCity.isSelected());

            return selectCityItemLayout;
        }
    };


    private PagerAdapter topProductViewPageAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return topProducts.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ViewPager viewPager = (ViewPager) container;
            viewPager.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Product product = topProducts.get(position);
            ProductScrollItemView productScrollItemView = new ProductScrollItemView(mContext);
            productScrollItemView.setTag(product);
            productScrollItemView.getProductTV().setText(product.getName());
            productScrollItemView.getProductDescTV().setText(product.getIntroduce());

            CacheInst cacheInst = InstCache.getInstance().getInst(product.getInstid());

            if (AssertValue.isNotNull(cacheInst)) {
                productScrollItemView.getInstTV().setText(cacheInst.getInstname());
            }


            container.addView(productScrollItemView);
            return productScrollItemView;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    };

    private Handler autoScrollHandler = new Handler() {
        private static final int MSG_CHANGE_PRODUCT = 1;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == MSG_CHANGE_PRODUCT) {
                int i = viewPager.getCurrentItem() + 1;
                if (i >= viewPager.getAdapter().getCount()) {
                    i = 0;
                }
                viewPager.setCurrentItem(i, true);

            }

            autoScrollHandler.sendEmptyMessageDelayed(MSG_CHANGE_PRODUCT, 5000);
        }
    };
}

