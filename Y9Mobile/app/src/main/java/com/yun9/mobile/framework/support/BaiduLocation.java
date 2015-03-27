package com.yun9.mobile.framework.support;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.yun9.mobile.framework.bean.Bean;
import com.yun9.mobile.framework.bean.BeanContext;
import com.yun9.mobile.framework.bean.Initialization;
import com.yun9.mobile.framework.location.Location;
import com.yun9.mobile.framework.location.LocationCallBack;
import com.yun9.mobile.framework.location.LocationCallBack.LocationOutParam;
import com.yun9.mobile.framework.util.Logger;

public class BaiduLocation implements Location, Bean, Initialization {

	public LocationClient mLocationClient = null;

	private BDLocation mLocation;

	private static final Logger logger = Logger.getLogger(BaiduLocation.class);

	public LocationClient getmLocationClient() {
		return mLocationClient;
	}

	public void setmLocationClient(LocationClient mLocationClient) {
		this.mLocationClient = mLocationClient;
	}

	public BDLocation getmLocation() {
		return mLocation;
	}

	public void setmLocation(BDLocation mLocation) {
		this.mLocation = mLocation;
	}

	@Override
	public void init(BeanContext beanContext) {
		logger.d("location init.");
	}

	@Override
	public Class<?> getType() {
		return Location.class;
	}

	@Override
	public void getLocation(LocationCallBack callBack) {
		if(mLocation == null){
			callBack.onFailure();
		}
		else{
			LocationOutParam outParm = new LocationOutParam(mLocation.getLongitude(), mLocation.getLatitude(), mLocation.getCity(), mLocation.getStreet());
			callBack.onSuccess(outParm);
		}
	
	}

}
