package com.yun9.wservice.view.order;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderProviderWidget extends JupiterRelativeLayout{

    private TextView instNameTV;
    private TextView instPhoneTV;
    private LinearLayout contactUsLL;

    public OrderProviderWidget(Context context) {
        super(context);
    }

    public OrderProviderWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderProviderWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_provider;
    }

    public void buildWithData() {

    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        instNameTV = (TextView) this.findViewById(R.id.inst_name_tv);
        instPhoneTV = (TextView) this.findViewById(R.id.inst_phone_tv);
        contactUsLL = (LinearLayout) this.findViewById(R.id.contact_us_ll);
        buildView();
    }

    private void buildView() {
        contactUsLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "10010";
                //用intent启动拨打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                OrderProviderWidget.this.getContext().startActivity(intent);
            }
        });
    }
}
