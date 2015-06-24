package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

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

            }
        });
    }
}
