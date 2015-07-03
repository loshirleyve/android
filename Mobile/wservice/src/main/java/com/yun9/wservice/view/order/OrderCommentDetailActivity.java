package com.yun9.wservice.view.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;
import com.yun9.wservice.model.WorkOrderComment;
import com.yun9.wservice.widget.ShowCommentWidget;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/6/17.
 */
public class OrderCommentDetailActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.show_comment_widget)
    private ShowCommentWidget showCommentWidget;

    @ViewInject(id=R.id.sub_comment_lv)
    private ListView subCommentLV;

    @ViewInject(id=R.id.comment_et)
    private EditText editText;

    @ViewInject(id=R.id.order_provider_widget)
    private OrderProviderWidget orderProviderWidget;

    @BeanInject
    private ResourceFactory resourceFactory;
    @BeanInject
    private SessionManager sessionManager;

    private String orderId;

    private Order.OrderWorkOrder workOrder;

    private WorkOrderComment workOrderComment;

    public static void start(Activity activity,String orderId,Order.OrderWorkOrder workOrder) {
        Intent intent = new Intent(activity,OrderCommentDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderid", orderId);
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
        return R.layout.activity_order_comment_detail;
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderCommentDetailActivity.this.finish();
            }
        });

        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSubComment();
            }
        });

        subCommentLV.setAdapter(adapter);
    }

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryWorkCommentsByWorkOrderIdService");
        resource.param("workorderid", workOrder.getOrderworkid());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                WorkOrderComment comment = (WorkOrderComment) response.getPayload();
                reloadData(comment);
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

    private void reloadData(WorkOrderComment comment) {
        this.workOrderComment = comment;
        CacheUser user = UserCache.getInstance().getUser(comment.getSenderid());
        if (user != null) {
            showCommentWidget.setTitle(user.getName());
        }
        showCommentWidget.setContent(comment.getCommenttext());
        if (comment.getCreatedate() != null){
            showCommentWidget.setTime(comment.getCreatedate());
        }
        showCommentWidget.setRating((float) comment.getScore());
        orderProviderWidget.buildWithData(comment.getBuyerinstid());
        adapter.notifyDataSetChanged();
    }

    private void saveSubComment() {
        String content = editText.getText().toString();
        if (!AssertValue.isNotNullAndNotEmpty(content)) {
            showToast("请填写评论内容！");
            return;
        }
        // 调用服务提交评论
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("AddWorkOrderCommentService");
        resource.param("workorderid",workOrder.getOrderworkid());
        resource.param("senderid",sessionManager.getUser().getId());
        resource.param("commenttype",WorkOrderComment.TYPE_ADD_COMMENT);
        resource.param("commenttext",content);
        resource.param("score",0);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showToast("评论成功！");
                OrderCommentDetailActivity.this.finish();
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

    private JupiterAdapter adapter = new JupiterAdapter() {
        @Override
        public int getCount() {
            if (workOrderComment != null
                    && workOrderComment.getAddcomments() != null){
                return workOrderComment.getAddcomments().size();
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
            OrderWorkOrderSubCommentWidget subCommentWidget;
            if (convertView == null) {
                subCommentWidget = new OrderWorkOrderSubCommentWidget(OrderCommentDetailActivity.this);
                convertView = subCommentWidget;
            } else {
                subCommentWidget = (OrderWorkOrderSubCommentWidget) convertView;
            }
            subCommentWidget.buildWithData(workOrderComment.getAddcomments().get(position));
            return convertView;
        }
    };
}
