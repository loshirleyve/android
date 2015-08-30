package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/8/29.
 */
public class WorkorderAnalysisUserWidget extends JupiterRelativeLayout {

    private TextView userNameTv;
    private TextView allNumsTv;
    private TextView completeNumsTv;
    private TextView waitNumsTv;
    private TextView completeRateTv;

    public WorkorderAnalysisUserWidget(Context context) {
        super(context);
    }

    public WorkorderAnalysisUserWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkorderAnalysisUserWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_worker_order_analysis_user;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        userNameTv = (TextView) this.findViewById(R.id.user_name_tv);
        allNumsTv = (TextView) this.findViewById(R.id.allnums_tv);
        completeNumsTv = (TextView) this.findViewById(R.id.complete_nums_tv);
        waitNumsTv = (TextView) this.findViewById(R.id.wait_nums_tv);
        completeRateTv = (TextView) this.findViewById(R.id.completerate_tv);

    }

    public TextView getUserNameTv() {
        return userNameTv;
    }

    public WorkorderAnalysisUserWidget setUserNameTv(TextView userNameTv) {
        this.userNameTv = userNameTv;
        return this;
    }

    public TextView getAllNumsTv() {
        return allNumsTv;
    }

    public TextView getCompleteNumsTv() {
        return completeNumsTv;
    }

    public TextView getWaitNumsTv() {
        return waitNumsTv;
    }

    public TextView getCompleteRateTv() {
        return completeRateTv;
    }
}
