package com.yun9.jupiter.location;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.util.AssertValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/28.
 */
public class LocationFactory implements Bean, Initialization {

    public static final int LocType_GPS = 0;
    public static final int LocType_NETWORK = 1;


    private LocationClient locationClient = null;

    private PoiSearch poiSearch = null;

    private boolean started = false;

    private LocationListener lastLocationListener;

    private OnGetPoiInfoListener onGetPoiInfoListener;

    @Override
    public Class<?> getType() {
        return LocationFactory.class;
    }

    @Override
    public void init(BeanManager beanManager) {
        //初始化地图
        locationClient = new LocationClient(beanManager.getApplicationContext());
        this.initLocation();
        SDKInitializer.initialize(beanManager.getApplicationContext());

        this.poiSearch =PoiSearch.newInstance();

    }

    public void start() {
        if (!started) {
            locationClient.start();
        }
    }

    public void stop() {
        if (!locationClient.isStarted()) {
            locationClient.stop();
        }
    }

    public int requestLocation() {
        int ret = locationClient.requestLocation();

        return ret;
    }

    public void poiSearch(LocationBean locationBean, String keyword,int pageNum,int radius) {

        if (AssertValue.isNotNull(locationBean)) {
            poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                @Override
                public void onGetPoiResult(PoiResult poiResult) {
                    List<PoiInfo> poiInfoList = poiResult.getAllPoi();
                    List<PoiInfoBean> poiInfoBeans = new ArrayList<PoiInfoBean>();

                    if (AssertValue.isNotNullAndNotEmpty(poiInfoList) && AssertValue.isNotNull(onGetPoiInfoListener)) {

                        for (PoiInfo poiInfo : poiInfoList) {
                            PoiInfoBean poiInfoBean = new PoiInfoBean();

                            poiInfoBean.setLatitude(poiInfo.location.latitude);
                            poiInfoBean.setLontitude(poiInfo.location.longitude);
                            poiInfoBean.setAddress(poiInfo.address);
                            poiInfoBean.setCity(poiInfo.city);
                            poiInfoBean.setName(poiInfo.name);
                            poiInfoBean.setPhoneNum(poiInfo.phoneNum);
                            poiInfoBean.setPostCode(poiInfo.postCode);
                            poiInfoBean.setUid(poiInfo.uid);

                            poiInfoBeans.add(poiInfoBean);
                        }
                    }

                    onGetPoiInfoListener.onGetPoiInfo(poiInfoBeans);

                }

                @Override
                public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                }
            });

            PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
            poiNearbySearchOption.location(new LatLng(locationBean.getLatitude(), locationBean.getLontitude()));
            poiNearbySearchOption.keyword(keyword != null ? keyword : "");
            poiNearbySearchOption.radius(radius);
            poiNearbySearchOption.pageNum(pageNum);
            poiSearch.searchNearby(poiNearbySearchOption);
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(1000 * 60 * 10);//设置发起定位请求的间隔时间为10分钟
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        this.locationClient.setLocOption(option);
    }

    public boolean isStarted() {
        return started;
    }

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        if (AssertValue.isNotNull(lastLocationListener)) {
            locationClient.unRegisterLocationListener(lastLocationListener);
        }
        this.lastLocationListener = new LocationListener(onLocationListener);
        locationClient.registerLocationListener(lastLocationListener);
    }

    public void setOnGetPoiInfoListener(OnGetPoiInfoListener onGetPoiInfoListener) {
        this.onGetPoiInfoListener = onGetPoiInfoListener;
    }
}
