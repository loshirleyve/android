package com.yun9.wservice.view.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.io.Serializable;

/**
 * Created by huangbinglong on 15/7/1.
 */
public class OrderAttachmentActivity extends JupiterFragmentActivity{

    public static final String PARAM_KEY_ORDER_ID = "orderid";

    @ViewInject(id=R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.order_attach_vitual_widget)
    private OrderAttachmentVitualWidget vitualWidget;

    @ViewInject(id=R.id.order_attach_material_widget)
    private OrderAttachmentMaterialWidget materialWidget;

    private String orderId;

    private JupiterCommand openMaterialCommand;

    private OrderAttachmentChoiceWayActivity.ViewHolder choicedMaterialWay;

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
    }

    private void loadData() {

        materialWidget.buildWithData();
        materialWidget.getTitleLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaterialCommand = new JupiterCommand() {};
                OrderAttachmentChoiceWayActivity.start(OrderAttachmentActivity.this,openMaterialCommand);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (openMaterialCommand !=null && openMaterialCommand.getRequestCode() == requestCode
                && resultCode == JupiterCommand.RESULT_CODE_OK){
            Serializable tag = data
                    .getSerializableExtra(OrderAttachmentChoiceWayActivity.CHOICE_WAY_HOLDER);
            if (tag != null){
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

    @Override
    protected int getContentView() {
        return R.layout.activity_order_attachment;
    }
}
