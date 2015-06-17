package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/6/17.
 */
public class OrderProductBaseListWidget extends JupiterRelativeLayout{

    private ListView productLV;
    private TextView orderSnTV;
    private TextView orderStateTV;

    public OrderProductBaseListWidget(Context context) {
        super(context);
    }

    public OrderProductBaseListWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderProductBaseListWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData() {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_product_base_list;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        productLV = (ListView) this.findViewById(R.id.product_list_lv);
        orderSnTV = (TextView) this.findViewById(R.id.order_sn_tv);
        orderStateTV = (TextView) this.findViewById(R.id.order_state_tv);
        this.buildView();
    }

    private void buildView() {
        JupiterAdapter adapter = new JupiterAdapter() {
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
                OrderProductBaseWidget productBaseWidget;
                if (convertView == null) {
                    productBaseWidget = new OrderProductBaseWidget(OrderProductBaseListWidget
                                                                        .this.getContext());
                    convertView = productBaseWidget;
                } else {
                    productBaseWidget = (OrderProductBaseWidget) convertView;
                }
                return convertView;
            }
        };
        productLV.setAdapter(adapter);
    }
}
