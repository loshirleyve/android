package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by rxy on 15/8/20.
 */
public class WidgetOrderListSubItem extends JupiterRelativeLayout {

    private TextView workorderitemname;
    private TextView workorderitemstate;
    private TextView workorderitemnums;

    public WidgetOrderListSubItem(Context context) {
        super(context);
    }

    public WidgetOrderListSubItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidgetOrderListSubItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_sub_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        workorderitemname = (TextView) findViewById(R.id.workorderitemname);
        workorderitemstate = (TextView) findViewById(R.id.workorderitemstate);
        workorderitemnums = (TextView) findViewById(R.id.workorderitemnums);
    }

    public TextView getWorkorderitemname() {
        return workorderitemname;
    }

    public void setWorkorderitemname(TextView workorderitemname) {
        this.workorderitemname = workorderitemname;
    }

    public TextView getWorkorderitemstate() {
        return workorderitemstate;
    }

    public void setWorkorderitemstate(TextView workorderitemstate) {
        this.workorderitemstate = workorderitemstate;
    }

    public TextView getWorkorderitemnums() {
        return workorderitemnums;
    }

    public void setWorkorderitemnums(TextView workorderitemnums) {
        this.workorderitemnums = workorderitemnums;
    }
}
