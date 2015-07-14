package com.yun9.wservice.view.payment;

import android.app.Activity;
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
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Payinfo;

import java.io.Serializable;
import java.util.List;

/**
 * 选择子项付款方式，金额界面
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentChoiceWaysActivity extends JupiterFragmentActivity{

    public static final int NO_SELECT = -1;

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_ways_inst_tv)
    private TextView paymentInstTv;

    @ViewInject(id=R.id.listview)
    private ListView listView;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLl;

    private PaymentChoiceWaysCommand command;

    private Payinfo.PaymodeInfo selectedPaymode;

    private int selectedPosition;

    public static void start(Activity activity,PaymentChoiceWaysCommand command) {
        Intent intent = new Intent(activity,PaymentChoiceWaysActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (PaymentChoiceWaysCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_choice_ways;
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentChoiceWaysActivity.this.finish();
            }
        });
        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        listView.setAdapter(adapter);
    }

    private void confirm() {
        if (selectedPaymode != null) {
            PaymentChoiceWayWidget widget = (PaymentChoiceWayWidget) listView.getChildAt(selectedPosition);
            selectedPaymode.setUseAmount(widget.getUserAmount());
            getIntent().putExtra(PaymentChoiceWaysCommand.RETURN_PARAM_HAS_TICKET, widget.hasTicket());
            getIntent().putExtra(PaymentChoiceWaysCommand.RETURN_PARAM_SELECTED_TICKET_INDEX, widget.getSelectedOptionIndex());
            getIntent().putExtra(PaymentChoiceWaysCommand.RETURN_PARAM_PAYMODE, (Serializable) selectedPaymode);
        }
        setResult(JupiterCommand.RESULT_CODE_OK, getIntent());
        this.finish();
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (command != null
                    && command.getCategory() != null
                    && command.getCategory().getPaymodeInfos() != null){
                return command.getCategory().getPaymodeInfos().size();
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
            final PaymentChoiceWayWidget wayWidget;
            Payinfo.PaymodeInfo paymodeInfo = command.getCategory().getPaymodeInfos()
                                                        .get(position);
            paymodeInfo.setUseAmount(command.getSurplusAmount());
            if (convertView == null) {
                wayWidget = new PaymentChoiceWayWidget(PaymentChoiceWaysActivity.this);
                wayWidget.buildWithData(paymodeInfo);
                if (position != 0){
                    wayWidget.hideDetail();
                } else {
                    selectedPaymode = paymodeInfo;
                    wayWidget.showDetail();
                }
                wayWidget.setTag(paymodeInfo);
                wayWidget.buildWithData(paymodeInfo);
                convertView = wayWidget;
            } else {
                wayWidget = (PaymentChoiceWayWidget) convertView;
            }
            wayWidget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = position;
                    int count = adapter.getCount();
                    for (int i = 0; i < count; i++) {
                        PaymentChoiceWayWidget widget = (PaymentChoiceWayWidget) listView.getChildAt(i);
                        if (position == i) {
                            selectedPaymode = (Payinfo.PaymodeInfo) widget.getTag();
                            widget.showDetail();
                        } else {
                            widget.hideDetail();
                        }
                    }
                }
            });
            return convertView;
        }
    };
}
