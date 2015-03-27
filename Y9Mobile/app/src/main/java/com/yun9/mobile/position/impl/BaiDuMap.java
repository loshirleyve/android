package com.yun9.mobile.position.impl;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yun9.mobile.position.iface.Map;
import com.yun9.mobile.position.iface.MapCallBack;
import com.yun9.mobile.position.iface.MapCallBack.OutParam;

public class BaiDuMap implements Map{

	
	private Context context;
	private LocationClient mLocClient;
	private BDLocationListener myListener = new MyLocationListenner();
	
	private BDLocation baiDulcation = null;
	
	public BaiDuMap(Context context) {
		this.context = context;
		
		init();
	}
	
	private void init(){
		// 定位初始化
		mLocClient = new LocationClient(context);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(6000);
		option.disableCache(false);//禁止启用缓存定位
		option.setPoiNumber(0);	//最多返回POI个数	
		option.setPoiDistance(0); //poi查询距离		
		option.setPoiExtraInfo(false); //是否需要POI的电话和地址等详细信息	
		mLocClient.setLocOption(option);
		mLocClient.start();
	}
	
	
	
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		public MyLocationListenner() {
			super();
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
			{
				return;
			}
			
			baiDulcation = location;
			
			
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}




	@Override
	public void getPosition(MapCallBack callBack) {
		if(baiDulcation == null){
			
		}
		
		OutParam outParm = new OutParam(baiDulcation.getLongitude(), baiDulcation.getLatitude(), baiDulcation.getCity(), baiDulcation.getStreet());
		callBack.onSuccess(outParm );
		
	}


}
