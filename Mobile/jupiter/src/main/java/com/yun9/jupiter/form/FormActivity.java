package com.yun9.jupiter.form;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yun9.jupiter.R;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.form.model.FormCellBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.JsonUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leon on 15/5/25.
 */
public class FormActivity extends JupiterFragmentActivity {

    private JupiterTitleBarLayout titleBarLayout;

    private LinearLayout formLL;

    private ScrollView formScrollview;

    private TextView submitTv;

    private boolean edit;

    private Form form;

    private FormBean formBean;// 原始的表单数据模型

    private Map<Integer, IFormActivityCallback> activityCallbackMap;

    private int baseRequestCode = 10000;

    private FormCommand command;

    @Override
    protected int getContentView() {
        return R.layout.form_page;
    }

    public static void start(Activity activity, FormCommand command) {
        Intent intent = new Intent(activity, FormActivity.class);
        intent.putExtra(FormCommand.PARAM_COMMAND, command);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCallbackMap = new HashMap<>();
        command = (FormCommand) this.getIntent().getSerializableExtra(FormCommand.PARAM_COMMAND);
        String json =  command.getConfigJson();
        if (AssertValue.isNotNullAndNotEmpty(json)) {
            formBean = FormBean.fromJson(json);
        } else {
            formBean = command.getFormBean();
        }
        form = Form.getInstance(formBean);
        String valueJson =  command.getValueJson();
        if (AssertValue.isNotNullAndNotEmpty(valueJson)) {
            form.loadDataFromJson(valueJson);
        }
        this.initView();
        this.builder();
        edit = !formBean.isEditableWhenLoaded(); // 因为下面的toggleState会再次取反edit
        toggleState();
    }

    private void initView() {
        titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.titlebar);
        formLL = (LinearLayout) this.findViewById(R.id.formpage);
        formScrollview = (ScrollView) this.findViewById(R.id.form_sv);
        submitTv = (TextView) this.findViewById(R.id.submit);
    }

    public void edit(boolean edit) {
        this.edit = edit;
        if (AssertValue.isNotNullAndNotEmpty(form.getCells())) {
            for (FormCell formCell : form.getCells()) {
                if (AssertValue.isNotNull(formCell)) {
                    formCell.edit(edit);
                }
            }
        }
    }


    private void builder() {
        if (!AssertValue.isNotNull(form)) {
            return;
        }

        //设置返回键行为
        this.titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit) {
                    doCancel();
                    return;
                }
                if (form.getFormBean().isSaveFormWhenGoBack()) {
                    doComplete();
                    return;
                }
                new AlertDialog.Builder(FormActivity.this).setTitle("确认放弃修改？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                doCancel();

                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();
            }
        });

        //设置编辑按钮
        this.titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit) {
                    new AlertDialog.Builder(FormActivity.this).setTitle("确认放弃修改？")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击“确认”后的操作
                                    rebuildForm();
                                    toggleState();
                                }
                            })
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 点击“返回”后的操作,这里不设置没有任何操作
                                }
                            }).show();
                } else {
                    toggleState();
                }

            }
        });

        //设置标题
        this.titleBarLayout.getTitleTv().setText(form.getTitle());

        //添加cell
        if (AssertValue.isNotNullAndNotEmpty(form.getCells())) {
            for (FormCell formCell : form.getCells()) {
                View view = formCell.getCellView(this);

                if (AssertValue.isNotNull(view)) {
                    this.formLL.addView(view);
                }
            }
        }

        // 设置提交事件
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doComplete();
            }
        });

        if (form.getFormBean().isSaveFormWhenGoBack()) { // 如果返回即保存，则不需要保存按钮
            submitTv.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) formScrollview.getLayoutParams();
            params.addRule(RelativeLayout.ABOVE, 0);
        }

        formLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                formLL.requestFocus();
                return false;
            }
        });

    }

    private void gatherFormBean() {
        Intent intent = getIntent();
        intent.putExtra("form", form.getFormBean());
    }

    private void rebuildForm() {
        formLL.removeAllViews();
        form = Form.getInstance(formBean);
        //添加cell
        if (AssertValue.isNotNullAndNotEmpty(form.getCells())) {
            for (FormCell formCell : form.getCells()) {
                View view = formCell.getCellView(this);

                if (AssertValue.isNotNull(view)) {
                    this.formLL.addView(view);
                }
            }
        }
    }

    private void doComplete() {
        // 验证表单
        String message = form.validate();
        if (AssertValue.isNotNullAndNotEmpty(message)){
            this.showToast(message);
            return;
        }
        gatherFormBean();
        FormActivity.this.setResult(RESPONSE_CODE.COMPLETE, getIntent());
        FormActivity.this.finish();
    }

    private void doCancel() {
        FormActivity.this.setResult(RESPONSE_CODE.CANCEL, getIntent());
        FormActivity.this.finish();
    }

    public void toggleState() {
        FormActivity.this.edit(!edit);
        submitTv.setEnabled(edit);
        if (edit) {
            submitTv.setTextColor(getResources().getColor(R.color.red));
            FormActivity.this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_cancel);
        } else {
            submitTv.setTextColor(getResources().getColor(R.color.dim_foreground_disabled_material_dark));
            FormActivity.this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_edit);
        }
    }

    public int addActivityCallback(IFormActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        int requestCode = generateRequestCode();
        activityCallbackMap.put(requestCode, callback);
        return requestCode;
    }

    public void addActivityCallback(int requestCode,IFormActivityCallback callback) {
        if (callback == null) {
            callback = EMPTY_CALL_BACK;
        }
        activityCallbackMap.put(requestCode, callback);
    }

    private int generateRequestCode() {
        return ++baseRequestCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFormActivityCallback callback = activityCallbackMap.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
            activityCallbackMap.remove(requestCode);
        }
    }

    public class RESPONSE_CODE {
        public static final int COMPLETE = 100;
        public static final int CANCEL = 200;
    }

    private static final IFormActivityCallback EMPTY_CALL_BACK = new IFormActivityCallback() {
        @Override
        public void onActivityResult(int resultCode, Intent data) {

        }
    };

    public interface IFormActivityCallback {
        public void onActivityResult(int resultCode, Intent data);
    }

}
