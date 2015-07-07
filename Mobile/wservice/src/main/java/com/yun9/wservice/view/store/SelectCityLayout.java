package com.yun9.wservice.view.store;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/25.
 */
public class SelectCityLayout extends JupiterRelativeLayout {

    private JupiterGridView cityGV;

    private JupiterGridView districtGV;

    public SelectCityLayout(Context context) {
        super(context);
    }

    public SelectCityLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectCityLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.popup_select_city;
    }


    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        cityGV = (JupiterGridView) findViewById(R.id.city_gv);
        districtGV = (JupiterGridView) findViewById(R.id.district_gv);
    }

    public JupiterGridView getCityGV() {
        return cityGV;
    }

    public JupiterGridView getDistrictGV() {
        return districtGV;
    }
}
