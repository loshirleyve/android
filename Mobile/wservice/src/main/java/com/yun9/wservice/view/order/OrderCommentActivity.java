package com.yun9.wservice.view.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

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

    private OrderCommentCommand commentCommand;

    public static void start(Activity activity,OrderCommentCommand commentCommand) {
        Intent intent = new Intent(activity,OrderCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND,commentCommand);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent,commentCommand.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentCommand =
                (OrderCommentCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
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
        workOrderIdTV.setText("工单号 " + commentCommand.getWorkOrder().getWorkorderid());
        workOrderNameTV.setText(commentCommand.getWorkOrder().getName());
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryOrderInfoService");
        resource.param("orderid", commentCommand.getOrderId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Order order = (Order) response.getPayload();
                if (order != null
                        && AssertValue.isNotNullAndNotEmpty(order.getOrder().getId())) {
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
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        // 调用服务提交评论
        Resource resource = resourceFactory.create("AddWorkOrderCommentService");
        resource.param("workorderid",commentCommand.getWorkOrder().getWorkorderid());
        resource.param("senderid",sessionManager.getUser().getId());
        resource.param("commenttype","comment");
        resource.param("commenttext",content);
        resource.param("score", starBar.getRating());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showToast("评论成功！");
                setResult(JupiterCommand.RESULT_CODE_OK);
                OrderCommentActivity.this.finish();
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
            }

            @Override
            public void onFinally(Response response) {
                registerDialog.dismiss();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//        editText.setVisibility(View.GONE);
//        editText.setVisibility(View.VISIBLE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        return super.dispatchTouchEvent(ev);
    }
}
