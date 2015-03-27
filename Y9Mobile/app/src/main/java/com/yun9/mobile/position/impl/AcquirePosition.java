package com.yun9.mobile.position.impl;

import android.app.Activity;
import android.content.Intent;
import com.yun9.mobile.position.activity.PositionActivity;
import com.yun9.mobile.position.iface.IAcquirePosition;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack;
import com.yun9.mobile.position.presenter.PositionPresenter;

import java.io.Serializable;


public class AcquirePosition implements IAcquirePosition {

	/**
	 * 默认请求码
	 */
	private static final int REQ_POSITION_DEFAULT = 0x1001;
	/**
	 * 默认半径
	 */
	public static final int RADUIS_DEFAULT = PositionPresenter.DEFAULT_POI_SEARCH_RADIUS;
	
	
	
	/**
	 * 打卡模式
	 */
	public static final int MOD_DAKA = PositionPresenter.MOD_DAKA;
	
	/**
	 * 不限制获取位置模式
	 */
	public static final int MODE_POSITION = PositionPresenter.MODE_POSITION;
	
	/**
	 * 默认模式
	 */
	public static final int MOD_DEFAULT = PositionPresenter.MODE_DEFAULT;


	private Activity activity;
	/**
	 * @param activity
	 */
	public AcquirePosition(Activity activity) {
		super();
		this.activity = activity;
	}
	
	



	@Override
	public IAcquirePositionCallBack.OutParam getResult(int resultCode, Intent data) {
		
		if(resultCode != Activity.RESULT_OK)
		{
			return null;
		}
	
		
		double longitude;
		double latitude;
		String city;
		String addr;
		String poiName;
		try{
			longitude = data.getDoubleExtra(PositionPresenter.LONGITUDE, 0);
			latitude = data.getDoubleExtra(PositionPresenter.LATITUDE, 0);
			city = data.getStringExtra(PositionPresenter.CITY);
			addr = data.getStringExtra(PositionPresenter.ADDR);
			poiName = data.getStringExtra(PositionPresenter.POINAME);
			if((longitude == 0) || (latitude == 0) || (city == null) || (addr == null))
			{
				return null;
			}
			
			return new IAcquirePositionCallBack.OutParam(longitude, latitude, city, addr, poiName);
				
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void go2GetPosition(int mode, int radius, int reqCode) {
		Intent intent = new Intent(activity, PositionActivity.class);
		intent.putExtra(PositionPresenter.MODE, mode);
		intent.putExtra(PositionPresenter.RADIUS, radius);
		activity.startActivityForResult(intent, reqCode);
	}

    @Override
    public void go2GetPosition(int mode, int radius,  IAcquirePositionCallBack callBack) {
        Intent intent = new Intent(activity, PositionActivity.class);
        intent.putExtra(PositionPresenter.MODE, mode);
        intent.putExtra(PositionPresenter.RADIUS, radius);
        PositionPresenter.callBack =callBack;
        activity.startActivity(intent);
    }
}
