package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.PayModeTypeCode;
import com.yun9.wservice.manager.AlipayManager;
import com.yun9.wservice.manager.WeChatManager;
import com.yun9.wservice.model.PayModeType;
import com.yun9.wservice.model.PayRegisterCollect;
import com.yun9.wservice.wxapi.WXPayEntryActivity;

import java.util.List;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentByOnlineActivity extends JupiterFragmentActivity {

    public static final String CODE_RECHARGE = "0001";

    public static final String CODE_PAY = "0002";

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.payment_ways_lv)
    private ListView listView;

    @ViewInject(id = R.id.payment_amount)
    private JupiterRowStyleTitleLayout paymentAmount;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private AlipayManager alipayManager;

    @BeanInject
    private WeChatManager weChatManager;

    private PaymentByOnlineCommand paymentByOnlineCommand;

    private List<PayModeType> payModeTypes;

    private IWXAPI iwxapi;

    private ProgressDialog wechatDialog;

    public static void start(Activity activity, PaymentByOnlineCommand command) {
        Intent intent = new Intent(activity, PaymentByOnlineActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaymentByOnlineCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentByOnlineCommand = (PaymentByOnlineCommand) this.getIntent()
                .getSerializableExtra(PaymentByOnlineCommand.PARAM_COMMAND);
        iwxapi = WXAPIFactory.createWXAPI(this, WeChatManager.APP_ID);
        buildView();
        loadData();
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayModeTypeService");
        resource.param("instid", paymentByOnlineCommand.getInstId());
        resource.param("paymodetype", "online");
        resource.param("type", "3rdparty");
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                payModeTypes = (List<PayModeType>) response.getPayload();
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                adapter.notifyDataSetChanged();
                registerDialog.dismiss();
            }
        });
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentByOnlineActivity.this.finish();
            }
        });
        listView.setAdapter(adapter);
        paymentAmount.getHotNitoceTV().setVisibility(View.VISIBLE);
        paymentAmount.getHotNitoceTV().setTextColor(getResources().getColor(R.color.title_color));
        paymentAmount.getHotNitoceTV().setText(paymentByOnlineCommand.getAmount() + "元");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_by_online;
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (payModeTypes != null) {
                return payModeTypes.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            JupiterRowStyleSutitleLayout sutitleLayout;
            final PayModeType payMode = payModeTypes.get(position);
            if (convertView == null) {
                sutitleLayout = new JupiterRowStyleSutitleLayout(PaymentByOnlineActivity.this);
                sutitleLayout.getArrowRightIV().setVisibility(View.GONE);
                sutitleLayout.getTitleTV().setText(payMode.getName());
                sutitleLayout.getSutitleTv().setText(payMode.getDescr());
                sutitleLayout.getTimeTv().setVisibility(View.GONE);
                ImageLoaderUtil.getInstance(mContext).displayImage(payMode.getImgid(), sutitleLayout.getMainIV());
                sutitleLayout.setTag(payMode);
                sutitleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payBy(payMode);
                    }
                });
                convertView = sutitleLayout;
            } else {
                sutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }
            return convertView;
        }
    };

    private void payBy(final PayModeType payMode) {

        if (PayModeTypeCode.CODE_WX.equals(payMode.getCode())) {
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
        Resource resource = resourceFactory.create("UpdateByCollectService");
        resource.param("payRegisterId", paymentByOnlineCommand.getPayRegisterId());
        resource.param("source", paymentByOnlineCommand.getSource());
        resource.param("sourceid", paymentByOnlineCommand.getSourceid());
        resource.param("type", "lock");
        resource.param("payTypeCode", payMode.getCode());
        resource.param("amount", paymentByOnlineCommand.getAmount());
        resource.param("createby", sessionManager.getUser().getId());
        resource.param("collectuserid", paymentByOnlineCommand.getCreateBy());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                registerDialog.dismiss();
                PayRegisterCollect result = (PayRegisterCollect) response.getPayload();
                if (PayModeTypeCode.CODE_ALIPAY.equals(payMode.getCode())) {
                    payByAlipay(result);
                } else if (PayModeTypeCode.CODE_WX.equals(payMode.getCode())) {
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
    private void payByWeChat(final PayRegisterCollect result) {
        iwxapi.registerApp(WeChatManager.APP_ID);
        wechatDialog = ProgressDialog.show(this, null, "微信支付中，请稍候...", true);
        WeChatManager.OrderInfo orderInfo = new WeChatManager.OrderInfo("微信支付", "移办通订单支付",
                result.getId() + "_" + CODE_PAY, result.getAmount() + "");
        weChatManager.pay(PaymentByOnlineActivity.this, orderInfo, new WeChatManager.HttpResponseCallback() {
            @Override
            public void onSuccess(byte[] bytes) {
                PayReq req = weChatManager.getReq(PaymentByOnlineActivity.this, bytes);
                if (req != null) {
                    boolean hasApp = iwxapi.sendReq(req);
                    WXPayEntryActivity.setWeChatCallback(new WXPayEntryActivity.WeChatCallback() {
                        @Override
                        public void onResp(BaseResp resp) {
                            PaymentByOnlineActivity.this.onResp(resp);
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
    private void payByAlipay(final PayRegisterCollect result) {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "支付中，请稍候...", true);
        AlipayManager.OrderInfo orderInfo =
                new AlipayManager.OrderInfo("支付宝支付", sessionManager.getUser().getName()
                        + "于" + DateUtil.getStringToday() + "支付" + result.getAmount() + "元",
                        result.getId() + "_" + CODE_PAY, result.getAmount() + "");
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
                            PaymentByOnlineActivity.this.finish();
                            PaymentResultCommand resultCommand = new PaymentResultCommand();
                            resultCommand.setInstId(paymentByOnlineCommand.getInstId());
                            resultCommand.setSource(paymentByOnlineCommand.getSource());
                            resultCommand.setSourceId(paymentByOnlineCommand.getSourceid());
                            resultCommand.setPaymentDone(true);
                            resultCommand.setCreateBy(paymentByOnlineCommand.getCreateBy());
                            PaymentResultActivity.start(PaymentByOnlineActivity.this, resultCommand);
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
                            closeThis();
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

    private void onResp(BaseResp baseResp) {
        if (wechatDialog != null && wechatDialog.isShowing()) {
            wechatDialog.dismiss();
            wechatDialog = null;
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                setResult(JupiterCommand.RESULT_CODE_OK);
                showToast("支付成功");
                PaymentByOnlineActivity.this.finish();
                PaymentResultCommand resultCommand = new PaymentResultCommand();
                resultCommand.setInstId(paymentByOnlineCommand.getInstId());
                resultCommand.setSource(paymentByOnlineCommand.getSource());
                resultCommand.setSourceId(paymentByOnlineCommand.getSourceid());
                resultCommand.setPaymentDone(true);
                resultCommand.setCreateBy(paymentByOnlineCommand.getCreateBy());
                PaymentResultActivity.start(PaymentByOnlineActivity.this, resultCommand);
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
        PaymentByOnlineActivity.this.finish();
        PaymentOrderCommand command = new PaymentOrderCommand();
        command.setSource(paymentByOnlineCommand.getSource());
        command.setSourceValue(paymentByOnlineCommand.getSourceid());
        command.setInstId(paymentByOnlineCommand.getInstId());
        PaymentOrderRemainActivity.start(PaymentByOnlineActivity.this, command);
    }
}
