package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.enums.SourceType;
import com.yun9.wservice.model.Payinfo;
import com.yun9.wservice.view.order.OrderRechargeWidget;

import java.util.HashMap;
import java.util.Map;

/**
 * 立即支付订单界面
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentOrderActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_title_tv)
    private TextView titleTv;

    @ViewInject(id=R.id.payment_sub_title_tv)
    private TextView subTitleTv;

    @ViewInject(id=R.id.order_recharge)
    private OrderRechargeWidget rechargeWidget;

    @ViewInject(id=R.id.payment_ways_inst_tv)
    private TextView paymentInstTv;

    @ViewInject(id=R.id.payment_ways_lv)
    private ListView listView;

    @ViewInject(id=R.id.order_money_tv)
    private TextView paymentMoneyTv;

    @ViewInject(id=R.id.total_money_tv)
    private TextView totalMoneyTv;

    @ViewInject(id=R.id.remain_money_tv)
    private TextView remainMoneyTv;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLl;

    @ViewInject(id=R.id.confirm_tv)
    private TextView confirmTv;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private PaymentOrderCommand command;

    private PaymentChoiceWaysCommand choiceWaysCommand;

    private Payinfo payinfo;

    private Map<String,PayItem> choicePayItemMap;

    private Map<String,Payinfo.PaymodeInfo> categoryChoicePaymodeMap;

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
        categoryChoicePaymodeMap = new HashMap<>();
        choicePayItemMap = new HashMap<>();
        buildView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_order;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (choiceWaysCommand != null
                && choiceWaysCommand.getRequestCode() == requestCode
                && resultCode == JupiterCommand.RESULT_CODE_OK){
            Payinfo.PaymodeInfo paymodeInfo = (Payinfo.PaymodeInfo) data
                                        .getSerializableExtra(
                                                PaymentChoiceWaysCommand.RETURN_PARAM_PAYMODE);
            if (paymodeInfo == null){ // 用户选择了不使用
                paymodeInfo = categoryChoicePaymodeMap.get(choiceWaysCommand.getCategory().getId());
                if (paymodeInfo != null){
                    paymodeInfo.setUseAmount(0.0);
                } else {
                    return;
                }
            }
            // 如果选择的值不大于0
            if (!(paymodeInfo.getUseAmount() >= 0.0)){
                choicePayItemMap.remove(choiceWaysCommand.getCategory().getId());
                categoryChoicePaymodeMap.remove(choiceWaysCommand.getCategory().getId());
                return;
            }
            PayItem payItem = new PayItem();
            payItem.setPayModeId(paymodeInfo.getPaymodeId());
            payItem.setAmount(paymodeInfo.getUseAmount() + "");
            // 设置默认选择的BizFinaceAccount
            if (paymodeInfo.getBizFinanceAccounts() != null
                    && paymodeInfo.getBizFinanceAccounts().size() > 0){
                payItem.setAccountid(paymodeInfo.getBizFinanceAccounts().get(0).getId());
            }
            // 设置实际用户选择的优惠券
            boolean hasTicket = data.getBooleanExtra(PaymentChoiceWaysCommand.RETURN_PARAM_HAS_TICKET,false);
            if (hasTicket){
                int index = data.getIntExtra(PaymentChoiceWaysCommand.RETURN_PARAM_SELECTED_TICKET_INDEX,0);
                payItem.setAccountid(paymodeInfo.getBizFinanceAccounts().get(index).getId());
            }
            choicePayItemMap.put(choiceWaysCommand.getCategory().getId(),payItem);
            categoryChoicePaymodeMap.put(choiceWaysCommand.getCategory().getId(),paymodeInfo);
        }
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentOrderActivity.this.finish();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        listView.setAdapter(adapter);
    }

    private void submit() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("UpdateCollectByPayService");
        resource.param("amount",payinfo.getPayableAmount());
        resource.param("instId",command.getInstId());
        resource.param("payInstId",sessionManager.getInst().getId());
        resource.param("source",command.getSource());
        resource.param("sourceValue",command.getSourceValue());
        resource.param("userid", sessionManager.getUser().getId());
        resource.param("payItems", choicePayItemMap.values());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showToast(R.string.pay_success_be_wait);
                setResult(JupiterCommand.RESULT_CODE_OK);
                PaymentOrderActivity.this.finish();
                PaymentResultActivity.start(PaymentOrderActivity.this,
                        new PaymentResultCommand(
                                SourceType.TYPE_ORDER, payinfo.getSourceValue()
                        ).setPaymentDone(true));
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

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryPayinfoService");
        resource.param("source", command.getSource());
        resource.param("instId", command.getInstId());
        resource.param("buyinstId", sessionManager.getInst().getId());
        resource.param("sourceValue", command.getSourceValue());
        resource.param("userId", sessionManager.getUser().getId());
        resource.param("paymodeInfos", categoryChoicePaymodeMap.values());
        if (ClientProxyCache.getInstance().isProxy()){
            resource.param("buyinstId", ClientProxyCache.getInstance().getProxy().getInstId());
        }
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                payinfo = (Payinfo) response.getPayload();
                rebuild();
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

    private void rebuild() {
        titleTv.setText(payinfo.getTitle());
        subTitleTv.setText(payinfo.getSubtitle());
        rechargeWidget.buildWithData(payinfo.getBalance());
        paymentMoneyTv.setText(payinfo.getPayableAmount() + "元");
        totalMoneyTv.setText(payinfo.getFactpayAmount() + "元");
        remainMoneyTv.setText(payinfo.getSurplusAmount() + "元");

        if (payinfo.getSurplusAmount() != 0){
            confirmTv.setTextColor(getResources().getColor(R.color.devide_line));
            confirmLl.setClickable(false);
        } else {
            confirmTv.setTextColor(getResources().getColor(R.color.title_color));
            confirmLl.setClickable(true);
        }
        rechargeWidget.getRechargeIV().setOnClickListener(rechargeClickListener);
        rebuildChoicedPayItem();
        adapter.notifyDataSetChanged();
    }

    private void rebuildChoicedPayItem() {
        choicePayItemMap.clear();
        if (payinfo.getPaymodeCategorys() == null){
            return;
        }
        for (Payinfo.PaymodeCategory paymodeCategory : payinfo.getPaymodeCategorys()) {
            if (paymodeCategory.getPaymodeInfos() == null){
                continue;
            }
            for (Payinfo.PaymodeInfo paymodeInfo : paymodeCategory.getPaymodeInfos()){
                if (paymodeInfo.getUseAmount() > 0.0){
                    PayItem payItem = new PayItem();
                    payItem.setPayModeId(paymodeInfo.getPaymodeId());
                    payItem.setAmount(paymodeInfo.getUseAmount() + "");
                    // 设置默认选择的BizFinaceAccount
                    if (paymodeInfo.getBizFinanceAccounts() != null
                            && paymodeInfo.getBizFinanceAccounts().size() > 0){
                        payItem.setAccountid(paymodeInfo.getBizFinanceAccounts().get(0).getId());
                    }
                    choicePayItemMap.put(paymodeCategory.getId(),payItem);
                    categoryChoicePaymodeMap.put(paymodeCategory.getId(),paymodeInfo);
                    break;
                }
            }
        }
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (payinfo != null
                    && payinfo.getPaymodeCategorys() != null){
                return payinfo.getPaymodeCategorys().size();
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
            JupiterRowStyleSutitleLayout titleLayout;
            if (convertView == null) {
                titleLayout = new JupiterRowStyleSutitleLayout(PaymentOrderActivity.this);
                titleLayout.getHotNitoceTV()
                        .setBackgroundColor(getResources()
                                .getColor(R.color.transparent));
                titleLayout.getHotNitoceTV().setVisibility(View.VISIBLE);
                titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));
                titleLayout.getMainIV().setVisibility(View.GONE);
                titleLayout.getTimeTv().setVisibility(View.GONE);
                titleLayout.getTitleTV()
                        .setText(payinfo.getPaymodeCategorys().get(position).getName());
                titleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choiceWaysCommand = new PaymentChoiceWaysCommand();
                        choiceWaysCommand.setCategory(payinfo.getPaymodeCategorys().get(position));
                        choiceWaysCommand.setSurplusAmount(payinfo.getSurplusAmount());
                        PaymentChoiceWaysActivity.start(PaymentOrderActivity.this,choiceWaysCommand);
                    }
                });
                convertView = titleLayout;
            } else {
                titleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }
            Payinfo.PaymodeInfo info = getUserAmount(payinfo.getPaymodeCategorys().get(position));
            if (info != null) {
                titleLayout.getSutitleTv().setVisibility(View.VISIBLE);
                titleLayout.getSutitleTv().setText("已选: "+info.getPaymodeName());
                titleLayout.getHotNitoceTV().setText(info.getUseAmount()+"元");
            } else {
                titleLayout.getSutitleTv().setVisibility(View.GONE);
                titleLayout.getHotNitoceTV().setText("未选择");
            }
            return convertView;
        }

        private Payinfo.PaymodeInfo getUserAmount(Payinfo.PaymodeCategory category) {
            if (category.getPaymodeInfos() != null) {
                for (Payinfo.PaymodeInfo info : category.getPaymodeInfos()){
                    if (info.getUseAmount() > 0.0){
                        return info;
                    }
                }
            }
            return null;
        }

    };

    private View.OnClickListener rechargeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RechargeCommand rechargeCommand = new RechargeCommand();
            double amount = payinfo.getPayableAmount() - payinfo.getBalance();
            if (amount > 0.0){
                rechargeCommand.setAmount(amount);
            }
            RechargeActivity.start(PaymentOrderActivity.this,rechargeCommand);
        }
    };

    private class PayItem{

        private String payModeId;
        private String amount;
        private String accountid;

        public String getPayModeId() {
            return payModeId;
        }

        public void setPayModeId(String payModeId) {
            this.payModeId = payModeId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAccountid() {
            return accountid;
        }

        public void setAccountid(String accountid) {
            this.accountid = accountid;
        }
    }
}
