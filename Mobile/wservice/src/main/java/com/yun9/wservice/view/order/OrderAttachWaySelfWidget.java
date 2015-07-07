package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.model.ISelectable;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/1/15.
 */
public class OrderAttachWaySelfWidget extends JupiterRelativeLayout implements ISelectable {

    private JupiterRowStyleSutitleLayout sutitleLayout;

    private TextView tipTv;

    private LinearLayout detailLl;

    public OrderAttachWaySelfWidget(Context context) {
        super(context);
    }

    public OrderAttachWaySelfWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderAttachWaySelfWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_attach_way_self;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        sutitleLayout = (JupiterRowStyleSutitleLayout) this.findViewById(R.id.subtitle_layout);
        tipTv = (TextView) this.findViewById(R.id.tip_tv);
        detailLl = (LinearLayout) this.findViewById(R.id.detail_ll);
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
        if (isSelect){
            detailLl.setVisibility(VISIBLE);
        } else {
            detailLl.setVisibility(GONE);
        }
    }
    @Override
    public boolean isSelected() {
        return sutitleLayout.isSelected();
    }

    public JupiterRowStyleSutitleLayout getSutitleLayout() {
        return sutitleLayout;
    }

    public void setSutitleLayout(JupiterRowStyleSutitleLayout sutitleLayout) {
        this.sutitleLayout = sutitleLayout;
    }

    public TextView getTipTv() {
        return tipTv;
    }

    public void setTipTv(TextView tipTv) {
        this.tipTv = tipTv;
    }

    public LinearLayout getDetailLl() {
        return detailLl;
    }

    public void setDetailLl(LinearLayout detailLl) {
        this.detailLl = detailLl;
    }
}
