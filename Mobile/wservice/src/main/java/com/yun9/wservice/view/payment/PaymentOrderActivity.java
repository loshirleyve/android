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
import com.yun9.wservice.model.PayMode;
import com.yun9.wservice.model.Payinfo;
import com.yun9.wservice.view.common.InputTextActivity;
import com.yun9.wservice.view.common.InputTextCommand;

/**
 * 立即支付订单界面
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentOrderActivity extends JupiterFragmentActivity{

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

    private PaymentOrderCommand command;

    private PaymentOrderChoicePayWayCommand paymentOrderChoicePayWayCommand;

    private InputTextCommand inputTextCommand;

    private Payinfo payinfo;

    private PayMode usePayMode;

    private Double useBalance;

    public static void start(Activity activity,PaymentOrderCommand command) {
        Intent intent =  new Intent(activity,PaymentOrderActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_order;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentOrderActivity.this.finish();
            }
        });

        payIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        paymentBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputTextCommand == null){
                    inputTextCommand = new InputTextCommand();
                    inputTextCommand.addRegular(".+","请输入金额");
                }
                inputTextCommand.setValue(useBalance+"");
                InputTextActivity.start(PaymentOrderActivity.this,inputTextCommand);
            }
        });

        paymentPayWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentOrderChoicePayWayCommand == null){
                    paymentOrderChoicePayWayCommand = new PaymentOrderChoicePayWayCommand();
                    paymentOrderChoicePayWayCommand.setPayModes(payinfo.getPaymodes());
                }
                paymentOrderChoicePayWayCommand.setPayMode(usePayMode);
                PaymentOrderChoicePayWayActivity.start(PaymentOrderActivity.this,paymentOrderChoicePayWayCommand);
            }
        });
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayinfoBySourceService");
        resource.param("source",command.getSource());
        resource.param("sourceValue", command.getSourceValue());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                payinfo = (Payinfo) response.getPayload();
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
        if (payinfo == null){
            return;
        }
        useBalance = payinfo.getUseBalance();
        usePayMode = payinfo.getPayMode();
        paymentOrderInfoWidget.getPaymentTitleTv().setText(payinfo.getTitle());
        paymentOrderInfoWidget.getPaymentSubTitleTv().setText(payinfo.getSubtitle());
        paymentPayWay.getHotNitoceTV().setVisibility(View.VISIBLE);
        paymentPayWay.getHotNitoceTV().setTextColor(getResources().getColor(R.color.gray_font));
        if (payinfo.getPayMode() != null){
            paymentPayWay.getHotNitoceTV().setText(payinfo.getPayMode().getName());
        } else {
            paymentPayWay.getHotNitoceTV().setText("为选择");
        }
        if (payinfo.getBalance() != null){
            paymentBalance.getSutitleTv().setVisibility(View.VISIBLE);
            paymentBalance.getHotNitoceTV().setVisibility(View.VISIBLE);
            paymentBalance.getSutitleTv().setText(payinfo.getBalance().getBalance() + "元");
            paymentBalance.getHotNitoceTV().setText(payinfo.getUseBalance()+"元");
        } else {
            paymentBalance.getSutitleTv().setVisibility(View.GONE);
            paymentBalance.getHotNitoceTV().setVisibility(View.GONE);

        }
        totalAmountTv.setText(payinfo.getPayableAmount()+"元");
        useBalanceTv.setText(payinfo.getUseBalance()+"元");
        remainBalance.setText(payinfo.getUnpayAmount()+"元");
    }
}
