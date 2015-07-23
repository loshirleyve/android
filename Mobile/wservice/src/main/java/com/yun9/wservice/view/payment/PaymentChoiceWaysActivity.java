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
import com.yun9.jupiter.model.ISelectable;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Payinfo;

import java.io.Serializable;

/**
 * 选择子项付款方式，金额界面
 * Created by huangbinglong on 15/6/24.
 */
public class PaymentChoiceWaysActivity extends JupiterFragmentActivity {

    public static final int NO_SELECT = -1;

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.payment_ways_inst_tv)
    private TextView paymentInstTv;

    @ViewInject(id = R.id.listview)
    private ListView listView;

    @ViewInject(id = R.id.confirm_ll)
    private LinearLayout confirmLl;

    private PaymentChoiceWaysCommand command;

    private Payinfo.PaymodeInfo selectedPaymode;

    private int selectedPosition;

    public static void start(Activity activity, PaymentChoiceWaysCommand command) {
        Intent intent = new Intent(activity, PaymentChoiceWaysActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND, command);
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
        calculateSelectedIndex();
        listView.setAdapter(adapter);
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    View widget = listView.getChildAt(i);
                    if (selectedPosition == i) {
                        selectedPaymode = (Payinfo.PaymodeInfo) widget.getTag();
                        ((ISelectable) widget).select(true);
                    } else {
                        ((ISelectable) widget).select(false);
                    }
                }
            }
        }, 10);
    }

    private void calculateSelectedIndex() {
        if (command != null
                && command.getCategory() != null
                && command.getCategory().getPaymodeInfos() != null
                && command.getCategory().getPaymodeInfos().size() > 0) {
            Payinfo.PaymodeInfo info;
            for (int i = 0; i < command.getCategory().getPaymodeInfos().size(); i++) {
                info = command.getCategory().getPaymodeInfos().get(i);
                if (info.getUseAmount() > 0.0) {
                    selectedPosition = i;
                    selectedPaymode = info;
                    break;
                }
            }
        }
    }

    private void confirm() {
        Intent intent = new Intent();
        if (selectedPaymode != null) {
            PaymentChoiceWayWidget widget = (PaymentChoiceWayWidget) listView.getChildAt(selectedPosition);
            selectedPaymode.setUseAmount(widget.getUserAmount());
            intent.putExtra(PaymentChoiceWaysCommand.RETURN_PARAM_HAS_TICKET, widget.hasTicket());
            intent.putExtra(PaymentChoiceWaysCommand.RETURN_PARAM_SELECTED_TICKET_INDEX, widget.getSelectedOptionIndex());
            intent.putExtra(PaymentChoiceWaysCommand.RETURN_PARAM_PAYMODE, (Serializable) selectedPaymode);
        }
        setResult(JupiterCommand.RESULT_CODE_OK, intent);
        this.finish();
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (command != null
                    && command.getCategory() != null
                    && command.getCategory().getPaymodeInfos() != null
                    && command.getCategory().getPaymodeInfos().size() > 0) {
                return command.getCategory().getPaymodeInfos().size() + 1;
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

            // 如果是最后一个
            if (position == command.getCategory().getPaymodeInfos().size()) {
                if (convertView == null) {
                    JupiterRowStyleTitleLayout titleLayout
                            = new JupiterRowStyleTitleLayout(PaymentChoiceWaysActivity.this);
                    titleLayout.getMainIV().setVisibility(View.GONE);
                    titleLayout.getArrowRightIV().setVisibility(View.GONE);
                    titleLayout.setSelectMode(true);
                    titleLayout.getTitleTV().setText("不使用");
                    convertView = titleLayout;
                }
            } else {
                PaymentChoiceWayWidget wayWidget;
                Payinfo.PaymodeInfo paymodeInfo = command.getCategory().getPaymodeInfos()
                        .get(position);
                if (!(paymodeInfo.getUseAmount() > 0.0)) {
                    paymodeInfo.setUseAmount(command.getSurplusAmount());
                }
                if (convertView == null) {
                    wayWidget = new PaymentChoiceWayWidget(PaymentChoiceWaysActivity.this);
                    wayWidget.buildWithData(paymodeInfo);
                    if (position != 0) {
                        wayWidget.hideDetail();
                    } else {
                        wayWidget.showDetail();
                    }
                    wayWidget.setTag(paymodeInfo);
                    wayWidget.buildWithData(paymodeInfo);
                    convertView = wayWidget;
                }
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   selectedPosition = position;
                                                   int count = adapter.getCount();
                                                   for (int i = 0; i < count; i++) {
                                                       View widget = listView.getChildAt(i);
                                                       if (position == i) {
                                                           selectedPaymode = (Payinfo.PaymodeInfo) widget.getTag();
                                                           ((ISelectable) widget).select(true);
                                                       } else {
                                                           ((ISelectable) widget).select(false);
                                                       }
                                                   }
                                               }
                                           }
            );
            return convertView;
        }

    };
}
