package com.yun9.mobile.position.presenter;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.yun9.mobile.position.iview.PositionFragmentIView;
import com.yun9.mobile.position.iview.PositionIView;
import com.yun9.mobile.position.iface.*;

public class PositionFragmentPresenter {
	private static final String TAG = PositionFragmentPresenter.class.getSimpleName();

	private PositionFragmentIView IView;
	private List<PoiInfo> poiLoadList;
	private PoiSearch mPoiSearch;
	private IPoiSearch IPoiSearch;
	private PositionIView positionIView;
	private final int DAFAULT_PAGECAPACITY = 20;
	private int pageCapacity = DAFAULT_PAGECAPACITY;
	private int pageNum = 0;
	private Boolean isRefresh;
	private LatLng searLatLng;
	private Boolean isFirstInitData = true;
    private Boolean hasMoreData;
    
	/**
	 * 界面上展示搜索到的poi点（根据条件过滤一些poi点）
	 */
	private OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){  
	    public void onGetPoiResult(PoiResult result){  
	    	
	    	// 已经没有poi点
	    	if(result == null || result.getAllPoi() == null)
	    	{
	    		if(isFirstInitData){
	    			IView.showError();
	    			return ;
	    		}
	    		return ;
	    	}
	    	
	    	
	    	// 显示控件
	    	IView.showContent();
	    	
	    	
	    	
	    	pageNum++;
	    	
	    	
	    	// 去除超过半径范围的点（百度地图设置的半径不靠谱，设置200米范围也会返回400米范围的数据）
	    	List<PoiInfo> poiDeleteList = new ArrayList<PoiInfo>();
	    	List<PoiInfo> poiList = result.getAllPoi();
	    	for(PoiInfo poi: poiList){
	    		int distance = (int) DistanceUtil.getDistance(poi.location,searLatLng);
	    	
	    		if(distance > IPoiSearch.radius())
	    		{
	    			poiDeleteList.add(poi);
	    		}
	    	}
	    	poiList.removeAll(poiDeleteList);
	    	if(poiList.size() < 1)
	    	{
	    		IView.onPullDownRefreshComplete();
	    		IView.onPullUpRefreshComplete();
	    		return ;
	    	}

	    	
	    	//  还可以加载
            hasMoreData = false;
            IView.setHasMoreData(hasMoreData);

            
            // 判断是通过下拉刷新获取到的poi还是通过上拉加载获取到的
	    	if(isRefresh)
	    	{
	    		// 接收下拉刷新
	    		poiLoadList.clear();
	    		poiLoadList.addAll(poiList);
	    		IView.notifyDataSetChanged();
	    		IView.setLastUpdateTime();
	    		IView.onPullDownRefreshComplete();
	    		
	    		if(!isFirstInitData){
//	    			IView.showToast("刷新成功");
	    		}
	    	}
	    	else
	    	{
	    		poiLoadList.addAll(poiList);
	    		IView.notifyDataSetChanged();
	    		IView.onPullUpRefreshComplete();
//	    		IView.showToast("加载成功");
	    	}
	    	isFirstInitData = false;

	    }  
	    
	    public void onGetPoiDetailResult(PoiDetailResult result){  
	    }  
	};
	
	/**
	 * @param IView
	 */
	public PositionFragmentPresenter(PositionFragmentIView IView, List<PoiInfo> poiLoadList, IPoiSearch IPoiSearch,PositionIView positionIView) {
		super();
		this.IView = IView;
		this.poiLoadList = poiLoadList;
		this.IPoiSearch = IPoiSearch;
		this.positionIView = positionIView;
		
		init();
	}
	
	void init(){
		BDMapInit();
	}
	
	
	/**
	 * 
	 * 将选择的poi点传递给Activity
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	public void onItemClick(AdapterView<?> parent, View view,int position, long id){
		PoiInfo poiInfo = poiLoadList.get(position);
//		PoiInfo poi = new PoiInfo();
//		poi.city = poiInfo.city;
//		poi.address = poiInfo.address;
//		poi.name = poiInfo.name;
//		poi.location = new LatLng(poiInfo.location.latitude, poiInfo.location.longitude);
//		IPoiSearch.setPoi(poi);
//		String wholeAddr = poi.address;
		
		// 将选择的poi点传递给Activity
		IPoiSearch.setPoi(poiInfo);
		
		// 界面展示选择的位置
		positionIView.showChoosePoiAddr(poiInfo.address);
		positionIView.showChoosePoiName(poiInfo.name);
		
		// 将地图中心的位置移到选择的点
		IPoiSearch.freshMapCenter(poiInfo.location.latitude, poiInfo.location.longitude);
	}

	public void BDMapInit() {
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
	}
	
	
	
	/**
	 * 更新位置附近的poi点
	 * @param searchKey
	 */
	public void onPullDownToRefresh(String searchKey){
		PoiNearbySearchOption option = new PoiNearbySearchOption();
		option.radius(IPoiSearch.radius());
		option.keyword(searchKey);
		pageNum = 0;
		option.pageNum(pageNum);
		option.pageCapacity(pageCapacity);
		LatLng location = new LatLng(IPoiSearch.getLat(), IPoiSearch.getLng());
		option.location(location);
		
		mPoiSearch.searchNearby(option);
		isRefresh = true;
		searLatLng = new LatLng(IPoiSearch.getLat(), IPoiSearch.getLng());

        hasMoreData = true;
        IView.setHasMoreData(hasMoreData);
	}
	
	public void getInitData(String searchKey){
		if(isFirstInitData){
			Log.i(TAG, "getInitData");
			
			IView.showLoad();
			
			onPullDownToRefresh(searchKey);
		}
	}
	
	

	/**
	 * 获取位置附近更多的poi点
	 * @param searchKey
	 */
	public void onPullUpToRefresh(String searchKey){
		
		PoiNearbySearchOption option = new PoiNearbySearchOption();
		option.radius(IPoiSearch.radius());
		option.keyword(searchKey);
		option.pageNum(pageNum);
		option.pageCapacity(pageCapacity);
		
		LatLng location = new LatLng(IPoiSearch.getLat(), IPoiSearch.getLng());
		option.location(location);
		
		// 更新位置附近的poi点
		mPoiSearch.searchNearby(option);
		isRefresh = false;
		searLatLng = new LatLng(IPoiSearch.getLat(), IPoiSearch.getLng());

        IView.setHasMoreData(hasMoreData);
	}
	
//	public void setIView(PositionFragmentIView iView) {
//		IView = iView;
//	}
//
//	public void setPoiLoadList(List<PoiInfo> poiLoadList) {
//		this.poiLoadList = poiLoadList;
//	}
//
//
//	public void setIPoiSearch(IPoiSearch iPoiSearch) {
//		IPoiSearch = iPoiSearch;
//	}
	
	public void onDestroy(){
		mPoiSearch.destroy();
	}
}
