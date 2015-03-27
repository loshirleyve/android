package com.yun9.mobile.msg.presenter;

import android.app.Activity;
import android.content.Context;

import com.yun9.mobile.framework.bean.BeanConfig;
import com.yun9.mobile.framework.location.Location;
import com.yun9.mobile.framework.location.LocationCallBack;
import com.yun9.mobile.framework.location.LocationFactory;
import com.yun9.mobile.framework.location.LocationCallBack.LocationOutParam;
import com.yun9.mobile.framework.util.Logger;
import com.yun9.mobile.msg.interfaces.NewMsgCardIView;
import com.yun9.mobile.position.iface.IAcquirePosition;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack.OutParam;
import com.yun9.mobile.position.impl.AcquirePosition;
import com.yun9.mobile.position.impl.PositionFactory;




public class NewMsgCardPositionPresenter {

	private NewMsgCardIView iView;
	private static final Logger logger = Logger.getLogger(NewMsgCardPositionPresenter.class);
	private Activity activity;
	private LocationFactory locationFactory;
	
	private LocationOutParam mOutParm;
	private int[] imageIds = new int[3];
	private String[] texts = new String[]{"正在获取位置", "", "获取位置失败"};
	
	public NewMsgCardPositionPresenter(NewMsgCardIView iView, Activity activity) {
		this.iView = iView;
		this.activity = activity;
		init();
	}
	
	private void init(){
		locationFactory = BeanConfig.getInstance().getBeanContext().get(LocationFactory.class);
		
	
		imageIds[0] = activity.getResources().getIdentifier("ic_search", "drawable", "com.yun9.mobile");
		imageIds[1] = activity.getResources().getIdentifier("ic_locate", "drawable", "com.yun9.mobile");
		imageIds[2] = activity.getResources().getIdentifier("ic_iterror", "drawable", "com.yun9.mobile");
	}

	public void handlePostion() {
		Location location = locationFactory.create(activity);
		
		iView.locationState(texts[0], imageIds[0]);
		// 如果是通过定位获取的addr 则为street地址
		location.getLocation(new LocationCallBack() {
			@Override
			public void onSuccess(LocationOutParam outParm) {
				mOutParm = outParm;
				iView.locationState(mOutParm.getAddr(), imageIds[1]);
			}
			@Override
			public void onFailure() {
				iView.locationState(texts[2], imageIds[2]);
			}
		});
	}

	public void setLocation(LocationOutParam outParm) {
		mOutParm = outParm;
	}
	
	public LocationOutParam getLocation(){
		return mOutParm;
	}

	public void updataLocation() {
		iView.locationState(mOutParm.getAddr(), imageIds[1]);
	}

	public void locateOnClickListenr() {
		IAcquirePosition defaultIAPosition = PositionFactory.createPosition(activity); 
		defaultIAPosition.go2GetPosition(AcquirePosition.MOD_DEFAULT, AcquirePosition.MOD_DEFAULT, new IAcquirePositionCallBack() {
			@Override
			public void onSuccess(OutParam outParm) {
				// 如果是通过到位置模块获取的位置，则addr 为poiName地址（兴趣点）
				LocationOutParam locationOutParm = new LocationOutParam(outParm.getLongitude(), outParm.getLatitude(), outParm.getCity(), outParm.getPoiName());
				setLocation(locationOutParm);
				updataLocation();
			}
			
			@Override
			public void onFailure() {
				
			}
		});		
	}
}
