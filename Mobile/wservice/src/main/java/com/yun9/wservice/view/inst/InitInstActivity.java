package com.yun9.wservice.view.inst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by li on 2015/7/8.
 */
public class InitInstActivity extends JupiterFragmentActivity {
    @ViewInject(id = R.id.instNoEt)
    private EditText instNoEt;
    @ViewInject(id = R.id.companyNameEt)
    private EditText companyNameEt;
    @ViewInject(id = R.id.staffNum)
    private JupiterRowStyleSutitleLayout staffNumLayout;
    @ViewInject(id = R.id.manager)
    private JupiterRowStyleSutitleLayout managerLayout;
    @ViewInject(id = R.id.initInstTitle)
    private JupiterTitleBarLayout initInstTitle;

    public static void start(Activity activity, InstCommand command){
        Intent intent = new Intent(activity, InitInstActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(InstCommand.PARAM_COMMAND, command);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        managerLayout.getHotNitoceTV().setText("1");
        initInstTitle.getTitleLeft().setOnClickListener(onBackClickListener);
        staffNumLayout.setOnClickListener(onStaffNumClickListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_init_inst;
    }

    public boolean isInstNo(String instNo){
        String str = "^[a-zA-Z0-9]{6,8}$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(instNo);
        return matcher.matches();
    }

    private View.OnClickListener onBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private View.OnClickListener onStaffNumClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StaffNumActivity.start(InitInstActivity.this, new StaffNumCommand());
        }
    };
}
