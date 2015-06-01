package com.yun9.jupiter.location;

import com.yun9.jupiter.bean.Bean;

/**
 * Created by Leon on 15/6/1.
 */
public class EmptyLocationFactory implements LocationFactory,Bean {
    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public int requestLocation() {
        return 0;
    }

    @Override
    public void poiSearch(LocationBean locationBean, String keyword, int pageNum, int radius) {

    }

    @Override
    public void setOnLocationListener(OnLocationListener onLocationListener) {

    }

    @Override
    public void setOnGetPoiInfoListener(OnGetPoiInfoListener onGetPoiInfoListener) {

    }

    @Override
    public Class<?> getType() {
        return LocationFactory.class;
    }
}
