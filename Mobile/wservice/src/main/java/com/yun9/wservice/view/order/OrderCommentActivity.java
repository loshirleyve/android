package com.yun9.wservice.view.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

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

    @ViewInject(id=R.id.order_provider_widget)
    private OrderProviderWidget orderProviderWidget;

    public static void start(Activity activity,String orderId) {
        Intent intent = new Intent(activity,OrderCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderid",orderId);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildView();
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
    }

    private void submittingComment() {
        String content = editText.getText().toString();
        if (!AssertValue.isNotNullAndNotEmpty(content)) {
            showToast("请填写评论内容！");
            return;
        }
        // 调用服务提交投诉
        this.finish();
    }
}
