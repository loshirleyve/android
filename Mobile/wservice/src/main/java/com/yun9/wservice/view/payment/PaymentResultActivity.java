package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.FinanceCollects;

/**
 * Created by huangbinglong on 7/3/15.
 */
public class PaymentResultActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private PaymentResultCommand command;

    public static void start(Activity activity, PaymentResultCommand command) {
        Intent intent = new Intent(activity, PaymentResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JupiterCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(JupiterCommand.RESULT_CODE_OK);
        command = (PaymentResultCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        buildView();
        loadData();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentResultActivity.this.finish();
            }
        });
    }

    private void loadData() {
        Resource resource = resourceFactory.create("QueryCollectsByRequestService");
        resource.param("source", command.getSource());
        resource.param("sourcevalue", command.getSourceId());
        resource.param("instid", sessionManager.getInst().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                FinanceCollects financeCollects = (FinanceCollects) response.getPayload();
                buildWithData(financeCollects);
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

    private void buildWithData(FinanceCollects financeCollects) {
        if (financeCollects == null) {
            return;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_result;
    }
}
