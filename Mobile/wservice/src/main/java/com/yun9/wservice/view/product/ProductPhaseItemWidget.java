package com.yun9.wservice.view.product;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/26.
 */
public class ProductPhaseItemWidget extends JupiterRelativeLayout {

    private TextView phaseTV;

    public ProductPhaseItemWidget(Context context) {
        super(context);
    }

    public ProductPhaseItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductPhaseItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_product_phase_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        phaseTV = (TextView) findViewById(R.id.phase_tx);
    }

    public TextView getPhaseTV() {
        return phaseTV;
    }
}
