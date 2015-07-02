package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.yun9.jupiter.model.ISelectable;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/1/15.
 */
public class OrderAttachWayVistWidget extends JupiterRelativeLayout implements ISelectable {

    private JupiterRowStyleSutitleLayout sutitleLayout;

    public OrderAttachWayVistWidget(Context context) {
        super(context);
    }

    public OrderAttachWayVistWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderAttachWayVistWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_attach_way_visit;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        sutitleLayout = (JupiterRowStyleSutitleLayout) this.findViewById(R.id.subtitle_layout);
        buildView();
    }

    private void buildView() {
        sutitleLayout.setSelectMode(true);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        sutitleLayout.setOnClickListener(l);
    }

    @Override
    public void select(boolean isSelect) {
        sutitleLayout.select(isSelect);
    }
}
