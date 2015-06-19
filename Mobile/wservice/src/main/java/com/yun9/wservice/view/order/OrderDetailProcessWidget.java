package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/19.
 */
public class OrderDetailProcessWidget extends JupiterRelativeLayout{

    private LinearLayout processContainer;

    public OrderDetailProcessWidget(Context context) {
        super(context);
    }

    public OrderDetailProcessWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailProcessWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData() {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_process;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        processContainer = (LinearLayout) this.findViewById(R.id.process_container);
        LinearLayout container = new OrderDetailProcessItemWidget(context).getContainer();
        LinearLayout item = (LinearLayout) container.getChildAt(0);
        container.removeView(item);
        processContainer.addView(item);
        container = new OrderDetailProcessItemWidget(context).getContainer();
        item = (LinearLayout) container.getChildAt(0);
        container.removeView(item);
        processContainer.addView(item);
        container = new OrderDetailProcessItemWidget(context).getContainer();
        item = (LinearLayout) container.getChildAt(0);
        container.removeView(item);
        processContainer.addView(item);
    }
}
