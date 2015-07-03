package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 7/3/15.
 */
public class PaymentResultActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context,PaymentResultActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildView();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentResultActivity.this.finish();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_result;
    }
}
