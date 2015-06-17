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
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailWorkOrderListWidget extends JupiterRelativeLayout {

    private ListView workOrdeLV;

    public OrderDetailWorkOrderListWidget(Context context) {
        super(context);
    }

    public OrderDetailWorkOrderListWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderDetailWorkOrderListWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Order order) {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_detail_work_order_list;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        workOrdeLV = (ListView) this.findViewById(R.id.listview);
        buildView();
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
                OrderDetailWorkOrderWidget workOrderWidget;
                if (convertView == null) {
                    workOrderWidget = new OrderDetailWorkOrderWidget(OrderDetailWorkOrderListWidget.this.getContext());
                    convertView = workOrderWidget;
                } else {
                    workOrderWidget = (OrderDetailWorkOrderWidget) convertView;
                }
                return convertView;
            }
        };
        workOrdeLV.setAdapter(adapter);
    }

}
