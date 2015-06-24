package com.yun9.wservice.view.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Payinfo;
import com.yun9.wservice.view.order.OrderRechargeWidget;

import java.util.ArrayList;
import java.util.List;

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

    private PaymentOrderCommand command;

    private Payinfo payinfo;

    public static void start(Context context,PaymentOrderCommand command) {
        Intent intent =  new Intent(context,PaymentOrderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (PaymentOrderCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
        fakeData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_order;
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentOrderActivity.this.finish();
            }
        });
        listView.setAdapter(adapter);
    }

    private void buildWithData(Payinfo payinfo) {
        this.payinfo = payinfo;
        // 进行一系列界面值的改动
        adapter.notifyDataSetChanged();
    }

    public void fakeData() {
        Payinfo data = new Payinfo();
        List<Payinfo.PaymodeCategory> categoryList = new ArrayList<>();
        Payinfo.PaymodeCategory c1 = new Payinfo.PaymodeCategory();
        c1.setName("余额付款");
        Payinfo.PaymodeCategory c2 = new Payinfo.PaymodeCategory();
        c2.setName("其他支付方式");
        Payinfo.PaymodeCategory c3 = new Payinfo.PaymodeCategory();
        c3.setName("会员卡");

        categoryList.add(c1);
        categoryList.add(c2);
        categoryList.add(c3);
        data.setPaymodeCategorys(categoryList);
        this.buildWithData(data);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            JupiterRowStyleTitleLayout titleLayout;
            Payinfo.PaymodeCategory category = payinfo.getPaymodeCategorys().get(position);
            if (convertView == null) {
                titleLayout = new JupiterRowStyleTitleLayout(PaymentOrderActivity.this);
                titleLayout.getHotNitoceTV()
                        .setBackgroundColor(getResources()
                                .getColor(R.color.transparent));
                titleLayout.getHotNitoceTV().setVisibility(View.VISIBLE);
                titleLayout.getHotNitoceTV().setTextColor(getResources().getColor(R.color.black));
                titleLayout.getMainIV().setVisibility(View.GONE);
                titleLayout.getTitleTV()
                        .setText(category.getName());
                convertView = titleLayout;
            } else {
                titleLayout = (JupiterRowStyleTitleLayout) convertView;
            }
            double amount = getUserAmount(category);
            if (amount > 0) {
                titleLayout.getHotNitoceTV().setText(amount+"元");
            } else {
                titleLayout.getHotNitoceTV().setText("未选择");
            }
            return convertView;
        }

        private double getUserAmount(Payinfo.PaymodeCategory category) {
            if (category.getPaymodeInfos() != null) {
                double sum = 0;
                for (Payinfo.PaymodeInfo info : category.getPaymodeInfos()){
                    sum +=info.getUseAmount();
                }
                return sum;
            }
            return 0;
        }

    };
}
