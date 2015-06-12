package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderInfoWidget extends JupiterRelativeLayout{

    private ListView listView;

    private Order order;

    public OrderInfoWidget(Context context) {
        super(context);
    }

    public OrderInfoWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderInfoWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Order order) {
        this.order = order;
        reload();
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_info;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        listView = (ListView) this.findViewById(R.id.product_list);
        buildView();
    }

    private void buildView() {

    }

    private void reload() {
        listView.setAdapter(adapter);
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
