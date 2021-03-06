package com.yun9.wservice.view.store;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/25.
 */
public class SelectCityItemLayout extends JupiterRelativeLayout {

    private TextView cityTV;

    private LinearLayout itemLL;

    private RelativeLayout itemRL;

    public SelectCityItemLayout(Context context) {
        super(context);
    }

    public SelectCityItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectCityItemLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_select_city_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        cityTV = (TextView) findViewById(R.id.city_tv);
        itemLL = (LinearLayout) findViewById(R.id.item_ll);
        itemRL = (RelativeLayout) findViewById(R.id.item_rl);
    }

    public LinearLayout getItemLL() {
        return itemLL;
    }

    public TextView getCityTV() {
        return cityTV;
    }

    public void selected(boolean selected) {
        itemLL.setEnabled(!selected);
    }
}
