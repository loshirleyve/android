package com.yun9.wservice.view.inst;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by li on 2015/7/8.
 */
public class InitInstActivity extends JupiterFragmentActivity {
    private String staffNumCategory;
    private InstCommand command;
    private String userid;
    @BeanInject
    SessionManager sessionManager;
    @BeanInject
    ResourceFactory resourceFactory;
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

        command = (InstCommand) this.getIntent().getSerializableExtra(InstCommand.PARAM_COMMAND);
        if(!AssertValue.isNotNull(command) || !AssertValue.isNotNullAndNotEmpty(command.getUserid())){
            userid = sessionManager.getUser().getId();
        }else {
            userid = command.getUserid();
        }
        queryUserByIdAndSetName(userid);

        initInstTitle.getTitleLeft().setOnClickListener(onBackClickListener);
        staffNumLayout.setOnClickListener(onStaffNumClickListener);
        initInstTitle.getTitleRightTv().setOnClickListener(onInitInstClickListener);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_init_inst;
    }

    public boolean isInstNo(String instNo){
        String str = "^[a-zA-Z0-9]{4,8}$";
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == StaffNumCommand.RESULT_CODE_OK){
            staffNumLayout.getSutitleTv().setVisibility(View.VISIBLE);
            staffNumCategory = (String) data.getSerializableExtra(StaffNumCommand.STAFF_NUM_CATEGORY);
            switch (staffNumCategory){
                case "miniature":
                    staffNumLayout.getSutitleTv().setText(getString(R.string.staff_num_category1));
                    break;
                case "small":
                    staffNumLayout.getSutitleTv().setText(getString(R.string.staff_num_category2));
                    break;
                case "medium":
                    staffNumLayout.getSutitleTv().setText(getString(R.string.staff_num_category3));
                    break;
                case "large":
                    staffNumLayout.getSutitleTv().setText(getString(R.string.staff_num_category4));
                    break;
            }
        }
    }

    private void queryUserByIdAndSetName(final String userid){
        Resource resource = resourceFactory.create("QueryUserInfoByIdService");
        resource.param("userid", userid);
        resource.invok(new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                User user = (User) response.getPayload();
                if (AssertValue.isNotNullAndNotEmpty(user.getName()) && !user.getName().equals("none")) {
                    managerLayout.getSutitleTv().setText(user.getName());
                }else {
                    managerLayout.getSutitleTv().setText(user.getNo());
                }
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    private View.OnClickListener onInitInstClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!AssertValue.isNotNullAndNotEmpty(instNoEt.getText().toString())){
                Toast.makeText(mContext, getString(R.string.inst_no_input_notice), Toast.LENGTH_SHORT).show();
            }else if(!AssertValue.isNotNullAndNotEmpty(companyNameEt.getText().toString())){
                Toast.makeText(mContext, getString(R.string.company_name_input_notice), Toast.LENGTH_SHORT).show();
            }else if(!AssertValue.isNotNullAndNotEmpty(staffNumLayout.getSutitleTv().getText().toString())){
                Toast.makeText(mContext, getString(R.string.staff_num_input_notice), Toast.LENGTH_SHORT).show();
            }else if(!isInstNo(instNoEt.getText().toString())){
                Toast.makeText(mContext, getString(R.string.inst_no_right_input_notice), Toast.LENGTH_SHORT).show();
            }
            final Resource resource = resourceFactory.create("InstInit");
            resource.param("userid", userid)
                    .param("companyName", companyNameEt.getText().toString())
                    .param("companyNo", instNoEt.getText().toString())
                    .param("companyScale", staffNumCategory)
                    .param("homePageUrl", "http://origin.yun.com")
                    .param("logoImg", "logoImg");
            final ProgressDialog progressDialog = ProgressDialog.show(InitInstActivity.this, null, getString(R.string.app_wating), true);
            resource.invok(new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    Toast.makeText(mContext, getString(R.string.init_inst_success), Toast.LENGTH_SHORT).show();
                    setResult(InstCommand.RESULT_CODE_OK);
                    finish();
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    progressDialog.dismiss();
                }
            });
        }
    };
}
