package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.RechargeNo;
import com.yun9.wservice.manager.AlipayManager;
import com.yun9.wservice.manager.WeChatManager;
import com.yun9.wservice.model.AddRechargeResult;
import com.yun9.wservice.model.PayModeType;
import com.yun9.wservice.wxapi.WXPayEntryActivity;

/**
 * 充值界面
 * Created by huangbinglong on 15/6/23.
 */
public class RechargeActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.title_layout)
    private JupiterRowStyleTitleLayout titleLayout;

    @ViewInject(id = R.id.recharge_money_et)
    private EditText editText;

    @ViewInject(id = R.id.confirm_recharge_ll)
    private LinearLayout rechargeLL;

    @ViewInject(id = R.id.choosed_recharge_type_ll)
    private LinearLayout choosedRechargeTypeLL;

    @ViewInject(id = R.id.recharge_way_tip_tv)
    private TextView rechargeTypeTipTV;

    @ViewInject(id = R.id.recharge_way_desc_tv)
    private TextView rechargeTypeDescTV;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private AlipayManager alipayManager;

    @BeanInject
    private WeChatManager weChatManager;

    private RechargeChoiceWaysCommand command;

    private RechargeCommand rechargeCommand;

    private PayModeType rechargeType;

    private IWXAPI iwxapi;

    private ProgressDialog wechatDialog;

    public static void start(Activity activity, RechargeCommand command) {
        Intent intent = new Intent(activity, RechargeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rechargeCommand =
                (RechargeCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        iwxapi = WXAPIFactory.createWXAPI(this, WeChatManager.APP_ID);
        buildView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recharge;
    }

    private void buildView() {
        hideDesc();
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeActivity.this.finish();
            }
        });
        titleLayout.getHotNitoceTV().setBackgroundColor(getResources().getColor(R.color.transparent));
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.red));
        titleLayout.getHotNitoceTV().setText(R.string.payment_ways_hint);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command = new RechargeChoiceWaysCommand();
                command.setSelectedType(rechargeType);
                RechargeChoiceWaysActivity.start(RechargeActivity.this, command);
            }
        });

        if (rechargeCommand != null
                && rechargeCommand.getAmount() > 0.0){
            editText.setText(rechargeCommand.getAmount()+"");
        }

        rechargeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRecharge();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (command != null && command.getRequestCode() == requestCode
                && resultCode == command.RESULT_CODE_OK) {
            PayModeType type = (PayModeType) data.getSerializableExtra("type");
            if (type != null) {
                showDesc(type);
            } else {
                hideDesc();
            }
        }
    }

    private void confirmRecharge() {
        String content = editText.getText().toString();
        if (!AssertValue.isNotNullAndNotEmpty(content)) {
            showToast("请输入充值金额。");
            return;
        }
        if (rechargeType == null) {
            showToast("请选择充值方式。");
            return;
        }

        addRechargeRecord(content);

    }

    private void addRechargeRecord(String amount) {
        if (RechargeNo.TYPE_WEIXIN.equals(rechargeType.getCode())) {
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
        }
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "登记中，请稍候...", true);
        Resource resource = resourceFactory.create("AddRechargeService");
        resource.param("own", sessionManager.getUser().getId());
        resource.param("accounttype", "balance");
        resource.param("amount", amount);
        resource.param("typeid", rechargeType.getId());
        resource.param("userid", sessionManager.getUser().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                registerDialog.dismiss();
                AddRechargeResult result = (AddRechargeResult) response.getPayload();
                if (RechargeNo.TYPE_ALIPAY.equals(rechargeType.getCode())) {
                    payByAlipay(result);
                } else if (RechargeNo.TYPE_WEIXIN.equals(rechargeType.getCode())) {
                    payByWeChat(result);
                }
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

    /**
     * 使用微信进行支付
     *
     * @param result 注册订单返回的结果信息
     */
    private void payByWeChat(final AddRechargeResult result) {
        iwxapi.registerApp(WeChatManager.APP_ID);
        wechatDialog = ProgressDialog.show(this, null, "微信支付中，请稍候...", true);
        WeChatManager.OrderInfo orderInfo = new WeChatManager.OrderInfo("余额充值", "移办通充值",
                result.getCallbackid(), result.getAmount() + "");
        weChatManager.pay(RechargeActivity.this, orderInfo, new WeChatManager.HttpResponseCallback() {
            @Override
            public void onSuccess(byte[] bytes) {
                PayReq req = weChatManager.getReq(RechargeActivity.this, bytes);
                if (req != null) {
                    boolean hasApp = iwxapi.sendReq(req);
                    WXPayEntryActivity.setWeChatCallback(new WXPayEntryActivity.WeChatCallback() {
                        @Override
                        public void onResp(BaseResp resp) {
                            RechargeActivity.this.onResp(result,resp);
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

    /**
     * 使用支付宝支付
     *
     * @param result
     */
    private void payByAlipay(final AddRechargeResult result) {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "支付中，请稍候...", true);
        AlipayManager.OrderInfo orderInfo =
                new AlipayManager.OrderInfo("余额充值", sessionManager.getUser().getName()
                        + "于" + DateUtil.getStringToday() + "充值" + result.getAmount() + "元",
                        result.getCallbackid(), result.getAmount() + "");
        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                RechargeResultCommand command =
                        new RechargeResultCommand(result.getRechargeid(), result.getStateName(),
                                result.getRecharegeTypeName(), result.getAmount());
                switch (msg.what) {
                    case AlipayManager.SDK_PAY_FLAG: {
                        AlipayManager.PayResult payResult = new AlipayManager
                                .PayResult((String) msg.obj);
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();
                        String memo = payResult.getMemo();
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            command.setStateName("支付成功");
                            RechargeResultActivity.start(RechargeActivity.this, command);
                            setResult(JupiterCommand.RESULT_CODE_OK);
                            RechargeActivity.this.finish();
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                command.setStateName("支付结果确认中");
                                RechargeResultActivity.start(RechargeActivity.this, command);
                                setResult(JupiterCommand.RESULT_CODE_CANCEL);
                                RechargeActivity.this.finish();
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                showToast("支付失败\n" + memo);
                                RechargeActivity.this.finish();
                                setResult(JupiterCommand.RESULT_CODE_CANCEL);
                                RechargeRecordListActivity.start(RechargeActivity.this, null, null);
                            }
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

    private void onResp(AddRechargeResult result,BaseResp baseResp) {
        if (wechatDialog != null && wechatDialog.isShowing()) {
            wechatDialog.dismiss();
            wechatDialog = null;
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            RechargeResultCommand command =
                    new RechargeResultCommand(result.getRechargeid(), result.getStateName(),
                            result.getRecharegeTypeName(), result.getAmount());
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                command.setStateName("支付成功");
                RechargeResultActivity.start(RechargeActivity.this, command);
                setResult(JupiterCommand.RESULT_CODE_OK);
                RechargeActivity.this.finish();
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
        RechargeActivity.this.finish();
        setResult(JupiterCommand.RESULT_CODE_CANCEL);
        RechargeRecordListActivity.start(RechargeActivity.this, null, null);
    }

    private void showDesc(PayModeType type) {
        rechargeType = type;
        choosedRechargeTypeLL.setVisibility(View.VISIBLE);
        rechargeTypeTipTV.setText("将使用 " + type.getName() + " 方式付款");
        rechargeTypeDescTV.setText(type.getDescr());
        titleLayout.getHotNitoceTV().setText(type.getDescr());
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));

    }

    private void hideDesc() {
        choosedRechargeTypeLL.setVisibility(View.GONE);
        titleLayout.getHotNitoceTV().setText(R.string.payment_ways_hint);
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.red));
    }
}
