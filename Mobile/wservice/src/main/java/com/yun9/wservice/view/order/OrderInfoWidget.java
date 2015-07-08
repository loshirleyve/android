package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.cache.ClientProxyCache;
import com.yun9.wservice.model.OrderCartInfo;
import com.yun9.wservice.model.SimpleIdReturn;
import com.yun9.wservice.view.payment.PaymentOrderActivity;
import com.yun9.wservice.view.payment.PaymentOrderCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by huangbinglong on 15/6/12.
 */
public class OrderInfoWidget extends JupiterRelativeLayout{

    private ListView productLV;

    private OrderProviderWidget providerWidget;

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
        providerWidget = (OrderProviderWidget) this.findViewById(R.id.inst_cell);
        orderFeeTV = (TextView) this.findViewById(R.id.order_fee_tv);
        payNowLL = (LinearLayout) this.findViewById(R.id.pay_now_ll);
        buildView();
    }

    private void buildView() {
        payNowLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrderAndPay();
            }
        });
    }

    private void submitOrderAndPay() {
        SessionManager sessionManager = JupiterApplication.getBeanManager().get(SessionManager.class);
        ResourceFactory resourceFactory = JupiterApplication.getBeanManager().get(ResourceFactory.class);
        Resource resource = resourceFactory.create("AddOrderByOrderViewService");
        this.order.setCreateby(sessionManager.getUser().getId());
        resource.param("orderViews", Collections.singletonList(this.order));
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<SimpleIdReturn> orderIds= (List<SimpleIdReturn>) response.getPayload();
                payNow(orderIds.get(0).getId());
            }

            @Override
            public void onFailure(Response response) {

            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void payNow(String orderId) {
        PaymentOrderCommand command = new PaymentOrderCommand();
        command.setSource(PaymentOrderCommand.SOURCE_ORDER);
        command.setSourceValue(orderId);
        command.setInstId(order.getProvideinstid());
        ((Activity)(this.getContext())).finish();
        PaymentOrderActivity.start(this.getContext(), command);
    }

    private List<String> getProductIds() {
        List<String> ids = new ArrayList<>();
        if (order != null
                && order.getOrderproducts() != null){
            for (OrderCartInfo.OrderProduct product : order.getOrderproducts()){
                ids.add(product.getProductid());
            }
        }
        return ids;
    }

    private void reload() {
        orderFeeTV.setText(order.getOrderamount()+"元");
        providerWidget.buildWithData(order.getProvideinstid());
        productLV.setAdapter(adapter);
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (order != null
                    && order.getOrderproducts() != null){
                return order.getOrderproducts().size();
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
                widget.buildWithData(order.getOrderproducts().get(position));
                convertView = widget;
            } else {
                widget = (OrderProductWidget) convertView;
            }
            return convertView;
        }
    };
}
