package com.yun9.mobile.framework.support;

import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Injection;
import com.yun9.mobile.framework.location.Location;
import com.yun9.mobile.framework.location.LocationFactory;

public class BaiduLocationFactory implements LocationFactory, Bean, Injection {

	private BaiduLocation baiduLocation;
	
	private BaiduLocationListener BDLocationListener;
	
	private LocationClient mLocationClient;
	
	private Context context;
	
	public Location create(Context context) {
//		return this.baiduInit(context);
		return baiduLocation;
	}

	// 百度地图
	private Location baiduInit(Context context) {

		mLocationClient.registerLocationListener(BDLocationListener); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(6000 * 1);
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(0); // 最多返回POI个数
		option.setPoiDistance(0); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息

		mLocationClient.setLocOption(option);
		mLocationClient.start();
		mLocationClient.requestLocation();

		return baiduLocation;
	}

	@Override
	public Class<?> getType() {
		return LocationFactory.class;
	}

	@Override
	public void injection(BeanContext beanContext) {
		
		context = beanContext.getApplicationContext();
		init();
	}

	private void init(){
		baiduLocation = new BaiduLocation();
		BDLocationListener = new BaiduLocationListener(baiduLocation);
		mLocationClient = new LocationClient(context);
		baiduInit(context);
	}
}
