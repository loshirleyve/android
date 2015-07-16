package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailAdvisorWidget extends JupiterRelativeLayout{

    private ImageView userHeadIV;
    private TextView userNameTV;
    private TextView contactUsIV;
    private ImageView callUsIv;

    private Order order;

    public OrderDetailAdvisorWidget(Context context) {
        super(context);
    }

    public OrderDetailAdvisorWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailAdvisorWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWitdhData(Order order) {
        this.order = order;
        CacheUser user = UserCache.getInstance().getUser(order.getOrder().getAdviseruserid());
        if (user != null){
            ImageLoaderUtil.getInstance(this.mContext).displayImage(user.getHead(),userHeadIV);
            userNameTV.setText(user.getName());
        }
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_advisor;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        userHeadIV  = (ImageView) this.findViewById(R.id.user_head_iv);
        userNameTV = (TextView) this.findViewById(R.id.user_name_tv);
        contactUsIV = (TextView) this.findViewById(R.id.contact_us_iv);
        callUsIv = (ImageView) this.findViewById(R.id.call_us_iv);
        buildView();
    }

    private void buildView() {

    }

    public ImageView getUserHeadIV() {
        return userHeadIV;
    }

    public TextView getUserNameTV() {
        return userNameTV;
    }

    public TextView getContactUsIV() {
        return contactUsIV;
    }

    public ImageView getCallUsIv() {
        return callUsIv;
    }
}
