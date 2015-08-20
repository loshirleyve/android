package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.HistoryPayInfo;
import com.yun9.wservice.model.Payinfo;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentOrderRemainActivity extends JupiterFragmentActivity {

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_order_info)
    private PaymentOrderInfoWidget paymentOrderInfoWidget;

    @ViewInject(id=R.id.payment_payway)
    private JupiterRowStyleSutitleLayout paymentPayWay;

    @ViewInject(id=R.id.payment_balance)
    private JupiterRowStyleSutitleLayout paymentBalance;

    @ViewInject(id=R.id.go_to_pay_ib)
    private ImageButton payIb;

    @ViewInject(id=R.id.use_balance)
    private TextView useBalanceTv;

    @ViewInject(id=R.id.total_amount)
    private TextView totalAmountTv;

    @ViewInject(id=R.id.remain_balance)
    private TextView remainBalance;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private HistoryPayInfo payInfo;

    private PaymentOrderCommand command;

    public static void start(Activity activity,PaymentOrderCommand command) {
        Intent intent =  new Intent(activity,PaymentOrderRemainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (PaymentOrderCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
        loadData();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentOrderRemainActivity.this.finish();
            }
        });

        payIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayRegisterBySourceService");
        resource.param("source",command.getSource());
        resource.param("sourceValue",command.getSourceValue());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                payInfo = (HistoryPayInfo) response.getPayload();
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_order_remain;
    }
}
