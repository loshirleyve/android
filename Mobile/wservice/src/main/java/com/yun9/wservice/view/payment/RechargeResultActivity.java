package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.AddRechargeResult;

/**
 * Created by huangbinglong on 7/2/15.
 */
public class RechargeResultActivity extends JupiterFragmentActivity{

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

    public static void start(Context context,RechargeResultCommand command) {
        Intent intent = new Intent(context,RechargeResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RechargeResultCommand command = (RechargeResultCommand) getIntent()
                .getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView(command);
    }

    private void buildView(RechargeResultCommand command) {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeResultActivity.this.finish();
            }
        });
        stateTv.setText(command.getStateName());
        rechargeTypeTv.setText(command.getRechargeTypeName());
        rechargeAmountTv.setText(command.getAmount()+"元");
        rechargeIdTv.setText(command.getId());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge_result;
    }
}
