package com.yun9.jupiter.location;

/**
 * Created by Leon on 15/6/1.
 */
public interface LocationFactory {
    public void start();
    public void stop();
    public int requestLocation();
    public void poiSearch(LocationBean locationBean, String keyword, int pageNum, int radius);
    public void setOnLocationListener(OnLocationListener onLocationListener);
    public void setOnGetPoiInfoListener(OnGetPoiInfoListener onGetPoiInfoListener);
}
