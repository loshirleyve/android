package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.yun9.wservice.enums.PayModeType;
import com.yun9.wservice.enums.PayRegisterCollectState;
import com.yun9.wservice.model.HistoryPayInfo;
import com.yun9.wservice.model.PayRegisterCollect;
import com.yun9.wservice.view.common.LeftRightTextWidget;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentOrderRemainActivity extends JupiterFragmentActivity {

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_payway)
    private JupiterRowStyleSutitleLayout paymentPayWay;

    @ViewInject(id=R.id.go_to_pay_ib)
    private ImageButton payIb;

    @ViewInject(id=R.id.remain_balance)
    private TextView remainBalance;

    @ViewInject(id=R.id.items_ll)
    private LinearLayout itemsLl;

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
                payNow();
            }
        });
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayRegisterBySourceService");
        resource.param("source", command.getSource());
        resource.param("sourceid", command.getSourceValue());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                payInfo = (HistoryPayInfo) response.getPayload();
                buildWithData();
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

    private void buildWithData() {
        if (payInfo == null){
            return;
        }
        paymentPayWay.getHotNitoceTV().setVisibility(View.VISIBLE);
        paymentPayWay.getHotNitoceTV().setTextColor(getResources().getColor(R.color.gray_font));
        paymentPayWay.getHotNitoceTV().setText(payInfo.getPaymodeName());
        remainBalance.setText(payInfo.getUnPayamount() + "元");
        if (payInfo.getPayRegisterCollects() != null){
            itemsLl.addView(createItem("支付总额",payInfo.getAmount()+"元"));
            String name = null;
            String amount  = null;
            Double sum = 0.0;
            for (PayRegisterCollect collect : payInfo.getPayRegisterCollects()){
                name = "-" +collect.getPtname();
                if (PayRegisterCollectState.LOCK.equals(collect.getState())){
                    name += "(等待确认)";
                }
                sum += collect.getAmount();
                amount = collect.getAmount()+"";
                itemsLl.addView(createItem(name,amount));
            }
        }
    }

    private View createItem(String name,String amount) {
        LeftRightTextWidget widget = new LeftRightTextWidget(this);
        widget.getLeftTv().setText(name);
        widget.getRightTv().setText(amount);
        return widget;
    }

    private void payNow() {
        if (PayModeType.TYPE_ONLINE.equals(payInfo.getPaymodeType())){
            PaymentByOnlineCommand onlineCommand = new PaymentByOnlineCommand();
            onlineCommand.setPayRegisterId(payInfo.getId());
            onlineCommand.setSource(command.getSource());
            onlineCommand.setSourceid(command.getSourceValue());
            onlineCommand.setInstId(command.getInstId());
            onlineCommand.setAmount(payInfo.getUnPayamount());
            onlineCommand.setCreateBy(payInfo.getCreateby());
            PaymentOrderRemainActivity.this.finish();
            PaymentByOnlineActivity.start(PaymentOrderRemainActivity.this, onlineCommand);
        } else {
            PaymentResultCommand resultCommand = new PaymentResultCommand();
            resultCommand.setInstId(command.getInstId());
            resultCommand.setSource(command.getSource());
            resultCommand.setSourceId(command.getSourceValue());
            resultCommand.setPaymentDone(true);
            resultCommand.setCreateBy(payInfo.getCreateby());
            PaymentOrderRemainActivity.this.finish();
            PaymentResultActivity.start(PaymentOrderRemainActivity.this,resultCommand);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_order_remain;
    }
}
