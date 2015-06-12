package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderRechargeWidget extends JupiterRelativeLayout{

    private TextView titleTV;
    private TextView accountTV;
    private ImageView rechargeIV;

    public OrderRechargeWidget(Context context) {
        super(context);
    }

    public OrderRechargeWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderRechargeWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_recharge;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        titleTV = (TextView) this.findViewById(R.id.title_tv);
        accountTV = (TextView) this.findViewById(R.id.account_tv);
        rechargeIV = (ImageView) this.findViewById(R.id.recharge_iv);
    }

    public void buildWithData(long balance) {
        accountTV.setText(balance+"元");
    }
}
