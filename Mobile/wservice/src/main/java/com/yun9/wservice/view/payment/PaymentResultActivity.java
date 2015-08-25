package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.PayModeType;
import com.yun9.wservice.enums.PayRegisterCollectState;
import com.yun9.wservice.model.HistoryPayInfo;
import com.yun9.wservice.model.PayRegisterCollect;
import com.yun9.wservice.view.common.LeftRightTextWidget;
import com.yun9.wservice.view.order.OrderDetailActivity;

/**
 * Created by huangbinglong on 7/3/15.
 */
public class PaymentResultActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.state_name_tv)
    private TextView stateNameTv;

    @ViewInject(id=R.id.items_ll)
    private LinearLayout itemsLl;

    @ViewInject(id=R.id.remain_balance)
    private TextView remainBalance;

    @ViewInject(id=R.id.payment_payway)
    private JupiterRowStyleTitleLayout paymentPayWay;

    @ViewInject(id=R.id.remain_balance_rl)
    private RelativeLayout remainBalanceRl;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private PaymentResultCommand command;

    private HistoryPayInfo payInfo;

    public static void start(Activity activity, PaymentResultCommand command) {
        Intent intent = new Intent(activity, PaymentResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (PaymentResultCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        if (command.isPaymentDone()){
            AppCache.getInstance().put(OrderDetailActivity.ORDER_DETAIL_NEES_REFRESH,true);
            setResult(JupiterCommand.RESULT_CODE_OK);
        }
        buildView();
        loadData();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentResultActivity.this.finish();
            }
        });
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayRegisterBySourceService");
        resource.param("source",command.getSource());
        resource.param("sourceid",command.getSourceId());
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
        if (payInfo.getComplete() > 0){
            stateNameTv.setText("支付完成！");
        } else {
            stateNameTv.setText("支付未完成！");
        }
        if (payInfo.getUnPayamount() <=0.0){
            remainBalanceRl.setVisibility(View.GONE);
        }
        paymentPayWay.getHotNitoceTV().setVisibility(View.VISIBLE);
        paymentPayWay.getHotNitoceTV().setTextColor(getResources().getColor(R.color.gray_font));
        paymentPayWay.getHotNitoceTV().setText(payInfo.getPaymodeName());
        remainBalance.setText(payInfo.getUnPayamount() + "元");
        if (payInfo.getPayRegisterCollects() != null){
            String name = null;
            String amount  = null;
            Double sum = 0.0;
            for (PayRegisterCollect collect : payInfo.getPayRegisterCollects()){
                name = collect.getPtname()+":";
                if (PayRegisterCollectState.LOCK.equals(collect.getState())){
                    name += "(等待确认)";
                }
                sum += collect.getAmount();
                amount = collect.getAmount()+"元";
                itemsLl.addView(createItem(name,amount));
            }
            itemsLl.addView(createItem("总支付:",sum+"元"));
        }
    }

    private View createItem(String name,String amount) {
        LeftRightTextWidget widget = new LeftRightTextWidget(this);
        widget.getLeftTv().setText(name);
        widget.getRightTv().setText(amount);
        return widget;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_result;
    }

}
