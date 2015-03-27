package com.yun9.mobile.position.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.widget.ImageView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.yun9.mobile.position.iface.IAcquirePositionCallBack;
import com.yun9.mobile.position.iview.PositionIView;

public class PositionPresenter {
	private static final String TAG = PositionPresenter.class.getSimpleName();
	/**
	 * 默认搜素范围半径
	 */
	public static final String RADIUS = "radius";
	public static final int DEFAULT_POI_SEARCH_RADIUS = 300;
	private int poiSearchRadius = DEFAULT_POI_SEARCH_RADIUS;
	
	private boolean isFirstLoc = true;// 是否首次定位
	private PositionIView IView;
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private LocationClient mLocClient;
	private BDLocationListener myListener = new MyLocationListenner();
	private Activity activity;
	private SearchLatLng searchLatLng = new SearchLatLng();
	private MyLocationData locData;
	
	private int centerX;
	private int centerY;
	
	private PoiInfo poiChoose;
	private int mode;
	
	public static final String MODE = "MODE";
	public static final int MODE_DEFAULT = 0;
	public static final int MOD_DAKA = 1;
	public static final int MODE_POSITION = 2;
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ADDR = "addr";
	public static final String CITY = "city";
	public static final String POINAME = "poiName";
	private ImageView ivPicPosition;

    public static IAcquirePositionCallBack callBack;
    private  IAcquirePositionCallBack mCallBack = callBack;

    /**
	 * @param iView
	 */
	public PositionPresenter(PositionIView iView, Activity activity,MapView mapView,ImageView ivPicPosition) {
		super();
		IView = iView;
		this.activity = activity;
		this.mMapView = mapView;
		this.ivPicPosition = ivPicPosition;
		init();
	
	}
	
	private void init(){
		initBDMap();
		mCallBack = callBack;
		getMode();
	}

	
	
