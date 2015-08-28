package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.cache.InstCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.listener.OnClickWithNetworkListener;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheInst;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.PayModeType;
import com.yun9.wservice.model.HistoryPayInfo;
import com.yun9.wservice.model.PayMode;
import com.yun9.wservice.model.Payinfo;
import com.yun9.wservice.view.common.InputTextCommand;
import com.yun9.wservice.view.order.OrderDetailActivity;

import java.math.BigDecimal;

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

    private Double unPayAmount;

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
        if (resultCode != JupiterCommand.RESULT_CODE_OK){
            return;
        }
        if (inputTextCommand != null && inputTextCommand.getRequestCode() == requestCode){
            useBalance = Double.valueOf(data.getStringExtra(JupiterCommand.RESULT_PARAM));
            BigDecimal bigDecimal = (new BigDecimal(payinfo.getPayableAmount()))
                    .subtract(new BigDecimal(useBalance));
            unPayAmount = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            refreshUnpayAmount();
            refreshUseBalance();
        } else if (paymentOrderChoicePayWayCommand != null
                && paymentOrderChoicePayWayCommand.getRequestCode() == requestCode) {
            usePayMode = (PayMode) data.getSerializableExtra(JupiterCommand.RESULT_PARAM);
            refreshUsePayMode();
        }
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new OnClickWithNetworkListener() {
            @Override
            public void onClickWithNetwork(View v) {
                PaymentOrderActivity.this.finish();
            }
        });

        payIb.setOnClickListener(new OnClickWithNetworkListener() {
            @Override
            public void onClickWithNetwork(View v) {
                registerPay();
            }
        });

        paymentBalance.setOnClickListener(new OnClickWithNetworkListener() {
            @Override
            public void onClickWithNetwork(View v) {
                if (inputTextCommand == null) {
                    inputTextCommand = new InputTextCommand();
                    inputTextCommand.setTitle("请输入金额");
                    inputTextCommand.setTip("请输入金额:");
                    inputTextCommand.addRegular(".+", "请输入金额");
                    inputTextCommand
                            .addRegular("^(([0-9]+\\.[0-9]*[0-9][0-9]*)|" +
                                            "([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)|(0+))$",
                                    "输入非有效金额，请重输");
                    inputTextCommand.setMinValue(0.0);
                    double maxValue = payinfo.getPayableAmount()>payinfo.getBalance().getBalance()
                            ?payinfo.getBalance().getBalance():payinfo.getPayableAmount();
                    inputTextCommand.setMaxValue(maxValue);
                }
                inputTextCommand.setValue(useBalance + "");
                PaymentInputAmountActivity.start(PaymentOrderActivity.this, inputTextCommand);
            }
        });

        paymentPayWay.setOnClickListener(new OnClickWithNetworkListener() {
            @Override
            public void onClickWithNetwork(View v) {
                if (paymentOrderChoicePayWayCommand == null) {
                    paymentOrderChoicePayWayCommand = new PaymentOrderChoicePayWayCommand();
                    paymentOrderChoicePayWayCommand.setPayModes(payinfo.getPaymodes());
                }
                paymentOrderChoicePayWayCommand.setPayMode(usePayMode);
                PaymentOrderChoicePayWayActivity.start(PaymentOrderActivity.this, paymentOrderChoicePayWayCommand);
            }
        });
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayinfoBySourceService");
        resource.param("source", command.getSource());
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
        refreshUseBalance();
        usePayMode = payinfo.getPayMode();
        unPayAmount = payinfo.getUnpayAmount();
        paymentOrderInfoWidget.getPaymentTitleTv().setText(payinfo.getTitle());
        paymentOrderInfoWidget.getPaymentSubTitleTv().setText(payinfo.getSubtitle());
        paymentPayWay.getHotNitoceTV().setVisibility(View.VISIBLE);
        paymentPayWay.getHotNitoceTV().setTextColor(getResources().getColor(R.color.gray_font));
        refreshUsePayMode();
        paymentBalance.getSutitleTv().setVisibility(View.VISIBLE);
        paymentBalance.getHotNitoceTV().setVisibility(View.VISIBLE);
        paymentBalance.getHotNitoceTV().setTextColor(getResources().getColor(R.color.gray_font));
        paymentBalance.getSutitleTv().setTextColor(getResources().getColor(R.color.title_color));
        if (payinfo.getBalance() != null){
            paymentBalance.getSutitleTv().setText(payinfo.getBalance().getBalance() + "元");
        } else {
            paymentBalance.getSutitleTv().setText("0元");
            paymentBalance.setEnabled(false);
        }
        totalAmountTv.setText(payinfo.getPayableAmount() + "元");
        refreshUnpayAmount();
        paymentOrderInfoWidget.getSourceSnTv().setText(payinfo.getSourceSn());
        CacheInst cacheInst = InstCache.getInstance().getInst(payinfo.getInstId());
        if (cacheInst != null){
            paymentOrderInfoWidget.getPaymentInstNameTv().setText(cacheInst.getInstname());
        }
    }

    private void refreshUseBalance() {
        if (useBalance != null){
            paymentBalance.getHotNitoceTV().setText("已使用 "+useBalance+"元");
            useBalanceTv.setText(useBalance + "元");
        } else {
            paymentBalance.getHotNitoceTV().setText("已使用 0元");
            useBalanceTv.setText("0元");
        }
    }

    private void refreshUsePayMode() {
        if (usePayMode != null){
            paymentPayWay.getHotNitoceTV().setText(usePayMode.getName());
        } else {
            paymentPayWay.getHotNitoceTV().setText("未选择");
        }
    }

    private void refreshUnpayAmount() {
        remainBalance.setText(unPayAmount+"元");
    }

    private void registerPay() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("AddPayRegisterBySoruceService");
        resource.param("source", command.getSource());
        resource.param("sourceId", command.getSourceValue());
        resource.param("instId", command.getInstId());
        resource.param("businessKey", payinfo.getSubtitle());
        resource.param("sourceAmount", payinfo.getPayableAmount());
        resource.param("useBalance", useBalance);
        if (usePayMode != null){
            resource.param("paymodeCode", usePayMode.getCode());
        }
        resource.param("createBy", payinfo.getCreateBy());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                HistoryPayInfo historyPayInfo = (HistoryPayInfo) response.getPayload();
                if (PayModeType.TYPE_ONLINE.equals(historyPayInfo.getPaymodeType())
                        && unPayAmount > 0.0){
                    PaymentByOnlineCommand onlineCommand = new PaymentByOnlineCommand();
                    onlineCommand.setPayRegisterId(historyPayInfo.getId());
                    onlineCommand.setSource(command.getSource());
                    onlineCommand.setSourceid(command.getSourceValue());
                    onlineCommand.setInstId(command.getInstId());
                    onlineCommand.setAmount(historyPayInfo.getUnPayamount());
                    onlineCommand.setCreateBy(payinfo.getCreateBy());
                    PaymentOrderActivity.this.finish();
                    PaymentByOnlineActivity.start(PaymentOrderActivity.this, onlineCommand);
                } else if (historyPayInfo.getComplete() == 0) {
                    PaymentOrderActivity.this.finish();
                    PaymentOrderRemainActivity.start(PaymentOrderActivity.this, command);
                } else{
                    PaymentResultCommand resultCommand = new PaymentResultCommand();
                    resultCommand.setInstId(command.getInstId());
                    resultCommand.setSource(command.getSource());
                    resultCommand.setSourceId(command.getSourceValue());
                    resultCommand.setPaymentDone(true);
                    resultCommand.setCreateBy(payinfo.getCreateBy());
                    PaymentOrderActivity.this.finish();
                    PaymentResultActivity.start(PaymentOrderActivity.this,resultCommand);
                }
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                AppCache.getInstance().put(OrderDetailActivity.ORDER_DETAIL_NEES_REFRESH,true);
                registerDialog.dismiss();
            }
        });
    }
}
