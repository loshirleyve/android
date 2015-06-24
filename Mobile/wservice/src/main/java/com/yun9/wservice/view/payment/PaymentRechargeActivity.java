package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.RechargeType;

/**
 * Created by huangbinglong on 15/6/23.
 */
public class PaymentRechargeActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.title_layout)
    private JupiterRowStyleTitleLayout titleLayout;

    @ViewInject(id=R.id.recharge_money_et)
    private EditText editText;

    @ViewInject(id=R.id.confirm_recharge_ll)
    private LinearLayout rechargeLL;

    @ViewInject(id=R.id.choosed_recharge_type_ll)
    private LinearLayout choosedRechargeTypeLL;

    @ViewInject(id=R.id.recharge_way_tip_tv)
    private TextView rechargeTypeTipTV;

    @ViewInject(id=R.id.recharge_way_desc_tv)
    private TextView rechargeTypeDescTV;

    private PaymentChoiceWaysCommand command;

    private RechargeType rechargeType;

    public static void start(Context context) {
        Intent intent = new Intent(context,PaymentRechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_recharge;
    }

    private void buildView() {
        hideDesc();
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentRechargeActivity.this.finish();
            }
        });
        titleLayout.getHotNitoceTV().setBackgroundColor(getResources().getColor(R.color.transparent));
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.red));
        titleLayout.getHotNitoceTV().setText(R.string.payment_ways_hint);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command = new PaymentChoiceWaysCommand();
                command.setSelectedType(rechargeType);
                PaymentChoiceWaysActivity.start(PaymentRechargeActivity.this,command);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (command !=null && command.getRequestCode() == requestCode
                && resultCode == command.RESULT_CODE_OK){
            RechargeType type = (RechargeType) data.getSerializableExtra("type");
            if (type != null) {
                showDesc(type);
            } else {
                hideDesc();
            }
        }
    }

    private void showDesc(RechargeType type) {
        rechargeType = type;
        choosedRechargeTypeLL.setVisibility(View.VISIBLE);
        rechargeTypeTipTV.setText("将使用 " + type.getRechargename() + " 方式付款");
        rechargeTypeDescTV.setText(type.getWarmthwarning());
        titleLayout.getHotNitoceTV().setText(type.getRechargename());
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));

    }

    private void hideDesc() {
        choosedRechargeTypeLL.setVisibility(View.GONE);
        titleLayout.getHotNitoceTV().setText(R.string.payment_ways_hint);
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.red));
    }
}