	private void initBDMap(){
		
		mBaiduMap = mMapView.getMap();
		
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(activity);
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
	 * 获取启动的模式类型
	 */
	private void getMode(){
		mode = activity.getIntent().getIntExtra(MODE, MODE_DEFAULT);
//        try{
//            callBack = (IAcquirePositionCallBack) activity.getIntent().getSerializableExtra("callBack");
//        }catch (Exception e){
//            callBack = null;
//        }

	}




    /**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {
		private static final String CURRENT_POSITION = "当前位置";

		public MyLocationListenner() {
			super();
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
//				// map view 销毁后不在处理新接收的位置
				if (location == null || mMapView == null)
				{
					return;
				}
				
				searchLatLng.setLatitude(location.getLatitude());
				searchLatLng.setLongitude(location.getLongitude());
				// 此处设置开发者获取到的方向信息，顺时针0-360
				locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
				mBaiduMap.setMyLocationData(locData);
				
				
				if (isFirstLoc) {
					isFirstLoc = false;
					LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
					mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(mBaiduMap.getMaxZoomLevel()).build()));//设置缩放级别
					mBaiduMap.animateMapStatus(u);
					
					PoiInfo poi = new PoiInfo();
//					poi.name = CURRENT_POSITION;
					poi.name = location.getStreet();
					poi.city = location.getCity();
					poi.address = location.getAddrStr();
					poi.location = ll;
					setPoiChoose(poi);
					IView.showChoosePoiName(poi.name);
					IView.showChoosePoiAddr(poi.address);
					IView.initBottomView();
					IView.closeBottomLoad();
					IView.showBottomView();
				}
				
			
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}


	public void onDestroy(){
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
	}
	
	
	/**
	 * @author lhk
	 * 搜索点
	 */
	public class SearchLatLng{
		/**
		 * 纬度
		 */
		private double	latitude;
		
		/**
		 * 经度
		 */
		private double longitude;

		/**
		 * @param latitude
		 * @param longitude
		 */
		public SearchLatLng(double latitude, double longitude) {
			super();
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public SearchLatLng() {
			super();
		
		}
		
		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
	}

	public SearchLatLng getSearchLatLng() {
		return searchLatLng;
	}

	public void setSearchLatLng(SearchLatLng searchLatLng) {
		this.searchLatLng = searchLatLng;
	}

	public int getPoiSearchRadius() {
		return poiSearchRadius;
	}

	public void setPoiSearchRadius(int poiSearchRadius) {
		this.poiSearchRadius = poiSearchRadius;
	}

	public PoiInfo getPoiChoose() {
		return poiChoose;
	}

	public void setPoiChoose(PoiInfo poiChoose) {
		this.poiChoose = poiChoose;
	}

	public void onResume() {
		
		mMapView.onResume();		
	}

	public void onPause() {
		mMapView.onPause();		
	}

	/**
	 * 获取地图中心点的X轴坐标
	 * @return
	 */
	private int getCenterX(){

		if(centerX != 0)
		{
			return centerX; 
		}
		else
		{
			int coordinate[] = new int[2];
			ivPicPosition.getLocationOnScreen(coordinate);
			int x = coordinate[0];
			int y = coordinate[1];
			
			int height = ivPicPosition.getHeight();
			int width = ivPicPosition.getWidth();
			
			centerY = y + height;
			centerX = x + width/2;
			return centerX;
		}
		
		
	}
	
	/**
	 * 获取地图中心点的Y轴坐标
	 * @return
	 */
	private int getCenterY(){

		if(centerY != 0)
		{
			return centerY; 
		}
		else
		{
			int coordinate[] = new int[2];
			ivPicPosition.getLocationOnScreen(coordinate);
			int x = coordinate[0];
			int y = coordinate[1];
			
			int height = ivPicPosition.getHeight();
			int width = ivPicPosition.getWidth();
			
			centerY = y + height;
			centerX = x + width/2;
			return centerY;
		}
	}
	
	/**
	 * 获取纬度
	 * @return
	 */
	public double getLat() {
		
		if(mode == MODE_POSITION)
		{
			Projection projection = mBaiduMap.getProjection();
			Point point = new Point(getCenterX(), getCenterY());
			LatLng ll = projection.fromScreenLocation(point);
			return ll.latitude;
		}
		else if(mode == MOD_DAKA){
			return mBaiduMap.getLocationData().latitude;
		}
		else{
			Projection projection = mBaiduMap.getProjection();
			Point point = new Point(getCenterX(), getCenterY());
			LatLng ll = projection.fromScreenLocation(point);
			return ll.latitude;
		}
	}

	/**
	 * 
	 * 获取经度
	 * @return
	 */
	public double getLng() {
		mode = MOD_DAKA;
		if(mode == MODE_POSITION)
		{
			Projection projection = mBaiduMap.getProjection();
			Point point = new Point(getCenterX(), getCenterY());
			LatLng ll = projection.fromScreenLocation(point);
			return ll.longitude;
		}
		else if(mode == MOD_DAKA){
			return mBaiduMap.getLocationData().longitude;
		}
		else{
			Projection projection = mBaiduMap.getProjection();
			Point point = new Point(getCenterX(), getCenterY());
			LatLng ll = projection.fromScreenLocation(point);
			return ll.longitude;
		}
	}

	
	/**
	 * 刷新地图中心位置
	 * @param latitude
	 * @param longitude
	 */
	public void freshMapCenter(double latitude, double longitude) {
		LatLng ll = new LatLng(latitude,longitude);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(mBaiduMap.getMaxZoomLevel()).build()));//设置缩放级别
		mBaiduMap.animateMapStatus(u);
	}

	
	/**
	 * 刷新当前位置
	 */
	public void refresh() {
		MyLocationData locData =mBaiduMap.getLocationData();
		if(locData == null)
		{
			IView.ShowToast("正在初始化...");
			return ;
		}
		else
		{
			freshMapCenter(locData.latitude, locData.longitude);
		}
	}


	public void vRightOnClick() {
		if(poiChoose == null)
		{
			IView.ShowToast("没选择位置");
			return ;
		}
		returnChoosePosition();
	}
	
	/**
	 * 将选中的位置传回给调用者
	 */
	private void returnChoosePosition(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(LATITUDE, poiChoose.location.latitude);
        resultIntent.putExtra(LONGITUDE, poiChoose.location.longitude);
        resultIntent.putExtra(ADDR, poiChoose.address);
        resultIntent.putExtra(CITY, poiChoose.city);
        resultIntent.putExtra(CITY, poiChoose.name);
        if(mCallBack != null)
        {
            double longitude = poiChoose.location.longitude;
            double latitude = poiChoose.location.latitude;
            String city = poiChoose.city;
            String addr = poiChoose.address;
            String poiName = poiChoose.name;
            IAcquirePositionCallBack.OutParam outParam = new IAcquirePositionCallBack.OutParam(longitude, latitude, city, addr, poiName);
            activity.finish();
            mCallBack.onSuccess(outParam);
            mCallBack = null;
        }
        else{
            activity.setResult(Activity.RESULT_OK, resultIntent);
            activity.finish();
        }


	}

    public void canelOnClick() {
        activity.finish();
        if(mCallBack != null){
            mCallBack.onFailure();
        }
        mCallBack = null;

    }
}
