package com.yun9.jupiter.form;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.model.FormBean;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leon on 15/5/25.
 */
public class FormActivity extends JupiterFragmentActivity {

    private JupiterTitleBarLayout titleBarLayout;

    private LinearLayout formLL;

    private TextView submitTv;

    private boolean edit = true;

    private Form form;

    private Map<Integer,IFormActivityCallback> activityCallbackMap;

    private int baseRequestCode = REQUEST_CODE.NORMAL;

    @Override
    protected int getContentView() {
        return R.layout.form_page;
    }

    public static void start(Activity activity,int requestCode, FormBean formBan) {
        Intent intent = new Intent(activity, FormActivity.class);
        intent.putExtra("form", formBan);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCallbackMap = new HashMap<>();
        FormBean formBean = (FormBean) this.getIntent().getSerializableExtra("form");
        form = new Form(formBean);
        this.initView();
        this.builder();
        toggleState();
    }

    private void initView(){
        titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.titlebar);
        formLL = (LinearLayout) this.findViewById(R.id.formpage);
        submitTv = (TextView) this.findViewById(R.id.submit);
    }

    public void edit(boolean edit){
        this.edit = edit;
        if (AssertValue.isNotNullAndNotEmpty(form.getCells())){
            for(FormCell formCell:form.getCells()){
                if (AssertValue.isNotNull(formCell)){
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
                FormActivity.this.setResult(RESPONSE_CODE.CANCEL,getIntent());
                FormActivity.this.finish();
            }
        });

        //设置编辑按钮
        this.titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               toggleState();

            }
        });

        //设置标题
        this.titleBarLayout.getTitleTv().setText(form.getTitle());

        //添加cell
        if (AssertValue.isNotNullAndNotEmpty(form.getCells())){
            for(FormCell formCell:form.getCells()){
                View view = formCell.getCellView(this);

                if(AssertValue.isNotNull(view)){
                   this.formLL.addView(view);
                }
            }
        }

        // 设置提交事件
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("form",form.getFormBean());
                FormActivity.this.setResult(RESPONSE_CODE.COMPLETE, getIntent());
                FormActivity.this.finish();
            }
        });

    }

    public void toggleState() {
        FormActivity.this.edit(!edit);
        if (edit){
            FormActivity.this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_complete);
        }else{
            FormActivity.this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_edit);
        }
    }

    public int addActivityCallback(IFormActivityCallback callback) {
        int requestCode = generateRequestCode();
        activityCallbackMap.put(requestCode, callback);
        return requestCode;
    }

    public int generateRequestCode() {
        return ++baseRequestCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IFormActivityCallback callback = activityCallbackMap.get(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode,data);
            activityCallbackMap.remove(requestCode);
        }
    }

    public class REQUEST_CODE {
        public static final int NORMAL = 100;
    }

    public class RESPONSE_CODE {
        public static final int COMPLETE = 100;
        public static final int CANCEL = 200;
    }

    public interface IFormActivityCallback{
        public void onActivityResult(int resultCode, Intent data);
    }

}
