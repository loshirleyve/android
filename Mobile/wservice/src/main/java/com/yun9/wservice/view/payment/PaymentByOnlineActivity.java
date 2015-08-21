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
import com.yun9.wservice.enums.RechargeNo;
import com.yun9.wservice.manager.AlipayManager;
import com.yun9.wservice.model.AddRechargeResult;
import com.yun9.wservice.model.PayMode;
import com.yun9.wservice.model.PayModeType;
import com.yun9.wservice.model.PayRegisterCollect;

import java.util.List;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentByOnlineActivity  extends JupiterFragmentActivity{

    public static final String CODE_RECHARGE = "0001";

    public static final String CODE_PAY = "0002";

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_ways_lv)
    private ListView listView;

    @ViewInject(id=R.id.payment_amount)
    private JupiterRowStyleTitleLayout paymentAmount;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private AlipayManager alipayManager;

    private PaymentByOnlineCommand paymentByOnlineCommand;

    private List<PayModeType> payModeTypes;

    public static void start(Activity activity,PaymentByOnlineCommand command) {
        Intent intent = new Intent(activity,PaymentByOnlineActivity.class);
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
        buildView();
        loadData();
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayModeTypeService");
        resource.param("instid",paymentByOnlineCommand.getInstId());
        resource.param("paymodetype","online");
        resource.param("type","3rdparty");
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
                sutitleLayout.setSelectMode(true);
                sutitleLayout.getArrowRightIV().setVisibility(View.GONE);
                sutitleLayout.getTitleTV().setText(payMode.getName());
                sutitleLayout.getSutitleTv().setText(payMode.getDescr());
                sutitleLayout.getTimeTv().setVisibility(View.GONE);
                ImageLoaderUtil.getInstance(mContext).displayImage(payMode.getImgid(),sutitleLayout.getMainIV());
                sutitleLayout.setTag(payMode);
                sutitleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payBy(payMode);
                    }
                });
                convertView = sutitleLayout;
            }else {
                sutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }
            return convertView;
        }
    };

    private void payBy(final PayModeType payMode) {
        if (PayModeTypeCode.CODE_WX.equals(payMode.getCode())) {
            showToast("功能正在研发...");
            return;
        }
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, "登记中，请稍候...", true);
        Resource resource = resourceFactory.create("UpdateByCollectService");
        resource.param("payRegisterId",paymentByOnlineCommand.getPayRegisterId());
        resource.param("source",paymentByOnlineCommand.getSource());
        resource.param("sourceid",paymentByOnlineCommand.getSourceid());
        resource.param("type","lock");
        resource.param("payTypeCode","balance");
        resource.param("amount",paymentByOnlineCommand.getAmount());
        resource.param("createby",sessionManager.getUser().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                registerDialog.dismiss();
                PayRegisterCollect result = (PayRegisterCollect) response.getPayload();
                if (PayModeTypeCode.CODE_ALIPAY.equals(payMode.getCode())) {
                    payByAlipay(result);
                } else if (PayModeTypeCode.CODE_WX.equals(payMode.getCode())) {
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
                        String resultInfo = payResult.getResult();
                        String memo = payResult.getMemo();
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            setResult(JupiterCommand.RESULT_CODE_OK);
                            showToast("支付成功");
                            PaymentByOnlineActivity.this.finish();
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                setResult(JupiterCommand.RESULT_CODE_OK);
                                showToast("支付结果正在确认");
                                PaymentByOnlineActivity.this.finish();
                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                showToast("支付失败\n" + memo);
                                PaymentByOnlineActivity.this.finish();
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
}
