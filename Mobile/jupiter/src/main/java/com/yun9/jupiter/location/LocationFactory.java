package com.yun9.jupiter.location;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/5/28.
 */
public class LocationFactory implements Bean, Initialization {

    public static final int LocType_GPS = 0;
    public static final int LocType_NETWORK = 1;


    private LocationClient locationClient = null;

    private boolean started = false;

    private LocationListener lastLocationListener;

    @Override
    public Class<?> getType() {
        return LocationFactory.class;
    }

    @Override
    public void init(BeanManager beanManager) {
        //初始化地图
        locationClient = new LocationClient(beanManager.getApplicationContext());
        this.initLocation();
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
}
