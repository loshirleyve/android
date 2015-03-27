package com.yun9.mobile.framework.support;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class BaiduLocationListener implements BDLocationListener {

	private static final String TAG = BaiduLocationListener.class.getSimpleName();
	private BaiduLocation baiduLocation;

	public BaiduLocationListener(BaiduLocation baiduLocation) {
		this.baiduLocation = baiduLocation;
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		if (location == null)
			return;

		
		baiduLocation.setmLocation(location);

//		StringBuffer sb = new StringBuffer(1024);
//		sb.append("time : ");
//		sb.append(location.getTime());
//		sb.append("\nerror code : ");
//		sb.append(location.getLocType());
//		sb.append("\nlatitude : ");
//		sb.append(location.getLatitude());
//		sb.append("\nlontitude : ");
//		sb.append(location.getLongitude());
//		sb.append("\nradius : ");
//		sb.append(location.getRadius());
//		
//		sb.append("\ngetAddrStr : ");
//		sb.append(location.getAddrStr());
//		
//		if (location.getLocType() == BDLocation.TypeGpsLocation) {
//			sb.append("\nspeed : ");
//			sb.append(location.getSpeed());
//			sb.append("\nsatellite : ");
//			sb.append(location.getSatelliteNumber());
//		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//			sb.append("\naddr : ");
//			sb.append(location.getAddrStr());
//		}
//		 Log.i(TAG,sb.toString());
	}

	@Override
	public void onReceivePoi(BDLocation poiLocation) {
		// 将在下个版本中去除poi功能
		if (poiLocation == null) {
			return;
		}
//		 StringBuffer sb = new StringBuffer(256);
//		 sb.append("Poi time : ");
//		 sb.append(poiLocation.getTime());
//		 sb.append("\nerror code : ");
//		 sb.append(poiLocation.getLocType());
//		 sb.append("\nlatitude : ");
//		 sb.append(poiLocation.getLatitude());
//		 sb.append("\nlontitude : ");
//		 sb.append(poiLocation.getLongitude());
//		 sb.append("\nradius : ");
//		 sb.append(poiLocation.getRadius());
//		 if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation){
//		 sb.append("\naddr : ");
//		 sb.append(poiLocation.getAddrStr());
//		 }
//		 if(poiLocation.hasPoi()){
//		 sb.append("\nPoi:");
//		 sb.append(poiLocation.getPoi());
//		 }else{
//		 sb.append("noPoi information");
//		 }
//		 Log.i(TAG,sb.toString());
	}
}
