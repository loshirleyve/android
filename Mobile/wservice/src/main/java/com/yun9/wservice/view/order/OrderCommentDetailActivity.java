package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterAdapter;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.Order;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/6/17.
 */
public class OrderCommentDetailActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.user_name_tv)
    private TextView userNameTV;

    @ViewInject(id=R.id.comment_time_tv)
    private TextView commentTimeTV;

    @ViewInject(id=R.id.comment_content_tv)
    private TextView commentContentTV;

    @ViewInject(id=R.id.rating_star_rb)
    private RatingBar ratingBar;

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

    private Order.WorkOrder workOrder;

    public static void start(Activity activity,String orderId,Order.WorkOrder workOrder) {
        Intent intent = new Intent(activity,OrderCommentDetailActivity.class);
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
        this.workOrder = (Order.WorkOrder) getIntent().getSerializableExtra("workorder");
        buildView();
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

    private void saveSubComment() {
        String content = editText.getText().toString();
        if (!AssertValue.isNotNullAndNotEmpty(content)) {
            showToast("请填写评论内容！");
            return;
        }
        // 调用服务提交投诉
        this.finish();
    }

    private JupiterAdapter adapter = new JupiterAdapter() {
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
            OrderWorkOrderSubCommentWidget subCommentWidget;
            if (convertView == null) {
                subCommentWidget = new OrderWorkOrderSubCommentWidget(OrderCommentDetailActivity.this);
                convertView = subCommentWidget;
            } else {
                subCommentWidget = (OrderWorkOrderSubCommentWidget) convertView;
            }
            return convertView;
        }
    };
}
