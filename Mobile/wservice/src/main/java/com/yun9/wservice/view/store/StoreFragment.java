package com.yun9.wservice.view.store;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;
import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.location.LocationBean;
import com.yun9.jupiter.location.LocationFactory;
import com.yun9.jupiter.location.OnGetPoiInfoListener;
import com.yun9.jupiter.location.OnLocationListener;
import com.yun9.jupiter.location.PoiInfoBean;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Product;
import com.yun9.wservice.model.ProductCategory;
import com.yun9.wservice.model.ServiceCity;
import com.yun9.wservice.view.login.LoginCommand;
import com.yun9.wservice.view.login.LoginMainActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
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
    private ListView productLV;

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

    private ProductCategory currProductCategory;

    private ViewPager viewPager;
    private View pageView;
    private CirclePageIndicator circlePageIndicator;

    private LinkedList<Product> products = new LinkedList<>();

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
                if (AssertValue.isNotNullAndNotEmpty(products)) {
                    refreshProduct(currProductCategory, products.get(0).getId(), null);
                } else {
                    refreshProduct(currProductCategory, null, null);
                }
            }
        });

        productLV.setAdapter(productListViewAdapter);

        //读取缓存的当前城市
        currServiceCity = AppCache.getInstance().get(CURR_CITY, ServiceCity.class);

        //初始化城市选择窗口
        initPopWSelectCity();

        this.refresh();
    }

    private void initPopWSelectCity() {
        selectCityLayout = new SelectCityLayout(mContext);
        selectCityLayout.getCityGV().setAdapter(selectCityGVadapter);
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

    private void addCategory(ProductCategory productCategory) {
        RadioButton radioButton = (RadioButton) getActivity().getLayoutInflater().inflate(R.layout.radio_button_item, null);
        radioButton.setText(productCategory.getCategoryname());
        radioButton.setTag(productCategory);
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

    private void refreshProduct(ProductCategory productCategory, String lastupid, String lastdownid) {
        if (!AssertValue.isNotNull(productCategory)) {
            mPtrFrame.refreshComplete();
            return;
        }

        final Resource resource = resourceFactory.create("QueryProducts");
        if (AssertValue.isNotNullAndNotEmpty(lastupid)) {
            resource.pullUp(lastupid);
        }

        if (AssertValue.isNotNullAndNotEmpty(lastdownid) && !AssertValue.isNotNullAndNotEmpty(lastupid)) {
            resource.pullDown(lastdownid);
        }

        resource.param("categoryid", productCategory.getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<Product> tempProducts = (List<Product>) response.getPayload();

                if (AssertValue.isNotNullAndNotEmpty(tempProducts) && Resource.PULL_TYPE.UP.equals(resource.getPullType())) {
                    for (int i = tempProducts.size(); i > 0; i--) {
                        products.addFirst(tempProducts.get(i - 1));
                    }
                }

                if (AssertValue.isNotNullAndNotEmpty(tempProducts) && Resource.PULL_TYPE.DOWN.equals(resource.getPullType())) {
                    for (Product product : tempProducts) {
                        products.addLast(product);
                    }
                }

                if (!AssertValue.isNotNullAndNotEmpty(tempProducts) && Resource.PULL_TYPE.DOWN.equals(resource.getPullType())) {
//                    Toast.makeText(mContext, R.string.app_no_more_data, Toast.LENGTH_SHORT).show();
//                    fileLV.setHasMoreItems(false);
                }

                productListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(getActivity(), response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                mPtrFrame.refreshComplete();
            }
        });
    }


    private void switchLocation(ServiceCity serviceCity) {
        if (!AssertValue.isNotNull(serviceCity))
            return;

        currServiceCity = serviceCity;
        AppCache.getInstance().put(CURR_CITY, currServiceCity);

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, mContext.getResources().getString(R.string.app_wating), true);

        //设置界面当前城市
        titleBar.getTitleLeftTv().setText(currServiceCity.getCity());

        Resource resource = resourceFactory.create("QueryCategorysBylocation");
        resource.param("province", serviceCity.getProvince());
        resource.param("city", serviceCity.getCity());

        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<ProductCategory> productCategories = (List<ProductCategory>) response.getPayload();
                cleanCategory();
                if (AssertValue.isNotNullAndNotEmpty(productCategories)) {
                    for (ProductCategory productCategory : productCategories) {
                        addCategory(productCategory);
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

    private ServiceCity findCity(String province, String city) {
        ServiceCity serviceCity = null;

        if (AssertValue.isNotNullAndNotEmpty(province) && AssertValue.isNotNullAndNotEmpty(city) && AssertValue.isNotNullAndNotEmpty(serviceCities)) {

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
            ProductCategory productCategory = (ProductCategory) v.getTag();

            if (AssertValue.isNotNull(productCategory)) {
                products.clear();
                productListViewAdapter.notifyDataSetChanged();
                currProductCategory = productCategory;
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
            //设置弹出选择城市界面数据
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 0.4f;
            getActivity().getWindow().setAttributes(lp);
            selectCityGVadapter.notifyDataSetChanged();
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
            final ServiceCity serviceCity = findCity(locationBean.getProvince(), locationBean.getCity());
            final String key = "com.yun9.wservice.store.switchcity.notice";
            boolean noticeSwitchCity = AppCache.getInstance().getAsBoolean(key);
            //当前定位城市是被支持的
            if (AssertValue.isNotNull(serviceCity) && !noticeSwitchCity && !switchAlertDialogShowing) {
                //当前城市还没有确定或者当前城市与定位城市不一致
                if (!AssertValue.isNotNull(currServiceCity) || (AssertValue.isNotNull(currServiceCity) && !currServiceCity.getId().equals(serviceCity.getId()))) {
                    CharSequence content = getResources().getString(R.string.store_change_location_dialog_content, serviceCity.getCity(), serviceCity.getCity());
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
                switchLocation(serviceCity);
            }
            selectCityPopupW.dismiss();
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
            //TODO 待服务调整后修改
            productItemLayout.getHotnoticeTV().setText("每月100元");

            return productItemLayout;
        }
    };

    private JupiterAdapter selectCityGVadapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return serviceCities.size();
        }

        @Override
        public Object getItem(int position) {
            return serviceCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SelectCityItemLayout selectCityItemLayout = null;
            ServiceCity serviceCity = serviceCities.get(position);

            if (AssertValue.isNotNull(convertView)) {
                selectCityItemLayout = (SelectCityItemLayout) convertView;
            } else {
                selectCityItemLayout = new SelectCityItemLayout(mContext);
            }

            selectCityItemLayout.setTag(serviceCity);
            selectCityItemLayout.setCityText(serviceCity.getProvince(), serviceCity.getCity());
            selectCityItemLayout.getItemLL().setOnClickListener(onSelectCityClickListener);
            selectCityItemLayout.getItemLL().setTag(serviceCity);

            return selectCityItemLayout;
        }
    };
}

