package com.yun9.mobile.position.iface;

import com.baidu.mapapi.search.core.PoiInfo;

public interface IPoiSearch {
	
	/**
	 * @return 纬度
	 */
	public double getLat();
	
	/**
	 * 
	 * @return 经度
	 */
	public double getLng();
	
	
	/**
	 * @return 检索的半径范围
	 */
	public int radius();
	
	
	/**
	 * 返回检索到的POI
	 * @param poi
	 */
	public void setPoi(PoiInfo poi);
	
	
	public void freshMapCenter(double latitude, double longitude);
}
