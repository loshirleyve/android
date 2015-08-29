package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class SignBillClassifyWidget extends JupiterRelativeLayout {

    private TextView nameTv;
    private TextView numsTv;
    private TextView amountTv;

    public SignBillClassifyWidget(Context context) {
        super(context);
    }

    public SignBillClassifyWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SignBillClassifyWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_signbill_classify;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        nameTv = (TextView) this.findViewById(R.id.classify_name_tv);
        numsTv = (TextView) this.findViewById(R.id.classify_nums_tv);
        amountTv = (TextView) this.findViewById(R.id.classify_amount_tv);
    }

    public TextView getNameTv() {
        return nameTv;
    }

    public TextView getNumsTv() {
        return numsTv;
    }

    public TextView getAmountTv() {
        return amountTv;
    }
}
