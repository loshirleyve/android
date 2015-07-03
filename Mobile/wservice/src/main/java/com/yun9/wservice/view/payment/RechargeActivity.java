package com.yun9.wservice.view.payment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.yun9.wservice.manager.AlipayManager;
import com.yun9.wservice.model.AddRechargeResult;
import com.yun9.wservice.model.Payinfo;
import com.yun9.wservice.model.RechargeNo;
import com.yun9.wservice.model.RechargeType;

/**
 * 充值界面
 * Created by huangbinglong on 15/6/23.
 */
public class RechargeActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.title_layout)
    private JupiterRowStyleTitleLayout titleLayout;

    @ViewInject(id=R.id.recharge_money_et)
    private EditText editText;

    @ViewInject(id=R.id.confirm_recharge_ll)
    private LinearLayout rechargeLL;

    @ViewInject(id=R.id.choosed_recharge_type_ll)
    private LinearLayout choosedRechargeTypeLL;

    @ViewInject(id=R.id.recharge_way_tip_tv)
    private TextView rechargeTypeTipTV;

    @ViewInject(id=R.id.recharge_way_desc_tv)
    private TextView rechargeTypeDescTV;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private AlipayManager alipayManager;

    private RechargeChoiceWaysCommand command;

    private RechargeType rechargeType;

    public static void start(Context context) {
        Intent intent = new Intent(context,RechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        rechargeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRecharge();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (command !=null && command.getRequestCode() == requestCode
                && resultCode == command.RESULT_CODE_OK){
            RechargeType type = (RechargeType) data.getSerializableExtra("type");
            if (type != null) {
                showDesc(type);
            } else {
                hideDesc();
            }
        }
    }

    private void confirmRecharge() {
        String content = editText.getText().toString();
        if (!AssertValue.isNotNullAndNotEmpty(content)){
            showToast("请输入充值金额。");
            return;
        }
        if (rechargeType == null){
            showToast("请选择充值方式。");
            return;
        }

        addRechargeRecord(content);

    }

    private void addRechargeRecord(String amount) {
        if (RechargeNo.TYPE_WEIXIN.equals(rechargeType.getRechargeno())) {
            showToast("功能正在研发...");
            return;
        }
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "登记中，请稍候...", true);
        Resource resource = resourceFactory.create("AddRechargeService");
        resource.param("own",sessionManager.getUser().getId());
        resource.param("accounttype", Payinfo.BizFinanceAccount.TYPE_BALANCE);
        resource.param("amount",amount);
        resource.param("typeid",rechargeType.getId());
        resource.param("userid",sessionManager.getUser().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                registerDialog.dismiss();
                AddRechargeResult result = (AddRechargeResult) response.getPayload();
                if (RechargeNo.TYPE_ALIPAY.equals(rechargeType.getRechargeno())){
                    payByAlipay(result);
                } else if (RechargeNo.TYPE_WEIXIN.equals(rechargeType.getRechargeno())) {
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
     * 使用支付宝支付
     * @param result
     */
    private void payByAlipay(final AddRechargeResult result) {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "支付中，请稍候...", true);
        AlipayManager.OrderInfo orderInfo =
                new AlipayManager.OrderInfo("余额充值",sessionManager.getUser().getName()
                        +"于"+ DateUtil.getStringToday()+"充值"+result.getAmount()+"元",result.getCallbackid(),result.getAmount());
       Handler handler = new Handler() {
            public void handleMessage(Message msg) {
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
                            result.setStateName("支付成功");
                            RechargeResultActivity.start(RechargeActivity.this,result);
                            RechargeActivity.this.finish();
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                result.setStateName("支付结果确认中");
                                RechargeResultActivity.start(RechargeActivity.this,result);
                                RechargeActivity.this.finish();
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                showToast("支付失败\n" + memo);
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
        alipayManager.pay(this,orderInfo,handler);
    }

    private void showDesc(RechargeType type) {
        rechargeType = type;
        choosedRechargeTypeLL.setVisibility(View.VISIBLE);
        rechargeTypeTipTV.setText("将使用 " + type.getRechargename() + " 方式付款");
        rechargeTypeDescTV.setText(type.getWarmthwarning());
        titleLayout.getHotNitoceTV().setText(type.getRechargename());
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));

    }

    private void hideDesc() {
        choosedRechargeTypeLL.setVisibility(View.GONE);
        titleLayout.getHotNitoceTV().setText(R.string.payment_ways_hint);
        titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.red));
    }
}
