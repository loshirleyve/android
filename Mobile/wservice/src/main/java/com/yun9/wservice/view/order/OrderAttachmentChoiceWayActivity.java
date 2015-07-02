package com.yun9.wservice.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.ISelectable;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.AttachTransferWay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 7/1/15.
 */
public class OrderAttachmentChoiceWayActivity extends JupiterFragmentActivity{

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.express_widget)
    private OrderAttachWayExpressWidget expressWidget;

    @ViewInject(id=R.id.vist_widget)
    private OrderAttachWayVistWidget vistWidget;

    @ViewInject(id=R.id.email_widget)
    private OrderAttachWayEmailWidget emailWidget;

    @ViewInject(id=R.id.self_widget)
    private OrderAttachWaySelfWidget selfWidget;

    @ViewInject(id=R.id.cancel_widget)
    private JupiterRowStyleTitleLayout cancelWidge;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    private List<View> widgets;

    public static void start(Context context) {
        Intent intent = new Intent(context,OrderAttachmentChoiceWayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        widgets = new ArrayList<>();
        buildView();
        loadData();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAttachmentChoiceWayActivity.this.finish();
            }
        });
        cancelWidge.setSelectMode(true);
        widgets.add(expressWidget);
        widgets.add(vistWidget);
        widgets.add(emailWidget);
        widgets.add(selfWidget);
        widgets.add(cancelWidge);
        setupWidgetClick();
        reSelect(0);
    }

    private void setupWidgetClick() {
        for (int i = 0; i < widgets.size(); i++) {
            setupWidgetClick(i);
        }
    }

    private void setupWidgetClick(final int position) {
        widgets.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSelect(position);
            }
        });
    }

    private void reSelect(int position) {
        for (int i = 0; i < widgets.size(); i++) {
            if (i == position){
                ((ISelectable)widgets.get(i)).select(true);
            } else {
                ((ISelectable)widgets.get(i)).select(false);
            }
        }
    }

    private void loadData() {
        Resource resource = resourceFactory.create("QueryTransferByInstIdService");
        resource.param("instid", sessionManager.getInst().getId());
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                List<AttachTransferWay> ways = (List<AttachTransferWay>) response.getPayload();
                for (AttachTransferWay way : ways){
                    setupWidgetData(way);
                }
            }

            @Override
            public void onFailure(Response response) {
                showToast(response.getCause());
                OrderAttachmentChoiceWayActivity.this.finish();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private void setupWidgetData(AttachTransferWay way) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_attachment_choice_way;
    }
}
