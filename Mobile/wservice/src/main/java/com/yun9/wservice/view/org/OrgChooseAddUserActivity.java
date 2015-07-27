package com.yun9.wservice.view.org;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.command.JupiterCommand;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrgDetailInfoBean;
import com.yun9.wservice.view.dynamic.OrgAndUserBean;

import junit.framework.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/2.
 */
public class OrgChooseAddUserActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.add_use_org_to)
    private JupiterRowStyleSutitleLayout adduseorg;

    @ViewInject(id = R.id.add_use_phonebook_to)
    private JupiterRowStyleSutitleLayout addusephonebook;

    @ViewInject(id = R.id.orgname)
    private TextView orgname;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private List<User> users = new ArrayList<User>();

    private OrgChooseAddUserCommand command;

    private String userid;

    private String instid;


    public static void start(Activity activity, OrgChooseAddUserCommand command) {
        Intent intent = new Intent(activity, OrgChooseAddUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, OrgChooseAddUserCommand.REQUEST_CODE);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_choose_adduser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgChooseAddUserCommand) this.getIntent().getSerializableExtra("command");
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getUserid())) {
            userid = command.getUserid();
        }

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getInstid())) {
            instid = command.getInstid();
        }
        if (!AssertValue.isNotNullAndNotEmpty(userid)) {
            userid = sessionManager.getUser().getId();
        }

        if (!AssertValue.isNotNullAndNotEmpty(instid)) {
            instid = sessionManager.getInst().getId();
        }
        initView();

    }


    public void initView() {
        orgname.setText(command.getOrgname());
        titleBarLayout.getTitleTv().setText(R.string.org_add_newuser);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        titleBarLayout.getTitleRightTv().setText(R.string.app_complete);
        adduseorg.getSutitleTv().setText("");
        addusephonebook.getSutitleTv().setText("");
        titleBarLayout.getTitleRight().setOnClickListener(onAddUserOrgClickListener);
        adduseorg.setOnClickListener(onChooseAddUserOrgClickListener);
        addusephonebook.setOnClickListener(onChooseAddUserPhoneClickListener);
    }

    private View.OnClickListener onChooseAddUserOrgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            OrgCompositeCommand orgCompositeCommand = new OrgCompositeCommand().setEdit(true).setOnlyUsers(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK);
            if (AssertValue.isNotNullAndNotEmpty(users)) {
                orgCompositeCommand.setSelectUsers(getSelectUserIds(users));
            }
            OrgCompositeActivity.start(OrgChooseAddUserActivity.this, orgCompositeCommand);
        }
    };


    private View.OnClickListener onChooseAddUserPhoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgPhoneUserActivity.start(OrgChooseAddUserActivity.this, new OrgPhoneUserCommand().setOrgid(command.getOrgid()));

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        users.clear();
        if (requestCode == OrgCompositeCommand.REQUEST_CODE && resultCode == OrgCompositeCommand.RESULT_CODE_OK) {
            users = (List<User>) data.getSerializableExtra(OrgCompositeCommand.PARAM_USER);
            if (AssertValue.isNotNullAndNotEmpty(users)) {
                String userstring = "";
                for (User user : users) {
                    userstring += user.getName() + " ";
                }
                adduseorg.getSutitleTv().setText(userstring);
            }
        }
        if (resultCode == JupiterCommand.RESULT_CODE_OK){
            setResult(JupiterCommand.RESULT_CODE_OK);
        }
    }

    private View.OnClickListener onAddUserOrgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNullAndNotEmpty(users)) {
                final ProgressDialog registerDialog = ProgressDialog.show(OrgChooseAddUserActivity.this, null, getResources().getString(R.string.app_wating), true);
                Resource resource = resourceFactory.create("AddOrgCard");
                resource.param("orgid", command.getOrgid());
                resource.param("userid", userid);
                resource.param("addUserIdList", getSelectUserIds(users));
                resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                    @Override
                    public void onSuccess(Response response) {
                        Toast.makeText(OrgChooseAddUserActivity.this, R.string.add_orguser_success_tip, Toast.LENGTH_SHORT).show();
                        setResult(OrgChooseAddUserCommand.RESULT_CODE_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(Response response) {
                        Toast.makeText(OrgChooseAddUserActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinally(Response response) {
                        registerDialog.dismiss();
                    }
                });
            } else
                Toast.makeText(OrgChooseAddUserActivity.this, R.string.choose_add_use_to, Toast.LENGTH_SHORT).show();
        }
    };


    private List<String> getSelectUserIds(List<User> users) {
        List<String> userids = new ArrayList<String>();
        if (AssertValue.isNotNullAndNotEmpty(users)) {
            for (User user : users) {
                userids.add(user.getId());
            }
        }
        return userids;
    }

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setResult(OrgChooseAddUserCommand.RESULT_CODE_CANCEL);
            finish();
        }
    };


}
