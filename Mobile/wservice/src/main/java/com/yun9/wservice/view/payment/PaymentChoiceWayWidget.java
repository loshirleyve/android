package com.yun9.wservice.view.payment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Payinfo;

/**
 * 选择付款方式的某一项
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentChoiceWayWidget extends JupiterRelativeLayout{

    private JupiterRowStyleSutitleLayout sutitleLayout;

    private LinearLayout paymodeDetailLl;

    private EditText editText;

    private TextView paymodeDetailTv;

    private Payinfo.PaymodeInfo paymodeInfo;

    public PaymentChoiceWayWidget(Context context) {
        super(context);
    }

    public PaymentChoiceWayWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaymentChoiceWayWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Payinfo.PaymodeInfo paymodeInfo) {
        this.paymodeInfo = paymodeInfo;
        if (paymodeInfo == null){
            sutitleLayout.getMainIV().setVisibility(GONE);
            paymodeDetailLl.setVisibility(GONE);
        } else {
            sutitleLayout.getTitleTV().setText(paymodeInfo.getPaymodeName());
            sutitleLayout.getSutitleTv().setText(paymodeInfo.getDescr());
            paymodeDetailTv.setText(paymodeInfo.getPaymodeTips());
            if (paymodeInfo.getUseAmount() > 0){
                editText.setText(paymodeInfo.getUseAmount()+"");
            }
        }
    }

    public void showDetail() {
        if (paymodeInfo != null) {
            sutitleLayout.select(true);
            paymodeDetailLl.setVisibility(VISIBLE);
        }
    }

    public void hideDetail() {
        sutitleLayout.select(false);
        paymodeDetailLl.setVisibility(GONE);
    }

    public double getUserAmount() {
        String ua = editText.getText().toString();
        if (AssertValue.isNotNullAndNotEmpty(ua)){
            return Double.valueOf(ua);
        }
        return 0;
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_payment_choice_way;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        sutitleLayout = (JupiterRowStyleSutitleLayout) this.findViewById(R.id.title_layout);
        paymodeDetailLl = (LinearLayout) this.findViewById(R.id.paymode_detail);
        editText = (EditText) this.findViewById(R.id.user_amout_et);
        paymodeDetailTv = (TextView) this.findViewById(R.id.paymode_detail_tv);
        sutitleLayout.setSelectMode(true);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        sutitleLayout.setOnClickListener(l);
    }
}
