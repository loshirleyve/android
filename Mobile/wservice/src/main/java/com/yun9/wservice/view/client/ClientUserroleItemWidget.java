package com.yun9.wservice.view.client;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by li on 2015/8/27.
 */
public class ClientUserroleItemWidget extends JupiterRelativeLayout {
    @ViewInject(id = R.id.titleTv)
    private TextView titleTv;
    @ViewInject(id = R.id.contentTv)
    private TextView contentTv;

    public ClientUserroleItemWidget(Context context) {
        super(context);
    }

    public ClientUserroleItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClientUserroleItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_client_userrole_item;
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

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }
}
