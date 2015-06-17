package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

import org.w3c.dom.Text;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailAdvisorWidget extends JupiterRelativeLayout{

    private ImageView userHeadIV;
    private TextView userNameTV;
    private ImageView contactUsIV;

    public OrderDetailAdvisorWidget(Context context) {
        super(context);
    }

    public OrderDetailAdvisorWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailAdvisorWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildView(Order order) {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_advisor;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        userHeadIV  = (ImageView) this.findViewById(R.id.user_head_iv);
        userNameTV = (TextView) this.findViewById(R.id.user_name_tv);
        contactUsIV = (ImageView) this.findViewById(R.id.contact_us_iv);
    }
}