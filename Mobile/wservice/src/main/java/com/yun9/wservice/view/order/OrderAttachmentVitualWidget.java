package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by huangbinglong on 15/7/1.
 */
public class OrderAttachmentVitualWidget extends JupiterRelativeLayout{

    public OrderAttachmentVitualWidget(Context context) {
        super(context);
    }

    public OrderAttachmentVitualWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderAttachmentVitualWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_attachment_vitual;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }
}
