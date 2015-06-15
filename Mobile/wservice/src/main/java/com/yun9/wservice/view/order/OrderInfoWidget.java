package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.OrderCartInfo;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderInfoWidget extends JupiterRelativeLayout{

    private ListView productLV;

    private TextView instName;

    private TextView instPhone;

    private LinearLayout contactUsLL;

    private TextView orderFeeTV;

    private LinearLayout payNowLL;

    private OrderCartInfo order;

    public OrderInfoWidget(Context context) {
        super(context);
    }

    public OrderInfoWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderInfoWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(OrderCartInfo order) {
        this.order = order;
        reload();
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_info;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        productLV = (ListView) this.findViewById(R.id.product_list);
        instName = (TextView) this.findViewById(R.id.inst_name);
        instPhone = (TextView) this.findViewById(R.id.inst_phone);
        contactUsLL = (LinearLayout) this.findViewById(R.id.contact_us_ll);
        orderFeeTV = (TextView) this.findViewById(R.id.order_fee_tv);
        payNowLL = (LinearLayout) this.findViewById(R.id.pay_now_ll);
        buildView();
    }

    private void buildView() {

    }

    private void reload() {
        productLV.setAdapter(adapter);
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            return 2;
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
            OrderProductWidget widget;
            if (convertView == null) {
                widget = new OrderProductWidget(OrderInfoWidget.this.getContext());
                convertView = widget;
            } else {
                widget = (OrderProductWidget) convertView;
            }
            return convertView;
        }
    };
}
