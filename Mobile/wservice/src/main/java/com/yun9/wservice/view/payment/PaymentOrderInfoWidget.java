package com.yun9.wservice.view.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentOrderInfoWidget extends JupiterRelativeLayout{

    private TextView paymentTitleTv;

    private TextView paymentSubTitleTv;

    private TextView paymentInstNameTv;

    private TextView sourceSnTv;

    public PaymentOrderInfoWidget(Context context) {
        super(context);
    }

    public PaymentOrderInfoWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaymentOrderInfoWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_payment_order_info;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        paymentTitleTv = (TextView) this.findViewById(R.id.payment_title_tv);
        paymentSubTitleTv = (TextView) this.findViewById(R.id.payment_sub_title_tv);
        paymentInstNameTv = (TextView) this.findViewById(R.id.payment_inst_name);
        sourceSnTv = (TextView) this.findViewById(R.id.source_sn_tv);
    }

    public TextView getPaymentTitleTv() {
        return paymentTitleTv;
    }

    public TextView getPaymentSubTitleTv() {
        return paymentSubTitleTv;
    }

    public TextView getPaymentInstNameTv() {
        return paymentInstNameTv;
    }

    public TextView getSourceSnTv() {
        return sourceSnTv;
    }
}
