package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderProviderWidget extends JupiterRelativeLayout{

    private TextView instNameTV;
    private TextView instPhoneTV;
    private LinearLayout contactUsLL;

    public OrderProviderWidget(Context context) {
        super(context);
    }

    public OrderProviderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderProviderWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildView() {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_provider;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        instNameTV = (TextView) this.findViewById(R.id.inst_name_tv);
        instPhoneTV = (TextView) this.findViewById(R.id.inst_phone_tv);
        contactUsLL = (LinearLayout) this.findViewById(R.id.contact_us_ll);
    }
}
