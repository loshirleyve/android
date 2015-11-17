package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrderCartInfo;
import com.yun9.wservice.model.wrapper.OrderCartInfoWrapper;

import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderInfoWidget extends JupiterRelativeLayout{

    private ListView productLV;

    private OrderProviderWidget providerWidget;

    private OrderCartInfoWrapper order;

    private List<OrderCartInfo>  orderProducts;

    public OrderInfoWidget(Context context) {
        super(context);
    }

    public OrderInfoWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderInfoWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(OrderCartInfoWrapper order,List<OrderCartInfo> orderProducts) {
        this.order = order;
        this.orderProducts = orderProducts;
        reload();
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_info;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        productLV = (ListView) this.findViewById(R.id.product_list);
        providerWidget = (OrderProviderWidget) this.findViewById(R.id.inst_cell);
        buildView();
    }

    private void buildView() {

    }

    private void reload() {
        if (AssertValue.isNotNullAndNotEmpty(order.getInstid())){
            providerWidget.buildWithData(order.getInstid());
        } else {
            providerWidget.setVisibility(GONE);
        }
        productLV.setAdapter(adapter);
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (order != null && orderProducts != null && orderProducts.size() > 0){
                return 1;
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
            OrderProductWidget widget;
            if (convertView == null) {
                widget = new OrderProductWidget(OrderInfoWidget.this.getContext());
                convertView = widget;
            } else {
                widget = (OrderProductWidget) convertView;
            }
            widget.buildWithData(orderProducts.get(position));
            return convertView;
        }
    };
}
