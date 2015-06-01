package com.yun9.jupiter.location;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;

/**
 * Created by Leon on 15/5/28.
 */
public class LocationListener implements BDLocationListener {

    private static Logger logger = Logger.getLogger(LocationListener.class);

    private OnLocationListener onLocationListener;

    public LocationListener(OnLocationListener onLocationListener){
        this.onLocationListener = onLocationListener;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null)
            return;
        if (AssertValue.isNotNull(onLocationListener)){
            LocationBean locationBean = new LocationBean();
            locationBean.setCode(location.getLocType());
            locationBean.setTime(location.getTime());
            locationBean.setLatitude(location.getLatitude());
            locationBean.setLontitude(location.getLongitude());
            locationBean.setRadius(location.getRadius());

            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                locationBean.setSpeed(location.getSpeed());
                locationBean.setSatelliteNumber(location.getSatelliteNumber());
                locationBean.setLocType(BaiduLocationFactory.LocType_GPS);
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                locationBean.setProvince(location.getProvince());
                locationBean.setCity(location.getCity());
                locationBean.setDistrict(location.getDistrict());
                locationBean.setAddr(location.getAddrStr());
                locationBean.setLocType(BaiduLocationFactory.LocType_NETWORK);
            }
            onLocationListener.onReceiveLocation(locationBean);
        }
    }
}
