package com.yun9.wservice.view.order;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.CustomCallbackActivity;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.AttachmentInputType;
import com.yun9.wservice.enums.AttachmentTransferType;
import com.yun9.wservice.model.Attachment;
import com.yun9.wservice.model.wrapper.AttachmentWrapper;
import com.yun9.wservice.task.UploadFileAsyncTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
        final ProgressDialog registerDialog =
                ProgressDialog.show(this, null,
                        getResources().getString(R.string.app_wating), true);
        Resource resource = resourceFactory.create("QueryOrderAttachmentService");
        resource.param("orderid", orderId);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                AttachmentWrapper wrapper = (AttachmentWrapper) response.getPayload();
                List<Attachment> attachments = wrapper.getOrderAttachments();
                buildWithData(attachments);
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

    private void buildWithData(List<Attachment> attachments) {
        if (attachments == null
                || attachments.size() == 0){
            return;
        }
        List<Attachment> materialAttachs = new ArrayList<>();
        List<Attachment> vitualAttachs = new ArrayList<>();
        for (Attachment attachment : attachments) {
            if (AttachmentTransferType.ENTITY.equals(attachment.getTransfertype())){
                materialAttachs.add(attachment);
            } else if (AttachmentTransferType.DOC.equals(attachment.getTransfertype())) {
                vitualAttachs.add(attachment);
            }
        }
        if (materialAttachs.size() == 0){
            materialWidget.setVisibility(View.GONE);
        } else {
            materialWidget.buildWithData(materialAttachs);
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
        if (vitualAttachs.size() == 0){
            vitualWidget.setVisibility(View.GONE);
        } else {
            vitualWidget.buildWithData(vitualAttachs);
        }

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
        final List<OrderAttachmentVitualWidget.Attachement> attachements = vitualWidget.getValue();
        if (attachements == null){
            return;
        }
       List<FileBean> fileBeans = vitualWidget.getFileBeans();
        final List<String> filePaths = toFilePath(fileBeans);
        if (fileBeans.size() > 0){
            UploadFileAsyncTask uploadFileAsyncTask = new UploadFileAsyncTask(this,fileBeans);
            uploadFileAsyncTask.setCompImage(true);
            uploadFileAsyncTask.setOnFileUploadCallback(new UploadFileAsyncTask.OnFileUploadCallback() {
                @Override
                public void onPostExecute(List<FileBean> imgs) {
                    boolean upload = true;
                    if (AssertValue.isNotNull(imgs)) {
                        FileBean image;
                        for (int i = 0; i < imgs.size();i++) {
                            image = imgs.get(i);
                            if (image.FILE_STORAGE_TYPE_LOCAL.equals(image.getStorageType())) {
                                upload = false;
                            } else {
                                changeRealFileId(attachements,filePaths.get(i),image.getId());
                            }
                        }
                    }

                    if (!upload) {
                        showToast(R.string.new_image_upload_error);
                    } else {
                        submit(attachements);
                    }
                }
            });
            uploadFileAsyncTask.execute();
        } else {
            submit(attachements);
        }
    }

    private List<String> toFilePath(List<FileBean> fileBeans) {
        List<String> filePaths = new ArrayList<>();
        for (FileBean fileBean : fileBeans){
            filePaths.add(fileBean.getFilePath());
        }
        return filePaths;
    }

    private void changeRealFileId(List<OrderAttachmentVitualWidget.Attachement> attachements,
                             String fakeId,String realId) {
        for (OrderAttachmentVitualWidget.Attachement attachement : attachements){
            if (fakeId.equals(attachement.getInputvalue())){
                attachement.setInputvalue(realId);
            }
        }
    }

    private void submit(List<OrderAttachmentVitualWidget.Attachement> attachements) {
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
