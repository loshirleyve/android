package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yun9.jupiter.cache.AppCache;
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
import com.yun9.wservice.model.HistoryPayInfo;
import com.yun9.wservice.view.order.OrderDetailActivity;

/**
 * Created by huangbinglong on 15/12/25.
 */
public class PaymentRefundActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.refund_et)
    private EditText refundEt;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private PaymentRefundCommand command;

    public static void start(Activity activity, PaymentRefundCommand command) {
        Intent intent = new Intent(activity, PaymentRefundActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_refund;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (PaymentRefundCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == JupiterCommand.RESULT_CODE_OK) {
            setResult(JupiterCommand.RESULT_CODE_OK);
        }
    }

    private void loadData() {
        // 加载退款列表原因
    }

    /**
     * 设置页面事件
     */
    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentRefundActivity.this.finish();
            }
        });

        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRefund();
            }
        });
    }

    /**
     * 保存退款原因
     */
    private void saveRefund() {
        String refundText = refundEt.getText().toString();
        if (!AssertValue.isNotNullAndNotEmpty(refundText)) {
            showToast("请填写退款原因！");
            return;
        }
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("UpdateOrderRefundService");
        resource.param("orderId", command.getOrderId());
        resource.param("refundReason", refundText);
        resource.param("refundAmount", command.getRefundamount());
        resource.param("userId", sessionManager.getUser().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                setResult(JupiterCommand.RESULT_CODE_OK);
                PaymentRefundActivity.this.finish();
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
}
