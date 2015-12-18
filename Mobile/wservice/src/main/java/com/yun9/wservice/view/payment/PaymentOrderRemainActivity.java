package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.PayModeType;
import com.yun9.wservice.enums.PayModeTypeCode;
import com.yun9.wservice.enums.PayRegisterCollectState;
import com.yun9.wservice.manager.AlipayManager;
import com.yun9.wservice.manager.WeChatManager;
import com.yun9.wservice.model.HistoryPayInfo;
import com.yun9.wservice.model.PayRegisterCollect;
import com.yun9.wservice.view.common.LeftRightTextWidget;
import com.yun9.wservice.wxapi.WXPayEntryActivity;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentOrderRemainActivity extends JupiterFragmentActivity {

    public static final String CODE_RECHARGE = "0001";

    public static final String CODE_PAY = "0002";

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

    @ViewInject(id=R.id.bottom_operator_ll)
    private LinearLayout bottomOperatorLl;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private AlipayManager alipayManager;

    @BeanInject
    private WeChatManager weChatManager;

    private HistoryPayInfo payInfo;

    private PaymentOrderCommand command;

    private IWXAPI iwxapi;

    private ProgressDialog wechatDialog;

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
        iwxapi = WXAPIFactory.createWXAPI(this, WeChatManager.APP_ID);
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

        if (PayModeType.TYPE_OFFLINE.equals(payInfo.getPaymodeType())
                || payInfo.getUnPayamount() <=0.0){
            bottomOperatorLl.setVisibility(View.GONE);
        }

        paymentPayWay.getHotNitoceTV().setVisibility(View.VISIBLE);
        paymentPayWay.getHotNitoceTV().setTextColor(getResources().getColor(R.color.gray_font));
        paymentPayWay.getHotNitoceTV().setText(payInfo.getPaymodeName());
        remainBalance.setText(payInfo.getUnPayamount() + "元");
        if (payInfo.getPayRegisterCollects() != null){
            itemsLl.addView(createItem(new PayRegisterCollect().setPtname("支付总额").setAmount(payInfo.getAmount())));
            for (PayRegisterCollect collect : payInfo.getPayRegisterCollects()){
                itemsLl.addView(createItem(collect));
            }
        }
    }

    private View createItem(final PayRegisterCollect collect) {
        String name = "-" +collect.getPtname();
        String amount  = collect.getAmount()+"元";
        if (PayRegisterCollectState.LOCK.equals(collect.getState())){
            name += "(等待确认)";
        }
        LeftRightTextWidget widget = new LeftRightTextWidget(this);
        widget.getLeftTv().setText(name);
        widget.getRightTv().setText(amount);
        if (PayModeType.TYPE_ONLINE.equals(collect.getPaymodetype())
                && PayRegisterCollectState.LOCK.equals(collect.getState())){
            widget.getBottomOperatorRl().setVisibility(View.VISIBLE);
            widget.getRightBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payAgain(collect);
                }
            });
        }
        return widget;
    }

    private void payAgain(PayRegisterCollect collect) {
        if (PayModeTypeCode.CODE_WX.equals(collect.getPtcode())) {
            if(!iwxapi.isWXAppInstalled())
            {
                showToast("您没有安装微信！");
                return;
            }

            if(!iwxapi.isWXAppSupportAPI())
            {
                showToast("当前微信版本不支持支付功能");
                return;
            }
            payByWeChat(collect);
        } else if (PayModeTypeCode.CODE_ALIPAY.equals(collect.getPtcode())) {
            payByAlipay(collect);
        }

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
            PaymentResultActivity.start(PaymentOrderRemainActivity.this, resultCommand);
        }
    }

    /**
     * 使用支付宝支付
     *
     * @param result
     */
    private void payByAlipay(final PayRegisterCollect result) {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "支付中，请稍候...", true);
        AlipayManager.OrderInfo orderInfo =
                new AlipayManager.OrderInfo("支付宝支付", sessionManager.getUser().getName()
                        + "于" + DateUtil.getStringToday() + "支付" + result.getAmount() + "元",
                        result.getId()+"_"+CODE_PAY, result.getAmount() + "");
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case AlipayManager.SDK_PAY_FLAG: {
                        AlipayManager.PayResult payResult = new AlipayManager
                                .PayResult((String) msg.obj);
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String memo = payResult.getMemo();
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            setResult(JupiterCommand.RESULT_CODE_OK);
                            showToast("支付成功");
                            PaymentOrderRemainActivity.this.finish();
                            PaymentResultCommand resultCommand = new PaymentResultCommand();
                            resultCommand.setInstId(command.getInstId());
                            resultCommand.setSource(command.getSource());
                            resultCommand.setSourceId(command.getSourceValue());
                            resultCommand.setPaymentDone(true);
                            resultCommand.setCreateBy(payInfo.getCreateby());
                            PaymentResultActivity.start(PaymentOrderRemainActivity.this, resultCommand);
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                setResult(JupiterCommand.RESULT_CODE_OK);
                                showToast("支付结果正在确认");
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                showToast("支付失败\n" + memo);
                            }
                            PaymentOrderRemainActivity.this.finish();
                            PaymentOrderRemainActivity.start(PaymentOrderRemainActivity.this, command);
                        }
                        break;
                    }
                    default:
                        break;
                }
                registerDialog.dismiss();
            }
        };
        alipayManager.pay(this, orderInfo, handler);
    }

    /**
     * 使用微信进行支付
     *
     * @param result 注册订单返回的结果信息
     */
    private void payByWeChat(final PayRegisterCollect result) {
        iwxapi.registerApp(WeChatManager.APP_ID);
        wechatDialog = ProgressDialog.show(this, null, "微信支付中，请稍候...", true);
        WeChatManager.OrderInfo orderInfo = new WeChatManager.OrderInfo("微信支付", "移办通订单支付",
                result.getId() + "_" + CODE_PAY, result.getAmount() + "");
        weChatManager.pay(PaymentOrderRemainActivity.this, orderInfo, new WeChatManager.HttpResponseCallback() {
            @Override
            public void onSuccess(byte[] bytes) {
                PayReq req = weChatManager.getReq(PaymentOrderRemainActivity.this, bytes);
                if (req != null) {
                    boolean hasApp = iwxapi.sendReq(req);
                    WXPayEntryActivity.setWeChatCallback(new WXPayEntryActivity.WeChatCallback() {
                        @Override
                        public void onResp(BaseResp resp) {
                            PaymentOrderRemainActivity.this.onResp(resp);
                        }
                    });
                    if (!hasApp) {
                        wechatDialog.dismiss();
                        showToast("打开微信失败。");
                        closeThis();
                    }
                } else {
                    wechatDialog.dismiss();
                    showToast("微信支付下单失败！");
                    closeThis();
                }
            }

            @Override
            public void onFailure(byte[] bytes, Throwable throwable) {
                showToast("获取微信预付码错误:" + throwable.getMessage());
            }
        });
    }

    private void onResp(BaseResp baseResp) {
        if (wechatDialog != null && wechatDialog.isShowing()) {
            wechatDialog.dismiss();
            wechatDialog = null;
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                setResult(JupiterCommand.RESULT_CODE_OK);
                showToast("支付成功");
                PaymentOrderRemainActivity.this.finish();
                PaymentResultCommand resultCommand = new PaymentResultCommand();
                resultCommand.setInstId(command.getInstId());
                resultCommand.setSource(command.getSource());
                resultCommand.setSourceId(command.getSourceValue());
                resultCommand.setPaymentDone(true);
                resultCommand.setCreateBy(payInfo.getCreateby());
                PaymentResultActivity.start(PaymentOrderRemainActivity.this, resultCommand);
                return;
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_COMM) {
                showToast("支付失败\n" + baseResp.errStr);
            } else if (baseResp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                showToast("用户取消操作。");
            }
            closeThis();
        }
    }

    private void closeThis() {
        PaymentOrderRemainActivity.this.finish();
        PaymentOrderRemainActivity.start(PaymentOrderRemainActivity.this, command);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_order_remain;
    }
}
