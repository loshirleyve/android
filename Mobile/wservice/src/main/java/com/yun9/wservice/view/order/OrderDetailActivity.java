package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.State;

/**
 * Created by huangbinglong on 15/6/15.
 */
public class OrderDetailActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.order_detail_base_widget)
    private OrderDetailBaseWidget orderDetailBaseWidget;

    @ViewInject(id=R.id.order_detail_process_widget)
    private OrderDetailProcessWidget orderDetailProcessWidget;

    @ViewInject(id=R.id.order_detail_payinfo_widget)
    private OrderDetailPayinfoWidget orderDetailPayinfoWidget;

    @ViewInject(id=R.id.order_detail_attach_widget)
    private OrderDetailAttachWidget orderDetailAttachWidget;

    @ViewInject(id=R.id.order_detail_advisor_widget)
    private OrderDetailAdvisorWidget orderDetailAdvisorWidget;

    @ViewInject(id=R.id.order_detail_provider_widget)
    private OrderDetailProviderWidget orderDetailProviderWidget;

    @ViewInject(id=R.id.order_detail_work_order_list_widget)
    private OrderDetailWorkOrderListWidget orderDetailWorkOrderListWidget;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private String orderId;

    public static void start(Activity activity,String orderId) {
        Intent intent = new Intent(activity,OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderid",orderId);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getIntent().getStringExtra("orderid");
        this.buildView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    private void buildView() {
        titleBarLayout.getTitleTv().setText(R.string.order_detail);
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailActivity.this.finish();
            }
        });
        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderComplainActivity.start(OrderDetailActivity.this, orderId);
            }
        });
    }

    private void reloadData() {
        Resource resource = resourceFactory.create("QueryOrderInfoService");
        resource.param("orderid", orderId);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Order order = (Order) response.getPayload();
                reloadData(order);
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void reloadData(Order order) {
        if (!State.Order.COMPLETE.equals(order.getState())){
            titleBarLayout.getTitleRight().setVisibility(View.GONE);
        }
        if (order.getProducts() == null
                || order.getProducts().size() == 0){
            orderDetailBaseWidget.setVisibility(View.GONE);
        } else {
            orderDetailBaseWidget.builWithData(order);
        }
        if (order.getOrderlogs() == null
                || order.getOrderlogs().size() == 0){
            orderDetailProcessWidget.setVisibility(View.GONE);
        } else {
            orderDetailProcessWidget.buildWithData(order);
        }
        if (AssertValue.isNotNullAndNotEmpty(order.getState())){
            orderDetailPayinfoWidget.buildWithData(order);
        } else {
            orderDetailPayinfoWidget.setVisibility(View.GONE);
        }
        if (order.getCommitattachment() != null
                && order.getCommitattachment() > 0){
            orderDetailAttachWidget.buildWithData(order);
        } else {
            orderDetailAttachWidget.setVisibility(View.GONE);
        }
        if (AssertValue.isNotNullAndNotEmpty(order.getAdviseruserid())){
            orderDetailAdvisorWidget.buildWitdhData(order);
        } else {
            orderDetailAdvisorWidget.setVisibility(View.GONE);
        }

        if (AssertValue.isNotNullAndNotEmpty(order.getProvideinstid())){
            orderDetailProviderWidget.buildWitdhData(order);
        } else {
            orderDetailProviderWidget.setVisibility(View.GONE);
        }
        if (order.getWorkorders() == null
                || order.getWorkorders().size() == 0){
            orderDetailWorkOrderListWidget.setVisibility(View.GONE);
        } else {
            orderDetailWorkOrderListWidget.buildWithData(order);
        }
    }
}
