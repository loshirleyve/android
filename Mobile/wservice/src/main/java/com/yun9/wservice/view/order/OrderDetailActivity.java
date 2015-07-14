package com.yun9.wservice.view.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.yun9.jupiter.command.JupiterCommand;
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
import com.yun9.wservice.view.msgcard.MsgCardDetailActivity;
import com.yun9.wservice.view.msgcard.MsgCardDetailCommand;
import com.yun9.wservice.view.payment.PaymentOrderActivity;
import com.yun9.wservice.view.payment.PaymentOrderCommand;
import com.yun9.wservice.view.payment.PaymentResultActivity;

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

    @ViewInject(id=R.id.main)
    private RelativeLayout mainRl;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private String orderId;

    public static void start(Context context,String orderId) {
        Intent intent = new Intent(context,OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderid", orderId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainRl.setVisibility(View.GONE);
        orderId = getIntent().getStringExtra("orderid");
        this.buildView();
        reloadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == JupiterCommand.RESULT_CODE_OK){
            reloadData();
        }
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
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
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
                reloadData(new Order());
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
                mainRl.setVisibility(View.VISIBLE);
            }
        });
    }

    private void reloadData(final Order order) {
        if (!AssertValue.isNotNull(order.getOrder())
                        || !State.Order.COMPLETE.equals(order.getOrder().getState())){
            titleBarLayout.getTitleRight().setVisibility(View.GONE);
        }
        if (order.getOrderproducts() == null
                || order.getOrderproducts().size() == 0){
            orderDetailBaseWidget.setVisibility(View.GONE);
        } else {
            orderDetailBaseWidget.builWithData(order);
        }
        if (order.getOrderLogs() == null
                || order.getOrderLogs().size() == 0){
            orderDetailProcessWidget.setVisibility(View.GONE);
        } else {
            orderDetailProcessWidget.buildWithData(order);
        }
        if (AssertValue.isNotNull(order.getOrder())
                && AssertValue.isNotNullAndNotEmpty(order.getOrder().getState())){
            orderDetailPayinfoWidget.buildWithData(order);

            if (order.getOrder().getPaystate() > 0){
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getHotNitoceTV().setTextColor(getResources().getColor(R.color.purple_font));
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getHotNitoceTV().setText("查看付款详情");
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getHotNitoceTV().getPaint().setFakeBoldText(false);
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getTitleTV().setTextColor(getResources().getColor(R.color.black));
                orderDetailPayinfoWidget.getSutitleLayout()
                        .getTitleTV().setText(R.string.already_pay);
            }

            orderDetailPayinfoWidget.getSutitleLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (order.getOrder().getPaystate() > 0) {
                        PaymentResultActivity.start(OrderDetailActivity.this);
                    } else {
                        PaymentOrderCommand command = new PaymentOrderCommand();
                        command.setSource(PaymentOrderCommand.SOURCE_ORDER);
                        command.setSourceValue(order.getOrder().getOrderid());
                        command.setInstId(order.getOrder().getProvideinstid());
                        PaymentOrderActivity.start(OrderDetailActivity.this, command);
                    }
                }
            });
        } else {
            orderDetailPayinfoWidget.setVisibility(View.GONE);
        }
        if (AssertValue.isNotNull(order.getOrder())
                && order.getOrder().getCommitattachment() > 0){
            orderDetailAttachWidget.buildWithData(order);
        } else {
            orderDetailAttachWidget.setVisibility(View.GONE);
        }
        if (AssertValue.isNotNull(order.getOrder())
                && AssertValue.isNotNullAndNotEmpty(order.getOrder().getAdviseruserid())){
            orderDetailAdvisorWidget.buildWitdhData(order);
            orderDetailAdvisorWidget.getContactUsIV().setOnClickListener(onContactUsClick);
        } else {
            orderDetailAdvisorWidget.setVisibility(View.GONE);
        }

        if (AssertValue.isNotNull(order.getOrder())
                && AssertValue.isNotNullAndNotEmpty(order.getOrder().getProvideinstid())){
            orderDetailProviderWidget.buildWitdhData(order);
        } else {
            orderDetailProviderWidget.setVisibility(View.GONE);
        }
        if (order.getOrderWorkorders() == null
                || order.getOrderWorkorders().size() == 0){
            orderDetailWorkOrderListWidget.setVisibility(View.GONE);
        } else {
            orderDetailWorkOrderListWidget.buildWithData(order);
        }
    }

    private View.OnClickListener onContactUsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MsgCardDetailActivity.start(OrderDetailActivity.this,
                    new MsgCardDetailCommand().setOrderId(orderId));
        }
    };
}
