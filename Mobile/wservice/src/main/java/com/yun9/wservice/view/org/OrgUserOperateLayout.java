package com.yun9.wservice.view.org;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by rxy on 15/7/9.
 */
public class OrgUserOperateLayout extends JupiterRelativeLayout {

    private TextView add_orghelper;
    private TextView delete_orguser;
    private TextView cancle;

    @Override
    protected int getContextView() {
        return R.layout.popup_orguser_operate;
    }


    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        add_orghelper = (TextView) findViewById(R.id.add_orghelper);
        delete_orguser = (TextView) findViewById(R.id.delete_orguser);
        cancle = (TextView) findViewById(R.id.cancle);
    }

    public OrgUserOperateLayout(Context context) {
        super(context);
    }

    public OrgUserOperateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrgUserOperateLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public TextView getAdd_orghelper() {
        return add_orghelper;
    }

    public TextView getDelete_orguser() {
        return delete_orguser;
    }

    public TextView getCancle() {
        return cancle;
    }


}
