package com.yun9.wservice.view.order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.ISelectable;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.AttachTransferWay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 7/1/15.
 */
public class OrderAttachmentChoiceWayActivity extends JupiterFragmentActivity{

    public static final String CHOICE_WAY_HOLDER = "choice_way_holder";

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

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLl;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    private List<View> widgets;

    public static void start(Activity activity,JupiterCommand command) {
        Intent intent = new Intent(activity,OrderAttachmentChoiceWayActivity.class);
        activity.startActivityForResult(intent,command.getRequestCode());
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
        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });
        cancelWidge.setSelectMode(true);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.setKey("快递");
        expressWidget.setTag(viewHolder);
        widgets.add(expressWidget);

        viewHolder = new ViewHolder();
        viewHolder.setKey("上门取件");
        vistWidget.setTag(viewHolder);
        widgets.add(vistWidget);

        viewHolder = new ViewHolder();
        viewHolder.setKey("发送邮件");
        emailWidget.setTag(viewHolder);
        widgets.add(emailWidget);

        viewHolder = new ViewHolder();
        viewHolder.setKey("自助");
        selfWidget.setTag(viewHolder);
        widgets.add(selfWidget);

        widgets.add(cancelWidge);
        setupWidgetClick();
        reSelect(0);
    }

    /**
     * 确定选择
     */
    private void confirm() {
        for (View view : widgets){
            if (((ISelectable)view).isSelected()){
                confirmSelected(view.getTag());
                return;
            }
        }
    }

    private void confirmSelected(Object tag) {
        Intent intent = new Intent();
        if (tag != null){
            ViewHolder viewHolder = (ViewHolder) tag;
            if (viewHolder.getKey().equals("快递")){
                if (AssertValue.isNotNullAndNotEmpty(expressWidget.getExpressNo())){
                    viewHolder.setValue(expressWidget.getExpressNo());
                } else {
                    showToast("请填写快递单号");
                    return;
                }
            }
            intent.putExtra(CHOICE_WAY_HOLDER, viewHolder);
        }
        setResult(JupiterCommand.RESULT_CODE_OK,intent);
        this.finish();
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
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
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
                registerDialog.dismiss();
            }
        });
    }

    private void setupWidgetData(AttachTransferWay way) {
        for (View view : widgets){
            Object tag = view.getTag();
            if (tag != null){
                ViewHolder viewHolder = (ViewHolder) tag;
                if (viewHolder.getKey().equals(way.getName())){
                    viewHolder.setWay(way);
                    break;
                }
            }
        }
        // 根据附件类型设置不同的控件的值
        if (way.getName().equals("快递")){
            expressWidget.getSutitleLayout().getSutitleTv().setText(way.getDescr());
            expressWidget.getTipTv().setText(way.getTips());
        } else if (way.getName().equals("上门取件")) {
            vistWidget.getSutitleLayout().getSutitleTv().setText(way.getDescr());
        } else if (way.getName().equals("发送邮件")) {
            emailWidget.getSutitleLayout().getSutitleTv().setText(way.getDescr());
            emailWidget.getTipTv().setText(way.getTips());
        } else if (way.getName().equals("自助")) {
            selfWidget.getSutitleLayout().getSutitleTv().setText(way.getDescr());
            selfWidget.getTipTv().setText(way.getTips());
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_attachment_choice_way;
    }

    public static class ViewHolder implements Serializable{

        private String key;

        private AttachTransferWay way;

        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public AttachTransferWay getWay() {
            return way;
        }

        public void setWay(AttachTransferWay way) {
            this.way = way;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
