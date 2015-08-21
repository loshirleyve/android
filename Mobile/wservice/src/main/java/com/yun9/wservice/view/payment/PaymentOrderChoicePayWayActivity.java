package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.PayMode;

import java.util.List;

/**
 * Created by huangbinglong on 8/20/15.
 */
public class PaymentOrderChoicePayWayActivity extends JupiterFragmentActivity {

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.payment_ways_lv)
    private ListView listView;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    private PaymentOrderChoicePayWayCommand command;

    private List<PayMode> payModes;

    public static void start(Activity activity,PaymentOrderChoicePayWayCommand command) {
        Intent intent = new Intent(activity,PaymentOrderChoicePayWayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaymentOrderChoicePayWayCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (PaymentOrderChoicePayWayCommand) getIntent()
                .getSerializableExtra(
                        PaymentOrderChoicePayWayCommand.PARAM_COMMAND);
        payModes = command.getPayModes();
        buildView();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentOrderChoicePayWayActivity.this.finish();
            }
        });

        listView.setAdapter(adapter);
    }

    private void confirm(PayMode payMode) {
        Intent intent = new Intent();
        intent.putExtra(JupiterCommand.RESULT_PARAM,payMode);
        this.setResult(JupiterCommand.RESULT_CODE_OK, intent);
        this.finish();
        return;
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (payModes != null) {
                return payModes.size();
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
            final PayMode payMode = payModes.get(position);
            if (convertView == null) {
                sutitleLayout = new JupiterRowStyleSutitleLayout(PaymentOrderChoicePayWayActivity.this);
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
                        confirm(payMode);
                    }
                });
                convertView = sutitleLayout;
            }else {
                sutitleLayout = (JupiterRowStyleSutitleLayout) convertView;
            }
            return convertView;
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_order_choice_payway;
    }

}
