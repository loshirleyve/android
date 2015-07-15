package com.yun9.wservice.view.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/15/15.
 */
public class PaymentResultItemWidget extends JupiterRelativeLayout{

    private TextView payWayNameTv;

    private TextView payTimeTv;

    private TextView payAmountTv;

    private TextView payStateNameTv;

    public PaymentResultItemWidget(Context context) {
        super(context);
    }

    public PaymentResultItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaymentResultItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_payment_result_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        payWayNameTv = (TextView) this.findViewById(R.id.pay_way_name_tv);
        payTimeTv = (TextView) this.findViewById(R.id.pay_time_tv);
        payStateNameTv = (TextView) this.findViewById(R.id.pay_state_name_tv);
        payAmountTv = (TextView) this.findViewById(R.id.pay_amount_tv);
    }

    public TextView getPayWayNameTv() {
        return payWayNameTv;
    }

    public TextView getPayTimeTv() {
        return payTimeTv;
    }

    public TextView getPayAmountTv() {
        return payAmountTv;
    }

    public TextView getPayStateNameTv() {
        return payStateNameTv;
    }
}
