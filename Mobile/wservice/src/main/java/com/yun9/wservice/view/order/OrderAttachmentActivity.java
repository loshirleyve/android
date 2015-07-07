package com.yun9.wservice.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbinglong on 15/7/1.
 */
public class OrderAttachmentActivity extends CustomCallbackActivity{

    public static final String PARAM_KEY_ORDER_ID = "orderid";

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.order_attach_vitual_widget)
    private OrderAttachmentVitualWidget vitualWidget;

    @ViewInject(id=R.id.order_attach_material_widget)
    private OrderAttachmentMaterialWidget materialWidget;

    @ViewInject(id=R.id.confirm_ll)
    private LinearLayout confirmLl;

    @BeanInject
    private ResourceFactory resourceFactory;

    private String orderId;

    private JupiterCommand openMaterialCommand;

    private OrderAttachmentChoiceWayActivity.ViewHolder choicedMaterialWay;


    private Map<Integer, IActivityCallback> activityCallbackMap;

    private int baseRequestCode = 10000;

    public static void start(Context context,String orderId) {
        Intent intent = new Intent(context,OrderAttachmentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PARAM_KEY_ORDER_ID,orderId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCallbackMap = new HashMap<>();
        orderId = getIntent().getStringExtra(PARAM_KEY_ORDER_ID);
        buildView();
        loadData();
    }

    private void buildView() {
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderAttachmentActivity.this.finish();
            }
        });

        confirmLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAttach();
            }
        });
    }

    private void loadData() {

        materialWidget.buildWithData();
        materialWidget.getTitleLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaterialCommand = new JupiterCommand() {
                };
                addActivityCallback(openMaterialCommand.getRequestCode(), new IActivityCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        if (resultCode == JupiterCommand.RESULT_CODE_OK) {
                            Serializable tag = data
                                    .getSerializableExtra(OrderAttachmentChoiceWayActivity.CHOICE_WAY_HOLDER);
                            if (tag != null) {
                                choicedMaterialWay =
                                        (OrderAttachmentChoiceWayActivity.ViewHolder) tag;
                                materialWidget.getTitleLayout().getHotNitoceTV().setTextColor(getResources()
                                        .getColor(R.color.title_color));
                                materialWidget.getTitleLayout().getHotNitoceTV().setText(choicedMaterialWay
                                        .getWay().getName());
                            } else {
                                choicedMaterialWay = null;
                                materialWidget.getTitleLayout().getHotNitoceTV().setTextColor(getResources()
                                        .getColor(R.color.black));
                                materialWidget.getTitleLayout().getHotNitoceTV().setText("未选择");
                            }
                        }
                    }
                });
                OrderAttachmentChoiceWayActivity.start(OrderAttachmentActivity.this, openMaterialCommand);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IActivityCallback callback = activityCallbackMap.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
            activityCallbackMap.remove(requestCode);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_attachment;
    }

    /**
     * 提交资料
     */
    private void submitAttach() {
        if (choicedMaterialWay == null){
            showToast("请选择实物资料提交方式");
            return;
        }
        List<OrderAttachmentVitualWidget.Attachement> attachements = vitualWidget.getValue();
        if (attachements == null){
            return;
        }
        Resource resource = resourceFactory.create("UpdateOrderAttachmentService");
        resource.param("attachkeys",attachements);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showToast("资料提交成功");
                OrderAttachmentActivity.this.finish();
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

    private int generateRequestCode() {
        return ++baseRequestCode;
    }

    public int addActivityCallback(IActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        int requestCode = generateRequestCode();
        activityCallbackMap.put(requestCode, callback);
        return requestCode;
    }

    public void addActivityCallback(int requestCode,IActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        activityCallbackMap.put(requestCode, callback);
    }

    private static final IActivityCallback EMPTY_CALL_BACK = new IActivityCallback() {
        @Override
        public void onActivityResult(int resultCode, Intent data) {

        }
    };
}
