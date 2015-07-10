package com.yun9.wservice.view.org;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/9/15.
 */
public class OrgUserContactWidget extends JupiterRelativeLayout{

    private TextView titleTv;

    private TextView contentTv;

    private LinearLayout operationLl;

    public OrgUserContactWidget(Context context) {
        super(context);
    }

    public OrgUserContactWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrgUserContactWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_org_user_contact;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        titleTv = (TextView) this.findViewById(R.id.title_tv);
        contentTv = (TextView) this.findViewById(R.id.content_tv);
        operationLl = (LinearLayout) this.findViewById(R.id.operation_ll);
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public void setTitleTv(TextView titleTv) {
        this.titleTv = titleTv;
    }

    public TextView getContentTv() {
        return contentTv;
    }

    public void setContentTv(TextView contentTv) {
        this.contentTv = contentTv;
    }

    public LinearLayout getOperationLl() {
        return operationLl;
    }

    public void setOperationLl(LinearLayout operationLl) {
        this.operationLl = operationLl;
    }
}
