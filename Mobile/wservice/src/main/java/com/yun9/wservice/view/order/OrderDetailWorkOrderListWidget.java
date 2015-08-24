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

import java.util.List;

/**
 * Created by huangbinglong on 15/6/16.
 */
public class OrderDetailWorkOrderListWidget extends JupiterRelativeLayout {

    private ListView workOrdeLV;

    private JupiterAdapter adapter;

    private Order order;

    private List<Order.OrderWorkOrder> workOrders;

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
        this.order = order;
        //workOrders = order.getOrderWorkorders();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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
        adapter = new JupiterAdapter() {
            @Override
            public int getCount() {
                if (workOrders != null){
                    return workOrders.size();
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
                OrderDetailWorkOrderWidget workOrderWidget;
                if (convertView == null) {
                    workOrderWidget = new OrderDetailWorkOrderWidget(OrderDetailWorkOrderListWidget.this.getContext());
                    convertView = workOrderWidget;
                } else {
                    workOrderWidget = (OrderDetailWorkOrderWidget) convertView;
                }
                workOrderWidget.buildWithData(order.getOrder().getOrderid(),workOrders.get(position));
                return convertView;
            }
        };
        workOrdeLV.setAdapter(adapter);
    }

}
