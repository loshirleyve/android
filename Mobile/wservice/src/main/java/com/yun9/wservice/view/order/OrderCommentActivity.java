package com.yun9.wservice.view.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

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

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/6/17.
 */
public class OrderCommentActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.product_list_widget)
    private OrderProductBaseListWidget productBaseListWidget;

    @ViewInject(id=R.id.work_order_id_tv)
    private TextView workOrderIdTV;

    @ViewInject(id=R.id.work_order_name_tv)
    private TextView workOrderNameTV;

    @ViewInject(id=R.id.comment_et)
    private EditText editText;

    @ViewInject(id=R.id.star_bar)
    private RatingBar starBar;

    @ViewInject(id=R.id.order_provider_widget)
    private OrderProviderWidget orderProviderWidget;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private String orderId;

    private Order.OrderWorkOrder workOrder;

    public static void start(Activity activity,String orderId,Order.OrderWorkOrder workOrder) {
        Intent intent = new Intent(activity,OrderCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderid",orderId);
        bundle.putSerializable("workorder", (Serializable) workOrder);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orderId = getIntent().getStringExtra("orderid");
        this.workOrder = (Order.OrderWorkOrder) getIntent().getSerializableExtra("workorder");
        buildView();
        loadData();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_comment;
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderCommentActivity.this.finish();
            }
        });
        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submittingComment();
            }
        });
        // 设置工单信息
        workOrderIdTV.setText("工单号 "+workOrder.getOrderworkid());
        workOrderNameTV.setText(workOrder.getOrderworkname());
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryOrderInfoService");
        resource.param("orderid", orderId);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Order order = (Order) response.getPayload();
                if (order != null
                        && AssertValue.isNotNullAndNotEmpty(order.getOrder().getOrderid())){
                    buildWithOrder(order);
                }
            }

            @Override
            public void onFailure(Response response) {

            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }

    private void buildWithOrder(Order order) {
        productBaseListWidget.buildWithData(order);
        orderProviderWidget.buildWithData(order);
    }

    private void submittingComment() {
        String content = editText.getText().toString();
        if (!AssertValue.isNotNullAndNotEmpty(content)) {
            showToast("请填写评论内容！");
            return;
        }
        // 调用服务提交评论
        Resource resource = resourceFactory.create("AddWorkOrderCommentService");
        resource.param("workorderid",workOrder.getOrderworkid());
        resource.param("senderid",sessionManager.getUser().getId());
        resource.param("commenttype","comment");
        resource.param("commenttext",content);
        resource.param("score",starBar.getRating());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showToast("评论成功！");
                OrderCommentActivity.this.finish();
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
}
