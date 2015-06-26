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
import com.yun9.wservice.model.Order;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/17.
 */
public class OrderProductBaseListWidget extends JupiterRelativeLayout{

    private ListView productLV;
    private TextView orderSnTV;
    private TextView orderStateTV;

    private JupiterAdapter adapter;

    private List<Order.OrderProduct> products;

    public OrderProductBaseListWidget(Context context) {
        super(context);
    }

    public OrderProductBaseListWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderProductBaseListWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(Order order) {
        orderSnTV.setText(order.getOrdersn());
        orderStateTV.setText(order.getStatename());
        this.products = order.getProducts();
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }
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
        adapter = new JupiterAdapter() {
            @Override
            public int getCount() {
                if (products != null){
                    return products.size();
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
                OrderProductBaseWidget productBaseWidget;
                if (convertView == null) {
                    productBaseWidget = new OrderProductBaseWidget(OrderProductBaseListWidget
                                                                        .this.getContext());
                    convertView = productBaseWidget;
                } else {
                    productBaseWidget = (OrderProductBaseWidget) convertView;
                }
                productBaseWidget.buildWithData(products.get(position));;
                return convertView;
            }
        };
        productLV.setAdapter(adapter);
    }
}
