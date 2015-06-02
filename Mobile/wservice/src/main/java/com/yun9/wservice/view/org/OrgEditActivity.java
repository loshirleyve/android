package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/6/2.
 */
public class OrgEditActivity extends JupiterFragmentActivity {

    private OrgEditCommand command;

    private boolean edit;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    public static void start(Activity activity, OrgEditCommand command) {
        Intent intent = new Intent(activity, OrgEditActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, OrgEditCommand.REQUEST_CODE);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_org_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgEditCommand) this.getIntent().getSerializableExtra("command");


        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);


        //检查是否进入编辑状态
        if (AssertValue.isNotNull(command) && command.isEdit()){
            this.setEdit(command.isEdit());
        }

        //如果没有orgid则进入新增状态

    }

    private void setEdit(boolean edit){
        this.edit = edit;

    }

    private void setAdd(){

    }

    private View.OnClickListener onCompleteClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
