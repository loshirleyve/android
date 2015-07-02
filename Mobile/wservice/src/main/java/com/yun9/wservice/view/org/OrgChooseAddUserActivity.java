package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/2.
 */
public class OrgChooseAddUserActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.add_use_org_to)
    private JupiterRowStyleTitleLayout adduseorg;

    @ViewInject(id = R.id.add_use_phonebook_to)
    private JupiterRowStyleTitleLayout addusephonebook;

    @ViewInject(id=R.id.orgname)
    private TextView orgname;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private  List<User> users=new ArrayList<User>();

    private OrgChooseAddUserCommand command;




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
        initView();

    }


    public void initView()
    {
        orgname.setText(command.getOrgname());
        titleBarLayout.getTitleTv().setText("添加新成员");
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        titleBarLayout.getTitleRightTv().setText("保存");
        titleBarLayout.getTitleRight().setOnClickListener(onAddUserOrgClickListener);
        adduseorg.setOnClickListener(onChooseAddUserOrgClickListener);
        addusephonebook.setOnClickListener(onChooseAddUserPhoneClickListener);
    }

    private View.OnClickListener onChooseAddUserOrgClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            OrgCompositeCommand orgCompositeCommand = new OrgCompositeCommand().setEdit(true).setOnlyUsers(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK);
            if(AssertValue.isNotNullAndNotEmpty(users))
            {
                orgCompositeCommand.setSelectUsers(getSelectUserIds(users));
            }
            OrgCompositeActivity.start(OrgChooseAddUserActivity.this, orgCompositeCommand);
        }
    };




    private View.OnClickListener onChooseAddUserPhoneClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgPhoneUserActivity.start(OrgChooseAddUserActivity.this, new OrgPhoneUserCommand());

        }
    };




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        users.clear();
        if (requestCode == OrgCompositeCommand.REQUEST_CODE && resultCode == OrgCompositeCommand.RESULT_CODE_OK) {
            List<User> users = (List<User>) data.getSerializableExtra(OrgCompositeCommand.PARAM_USER);
            this.users=users;
        }
    }

    private View.OnClickListener onAddUserOrgClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNullAndNotEmpty(users))
            {
                Resource resource = resourceFactory.create("AddOrgCard");
                resource.param("orgid", command.getOrgid());
                resource.param("userid",sessionManager.getUser().getId());
                resource.param("addUserIdList",getSelectUserIds(users));
                resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                    @Override
                    public void onSuccess(Response response) {
                        Toast.makeText(OrgChooseAddUserActivity.this, "添加用户成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onFailure(Response response) {
                        Toast.makeText(OrgChooseAddUserActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinally(Response response) {

                    }
                });
            }
            else
                Toast.makeText(OrgChooseAddUserActivity.this, "请选择用户！", Toast.LENGTH_SHORT).show();
        }
    };


    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    public List<String> getSelectUserIds(List<User> users)
    {
        List<String> userids=new ArrayList<String>();
        if(AssertValue.isNotNullAndNotEmpty(users))
        {
            for (User user:users)
            {
                userids.add(user.getId());
            }
        }
        return userids;
    }
}
