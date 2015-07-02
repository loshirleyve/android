package com.yun9.wservice.view.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/30.
 */
public class RechargeRecordItemWidget extends JupiterRelativeLayout{

    private JupiterRowStyleSutitleLayout sutitleLayout;

    private TextView rechardIdTv;

    private TextView rechargeStateTv;

    public RechargeRecordItemWidget(Context context) {
        super(context);
    }

    public RechargeRecordItemWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RechargeRecordItemWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_recharge_record_item;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        sutitleLayout = (JupiterRowStyleSutitleLayout) this.findViewById(R.id.subtitle_layout);
        rechardIdTv = (TextView) this.findViewById(R.id.recharge_id_tv);
        rechargeStateTv = (TextView) this.findViewById(R.id.recharge_state_tv);
    }

    private void buildView() {
        sutitleLayout.getTimeTv().setTextColor(getResources().getColor(R.color.devide_line));
        sutitleLayout.getTitleTV().setTextColor(getResources().getColor(R.color.purple_font));
        sutitleLayout.getSutitleTv().setTextColor(getResources().getColor(R.color.purple_font));
    }

    public JupiterRowStyleSutitleLayout getSutitleLayout() {
        return sutitleLayout;
    }

    public void setSutitleLayout(JupiterRowStyleSutitleLayout sutitleLayout) {
        this.sutitleLayout = sutitleLayout;
    }

    public TextView getRechardIdTv() {
        return rechardIdTv;
    }

    public void setRechardIdTv(TextView rechardIdTv) {
        this.rechardIdTv = rechardIdTv;
    }

    public TextView getRechargeStateTv() {
        return rechargeStateTv;
    }

    public void setRechargeStateTv(TextView rechargeStateTv) {
        this.rechargeStateTv = rechargeStateTv;
    }
}
