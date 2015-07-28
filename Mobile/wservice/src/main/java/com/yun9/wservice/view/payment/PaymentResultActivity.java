package com.yun9.wservice.view.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.cache.CtrlCodeCache;
import com.yun9.jupiter.cache.FileCache;
import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheFile;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateFormatUtil;
import com.yun9.jupiter.util.ImageLoaderUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.enums.CtrlCodeDefNo;
import com.yun9.wservice.enums.RechargeState;
import com.yun9.wservice.model.FinanceCollects;
import com.yun9.wservice.task.UploadFileAsyncTask;
import com.yun9.wservice.view.doc.DocCompositeActivity;
import com.yun9.wservice.view.doc.DocCompositeCommand;
import com.yun9.wservice.view.myself.UserInfoCommand;
import com.yun9.wservice.view.product.ProductClassifyPopLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by huangbinglong on 7/3/15.
 */
public class PaymentResultActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.title_bar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id=R.id.state_name_tv)
    private TextView stateNameTv;

    @ViewInject(id=R.id.product_name_tv)
    private TextView productNameTv;

    @ViewInject(id=R.id.product_id_tv)
    private TextView productIdTv;

    @ViewInject(id=R.id.pay_way_ll)
    private LinearLayout payWayLl;

    @ViewInject(id=R.id.product_amount_tv)
    private TextView productAmountTv;

    private PopupWindow confirmWindow;

    private PaymentResultPopWidget popWidget;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private PaymentResultCommand command;

    private DocCompositeCommand compositeCommand;

    private String selectedImageId;

    private FileBean selectedFile;

    private String collectId;

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
        command = (PaymentResultCommand) getIntent().getSerializableExtra(JupiterCommand.PARAM_COMMAND);
        if (command.isPaymentDone()){
            setResult(JupiterCommand.RESULT_CODE_OK);
        }
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
        initPopWindow();
    }

    private void initPopWindow() {
        popWidget = new PaymentResultPopWidget(this);
        confirmWindow = new PopupWindow(popWidget, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        confirmWindow.setOnDismissListener(onDismissListener);
        confirmWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        confirmWindow.setOutsideTouchable(true);
        confirmWindow.setFocusable(true);
        confirmWindow.setAnimationStyle(R.style.bottom2top_top2bottom);
        popWidget.getUploadTv().setOnClickListener(openDocCompositeClickListener);
        popWidget.getUploadImageIv().setOnClickListener(openDocCompositeClickListener);
        popWidget.getConfirmTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPayArrive();
            }
        });
    }

    private void confirmPayArrive() {
        if (selectedFile != null
                && FileBean.FILE_STORAGE_TYPE_LOCAL.equals(selectedFile.getStorageType())){
            selectedFile.setUserid(sessionManager.getUser().getId());
            selectedFile.setInstid(sessionManager.getInst().getId());
            UploadFileAsyncTask uploadFileAsyncTask = new UploadFileAsyncTask(this,
                    Collections.singletonList(selectedFile));
            uploadFileAsyncTask.setCompImage(true);
            uploadFileAsyncTask.setOnFileUploadCallback(new UploadFileAsyncTask.OnFileUploadCallback() {
                @Override
                public void onPostExecute(List<FileBean> imgs) {
                    boolean upload = true;
                    if (AssertValue.isNotNull(imgs)) {
                        for (FileBean image : imgs) {
                            if (image.FILE_STORAGE_TYPE_LOCAL.equals(image.getStorageType())) {
                                upload = false;
                            } else {
                                selectedImageId = image.getId();
                            }
                        }
                    }

                    if (!upload) {
                        Toast.makeText(mContext, getString(R.string.new_image_upload_error), Toast.LENGTH_SHORT).show();
                    } else {
                        submit();
                    }
                }
            });
            uploadFileAsyncTask.execute();
        } else {
            submit();
        }

    }

    private void submit() {
        String content = popWidget.getConfirmContentEt().getText().toString();
        Resource resource = resourceFactory.create("UpdateCollectByArriveInfoService");
        resource.param("collectid",collectId);
        resource.param("arriveimgid",selectedImageId);
        resource.param("arrivetext",content);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                showToast("提交成功");
                loadData();
                confirmWindow.dismiss();
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

    private void loadData() {
        final ProgressDialog registerDialog = ProgressDialog.show(this, null, getResources().getString(R.string.app_wating), true);
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
                registerDialog.dismiss();
            }
        });
    }

    private void buildWithData(FinanceCollects financeCollects) {
        payWayLl.removeAllViews();
        if (financeCollects == null) {
            return;
        }
        stateNameTv.setText(CtrlCodeCache.getInstance().getCtrlcodeName(CtrlCodeDefNo.COLLECT_STATE,
                financeCollects.getCollectstate()));
        productNameTv.setText(financeCollects.getSourceInfo().getProductname());
        productIdTv.setText(financeCollects.getSourceInfo().getOrdersn());
        productAmountTv.setText(financeCollects.getSourceInfo().getOrderamount()+"元");
        if (financeCollects.getFinanceCollectList() != null){
            PaymentResultItemWidget widget;
            for (final FinanceCollects.FinanceCollect collect : financeCollects.getFinanceCollectList()){
                widget = new PaymentResultItemWidget(this);
                widget.getPayWayNameTv().setText(collect.getPaymodeName());
                widget.getPayTimeTv().setText(DateFormatUtil.format(
                        collect.getCreatedate(), StringPool.DATE_FORMAT_DATETIME
                ));
                widget.getPayAmountTv().setText(collect.getCollectamount()+"元");
                widget.getPayStateNameTv().setText(
                        CtrlCodeCache.getInstance().getCtrlcodeName(CtrlCodeDefNo.COLLECT_STATE,
                                collect.getState()));
                widget.getUserConfirmContentTv().setText(collect.getArrivetext());
                ImageLoaderUtil.getInstance(PaymentResultActivity.this)
                        .displayImage(collect.getArriveimgid(), widget.getUserUploadImageIv());
                if (AssertValue.isNotNullAndNotEmpty(collect.getClientarrivestate())){
                    widget.getConfirmPayTv().setText("修改凭据");
                }

                if (RechargeState.ARRIVE.equals(collect.getState())){
                    widget.getConfirmPayTv().setVisibility(View.GONE);
                }

                if (RechargeState.ARRIVE.equals(collect.getState())
                        && !AssertValue.isNotNullAndNotEmpty(collect.getArrivetext())
                        && !AssertValue.isNotNullAndNotEmpty(collect.getArriveimgid())){
                    widget.getExtraInfoLl().setVisibility(View.GONE);
                }
                // 添加确定支付事件
                widget.getConfirmPayTv().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collectId = collect.getId();
                        selectedImageId = collect.getArriveimgid();
                        ImageLoaderUtil.getInstance(PaymentResultActivity.this)
                                .displayImage(collect.getArriveimgid(), popWidget.getUploadImageIv(),
                                        R.drawable.upload_icon);
                        popWidget.getConfirmContentEt().setText(collect.getArrivetext());
                        if (confirmWindow != null) {
                            WindowManager.LayoutParams lp = PaymentResultActivity.this.getWindow().getAttributes();
                            lp.alpha = 0.4f;
                            PaymentResultActivity.this.getWindow().setAttributes(lp);
                            confirmWindow.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                        }
                    }
                });
                payWayLl.addView(widget);
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment_result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (compositeCommand != null
                && compositeCommand.getRequestCode() ==  requestCode
                && resultCode == JupiterCommand.RESULT_CODE_OK){
            List<FileBean> images = (List<FileBean>) data
                    .getSerializableExtra(DocCompositeCommand.PARAM_IMAGE);
            if (images != null
                    && images.size() > 0){
                selectedImageId = images.get(0).getId();
                selectedFile = images.get(0);
                ImageLoaderUtil.getInstance(PaymentResultActivity.this).displayImage(
                        selectedFile.getFilePath(),popWidget.getUploadImageIv()
                ,R.drawable.upload_icon);
            } else {
                selectedImageId = null;
                ImageLoaderUtil.getInstance(PaymentResultActivity.this).displayImage(
                        "drawable://"+R.drawable.upload_icon,popWidget.getUploadImageIv()
                );
            }
        }
    }

    private View.OnClickListener openDocCompositeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            compositeCommand = new DocCompositeCommand();
            if (AssertValue.isNotNullAndNotEmpty(selectedImageId)){
                FileBean fileBean = selectedFile;
                if (fileBean == null){
                    fileBean = new FileBean();
                    fileBean.setId(selectedImageId);
                    fileBean.setStorageType(FileBean.FILE_STORAGE_TYPE_YUN);
                }
                CacheFile file = FileCache.getInstance().getFile(selectedImageId);
                if (file != null){
                    fileBean.setFilePath(file.getThumbnailUrl());
                    fileBean.setThumbnailPath(file.getThumbnailUrl());
                    fileBean.setUrl(file.getUrl());
                }
                compositeCommand.setOnSelectImages(Collections.singletonList(fileBean));
            }
            compositeCommand.setEdit(true);
            compositeCommand.setCompleteType(DocCompositeCommand.COMPLETE_TYPE_CALLBACK);
            compositeCommand.setFileType(FileBean.FILE_TYPE_IMAGE);
            DocCompositeActivity.start(PaymentResultActivity.this, compositeCommand);
        }
    };

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = PaymentResultActivity.this.getWindow().getAttributes();
            lp.alpha = 1f;
            PaymentResultActivity.this.getWindow().setAttributes(lp);
        }
    };
}
