package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/23.
 */
public class PaymentChoiceWaysActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_ways_lv)
    private ListView listView;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLL;

    private PaymentChoiceWaysCommand command;

    public static void start(Activity activity,PaymentChoiceWaysCommand command) {
        Intent intent = new Intent(activity,PaymentChoiceWaysActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaymentChoiceWaysCommand.PARAM_COMMAND,command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (PaymentChoiceWaysCommand) getIntent()
                                                .getSerializableExtra(
                                                        PaymentChoiceWaysCommand.PARAM_COMMAND);
        buildView();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_choice_way;
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentChoiceWaysActivity.this.finish();
            }
        });

        confirmLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
    }

    private void confirm() {

    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
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
            JupiterRowStyleSutitleLayout sutitleLayout;
            if (convertView == null) {
                sutitleLayout = new JupiterRowStyleSutitleLayout(PaymentChoiceWaysActivity.this);
            }else {
                sutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }
            return convertView;
        }
    };
}
