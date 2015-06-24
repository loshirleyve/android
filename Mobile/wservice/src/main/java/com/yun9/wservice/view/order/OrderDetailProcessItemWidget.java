package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/19.
 */
public class OrderDetailProcessItemWidget extends JupiterRelativeLayout{

    private LinearLayout container;

    private ImageView circleIV;

    private TextView stateNameTV;

    private TextView stateTimeTV;

    public OrderDetailProcessItemWidget(Context context) {
        super(context);
    }

    public OrderDetailProcessItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailProcessItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Order.OrderLog log) {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_process_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        container = (LinearLayout) this.findViewById(R.id.container);
        circleIV = (ImageView) this.findViewById(R.id.circle_iv);
        stateNameTV = (TextView) this.findViewById(R.id.state_name_tv);
        stateTimeTV = (TextView) this.findViewById(R.id.state_time_tv);
    }

    public LinearLayout getContainer() {
        return container;
    }

    public ImageView getCircleIV() {
        return circleIV;
    }

    public TextView getStateNameTV() {
        return stateNameTV;
    }

    public TextView getStateTimeTV() {
        return stateTimeTV;
    }
}
