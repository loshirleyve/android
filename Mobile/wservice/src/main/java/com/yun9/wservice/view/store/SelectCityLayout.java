package com.yun9.wservice.view.store;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.ServiceCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/25.
 */
public class SelectCityLayout extends JupiterRelativeLayout {

    private JupiterGridView cityGV;

    private OnClickListener onClickListener;

    private List<ServiceCity> serviceCitys = new ArrayList<>();

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
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        cityGV = (JupiterGridView) findViewById(R.id.city_gv);
    }

    public JupiterGridView getCityGV() {
        return cityGV;
    }
}
