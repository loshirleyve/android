package com.yun9.jupiter.form;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;

/**
 * Created by Leon on 15/5/25.
 */
public class FormActivity extends JupiterFragmentActivity {

    private JupiterTitleBarLayout titleBarLayout;

    private LinearLayout formLL;

    private FormAdapter formAdapter;

    private boolean edit = false;

    @Override
    protected int getContentView() {
        return R.layout.form_page;
    }

    public static void start(Context context, FormAdapter formAdapter) {
        Intent intent = new Intent(context, FormActivity.class);
        intent.putExtra("adapter", formAdapter);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formAdapter = (FormAdapter) this.getIntent().getSerializableExtra("adapter");

        this.initView();
        this.builder();
    }

    private void initView(){
        titleBarLayout = (JupiterTitleBarLayout) this.findViewById(R.id.titlebar);
        formLL = (LinearLayout) this.findViewById(R.id.formpage);
    }

    public void edit(boolean edit){
        this.edit = edit;
        if (AssertValue.isNotNullAndNotEmpty(formAdapter.getForm().getCells())){
            for(FormCell formCell:formAdapter.getForm().getCells()){
                if (AssertValue.isNotNull(formCell)){
                    formCell.edit(edit);
                }
            }
        }
    }


    private void builder() {
        if (!AssertValue.isNotNull(formAdapter)) {
            return;
        }

        //设置返回键行为
        this.titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormActivity.this.finish();
            }
        });

        //设置编辑按钮
        this.titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormActivity.this.showToast("编辑点击，进入编辑状态");
                FormActivity.this.edit(!edit);
                if (edit){
                    FormActivity.this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_complete);
                }else{
                    FormActivity.this.titleBarLayout.getTitleRightTv().setText(R.string.jupiter_edit);
                }

            }
        });

        //设置标题
        this.titleBarLayout.getTitleTv().setText(formAdapter.getForm().getTitle());

        //添加cell
        if (AssertValue.isNotNullAndNotEmpty(formAdapter.getForm().getCells())){
            for(FormCell formCell:formAdapter.getForm().getCells()){
                View view = formCell.getCellView(this);

                if(AssertValue.isNotNull(view)){
                   this.formLL.addView(view);
                }
            }
        }

    }

}
