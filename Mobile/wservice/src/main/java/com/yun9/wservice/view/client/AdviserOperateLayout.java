package com.yun9.wservice.view.client;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by rxy on 16/1/18.
 */
public class AdviserOperateLayout extends JupiterRelativeLayout {

    private TextView delete_adviser;
    private TextView cancle;

    @Override
    protected int getContextView() {
        return R.layout.popup_client_deladviser;
    }


    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        delete_adviser = (TextView) findViewById(R.id.delete_adviser);
        cancle = (TextView) findViewById(R.id.cancle);
    }

    public AdviserOperateLayout(Context context) {
        super(context);
    }

    public AdviserOperateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdviserOperateLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TextView getDelete_orguser() {
        return delete_adviser;
    }

    public TextView getCancle() {
        return cancle;
    }


}
