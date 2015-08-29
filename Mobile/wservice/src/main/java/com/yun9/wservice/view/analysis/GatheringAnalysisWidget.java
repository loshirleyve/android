package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class GatheringAnalysisWidget extends JupiterRelativeLayout {

    private TextView userNameTv;
    private TextView amountTv;

    public GatheringAnalysisWidget(Context context) {
        super(context);
    }

    public GatheringAnalysisWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GatheringAnalysisWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_gathering_analysis;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        userNameTv = (TextView) this.findViewById(R.id.user_name_tv);
        amountTv = (TextView) this.findViewById(R.id.amount_tv);
    }

    public TextView getUserNameTv() {
        return userNameTv;
    }

    public GatheringAnalysisWidget setUserNameTv(TextView userNameTv) {
        this.userNameTv = userNameTv;
        return this;
    }

    public TextView getAmountTv() {
        return amountTv;
    }

    public GatheringAnalysisWidget setAmountTv(TextView amountTv) {
        this.amountTv = amountTv;
        return this;
    }
}
