package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.AddRechargeResult;

/**
 * Created by huangbinglong on 7/2/15.
 */
public class RechargeResultActivity extends JupiterFragmentActivity{

    public static final String PARAM_KEY_RESULT = "result";

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.state_tv)
    private TextView stateTv;

    @ViewInject(id=R.id.recharge_type_tv)
    private TextView rechargeTypeTv;

    @ViewInject(id=R.id.recharge_amount_tv)
    private TextView rechargeAmountTv;

    @ViewInject(id=R.id.recharge_id_tv)
    private TextView rechargeIdTv;

    public static void start(Context context,AddRechargeResult result) {
        Intent intent = new Intent(context,RechargeResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_KEY_RESULT,result);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddRechargeResult result = (AddRechargeResult) getIntent()
                                                            .getSerializableExtra(PARAM_KEY_RESULT);
        buildView(result);
    }

    private void buildView(AddRechargeResult result) {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeResultActivity.this.finish();
            }
        });
        stateTv.setText(result.getStateName());
        rechargeTypeTv.setText(result.getRecharegeTypeName());
        rechargeAmountTv.setText(result.getAmount()+"元");
        rechargeIdTv.setText(result.getRechargeid());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge_result;
    }
}
