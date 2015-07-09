package com.yun9.wservice.view.org;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.cache.UserCache;
import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.CacheUser;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.StringUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterSearchInputLayout;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrgDetailInfoBean;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/6/2.
 */
public class OrgEditActivity extends JupiterFragmentActivity {

    private OrgEditCommand command;

    private boolean edit;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    private BasicJupiterEditAdapter useradapter;
    private BasicJupiterEditAdapter orgadapter;

    private List<JupiterEditableView> useritemList;
    private List<JupiterEditableView> orgitemList;
    private List<JupiterEditableView> textwatchorgitemList;

    @ViewInject(id = R.id.parentorgname)
    private EditText parentorgname;

    @ViewInject(id = R.id.neworg)
    private EditText neworg;


    @ViewInject(id = R.id.edit_ico_users)
    private JupiterEditIco jupiterEdituserIco;

    @ViewInject(id = R.id.edit_ico_orgs)
    private JupiterEditIco jupiterEditorgIco;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private String userid;

    private String instid;

    private OrgDetailInfoBean bean;

    private int requestcode;

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

    //初始化控件，判断是否存在orgid有的话就查询出org的信息，没有就是新增组织的页面
    public void initView() {
        jupiterEdituserIco.getRowStyleSutitleLayout().getTitleTV().setText(R.string.org_user_list);
        jupiterEditorgIco.getRowStyleSutitleLayout().getTitleTV().setText(R.string.children_org);
        titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgEditActivity.this.setEdit(!edit);
            }
        });
        titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        titleBarLayout.getTitleRight().setOnClickListener(addOrgClickListener);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        parentorgname.setText(command.getParentorgname() == null ? sessionManager.getInst().getName() : command.getParentorgname());

        //如果没有orgid则进入新增状态
        if (!AssertValue.isNotNull(command) || !AssertValue.isNotNullAndNotEmpty(command.getOrgid())) {
            titleBarLayout.getTitleTv().setText(R.string.custom_org);
            jupiterEdituserIco.setVisibility(View.GONE);
            jupiterEditorgIco.setVisibility(View.GONE);
        } else {
            jupiterEdituserIco.setVisibility(View.VISIBLE);
            jupiterEditorgIco.setVisibility(View.VISIBLE);
            getOrgDetails();
        }

        //检查是否进入编辑状态

        if (AssertValue.isNotNull(command)) {
            setEdit(command.isEdit());
        }
        useritemList = new ArrayList<JupiterEditableView>();
        orgitemList = new ArrayList<JupiterEditableView>();
        textwatchorgitemList = new ArrayList<JupiterEditableView>();
        setupEditIco();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == OrgChooseAddUserCommand.REQUEST_CODE && resultCode == OrgChooseAddUserCommand.RESULT_CODE_OK) || (requestCode == OrgEditCommand.REQUEST_CODE && resultCode == OrgEditCommand.RESULT_CODE_OK)) {
            requestcode = OrgEditCommand.RESULT_CODE_OK;
            initView();
        }
    }

    //界面的+图片可以添加用户和组织的控件
    private void setupEditIco() {
        JupiterTextIco useritem = new JupiterTextIcoWithoutCorner(getApplicationContext());
        useritem.setTitle(getResources().getString(R.string.org_add_newuser));
        useritem.setImage("drawable://" + com.yun9.jupiter.R.drawable.add_user);
        useritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgChooseAddUserActivity.start(OrgEditActivity.this, new OrgChooseAddUserCommand().setOrgid(bean.getId()).setOrgname(bean.getName()));
            }
        });
        useritemList.add(useritem);
        useradapter = new BasicJupiterEditAdapter(useritemList);
        useradapter.edit(true);
        jupiterEdituserIco.setAdapter(useradapter);


        JupiterTextIco orgitem = new JupiterTextIcoWithoutCorner(getApplicationContext());
        orgitem.setTitle(getResources().getString(R.string.org_add_neworg));
        orgitem.setImage("drawable://" + com.yun9.jupiter.R.drawable.add_user);
        orgitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgEditActivity.start(OrgEditActivity.this, new OrgEditCommand().setEdit(true).setParentorgname(bean.getName()).setParentorgid(bean.getId()).setDimType(bean.getType()));
            }
        });
        orgitemList.add(orgitem);
        orgadapter = new BasicJupiterEditAdapter(orgitemList);
        orgadapter.edit(true);
        jupiterEditorgIco.setAdapter(orgadapter);
    }

    //获取组织的详细信息
    private void getOrgDetails() {
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getOrgid())) {
            final ProgressDialog registerDialog = ProgressDialog.show(OrgEditActivity.this, null, getResources().getString(R.string.app_wating), true);
            Resource resource = resourceFactory.create("QueryOrgDetailsByOrgid");
            resource.param("orgid", command.getOrgid());
            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    bean = (OrgDetailInfoBean) response.getPayload();
                    if (bean != null)
                        builder(bean);
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(OrgEditActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    registerDialog.dismiss();
                }
            });
        }
    }

    //把查询的组织信息，构建到用户和组织控件显示
    private void builder(OrgDetailInfoBean bean) {
        neworg.setText(bean.getName());
        titleBarLayout.getTitleTv().setText(bean.getName());
        String sutitle = "";
        if (AssertValue.isNotNullAndNotEmpty(bean.getUsers())) {
            sutitle = "成员" + bean.getUsers().size() + " 管理员:" + bean.getOwnerName();
            titleBarLayout.getTitleSutitleTv().setVisibility(View.VISIBLE);
            this.titleBarLayout.getTitleSutitleTv().setText(sutitle);
            for (User user : bean.getUsers()) {
                final JupiterTextIco useritem = new JupiterTextIco(getApplicationContext());
                useritem.setTitle(user.getName());
                useritem.hideCorner();
                useritem.setImage("drawable://" + R.drawable.user_head);
                CacheUser cacheUser = UserCache.getInstance().getUser(user.getId());
                if (AssertValue.isNotNull(cacheUser) && AssertValue.isNotNullAndNotEmpty(cacheUser.getUrl())) {
                    useritem.setImage(cacheUser.getUrl());
                }
                useritem.setTag(user.getId());
                useritem.setCornerImage(com.yun9.jupiter.R.drawable.icn_delete);
                useritem.getBadgeView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteUserItem(useritem);
                    }
                });
                useritem.edit(true);
                useritemList.add(useritem);
                useradapter.notifyDataSetChanged();
            }
        }
        if (AssertValue.isNotNullAndNotEmpty(bean.getChildren())) {
            for (Org org : bean.getChildren()) {
                final JupiterTextIcoWithoutCorner orgitem = new JupiterTextIcoWithoutCorner(getApplicationContext());
                orgitem.setTitle(org.getName());
                orgitem.hideCorner();
                orgitem.setImage("drawable://" + R.drawable.user_group);
                orgitem.setTag(org.getId());
                orgitemList.add(orgitem);
                orgadapter.notifyDataSetChanged();
            }
        }
    }

    //添加组织的方法
    private void addOrg(String orgname) {
        Resource resource = resourceFactory.create("AddOrg");
        resource.param("instid", instid);
        resource.param("userid", userid);
        resource.param("name", orgname);
        resource.param("type", command.getDimType());
        resource.param("parentid", command.getParentorgid() == null ? "0" : command.getParentorgid());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Org org = (Org) response.getPayload();
                command = new OrgEditCommand().setOrgid(org.getId());
                setResult(OrgEditCommand.RESULT_CODE_OK);
                initView();
                Toast.makeText(OrgEditActivity.this, R.string.add_org_success_tip, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrgEditActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    //修改组织名称的方法
    private void updateOrgName(String orgname) {
        Resource resource = resourceFactory.create("UpdateOrg");
        resource.param("orgid", command.getOrgid());
        resource.param("userid", userid);
        resource.param("name", orgname);
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                titleBarLayout.getTitleTv().setText(neworg.getText().toString());
                Toast.makeText(OrgEditActivity.this, R.string.update_org_success_tip, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrgEditActivity.this,  response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }

    //删除用户的一项的方法
    private void deleteUserItem(JupiterEditableView item) {
        String resourceid = (String) item.getTag();
        Resource resource = resourceFactory.create("RemoveOrgCardByUserId");
        resource.param("userid", userid);
        resource.param("resourceid", resourceid);
        resource.param("orgid", command.getOrgid());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(OrgEditActivity.this, R.string.delete_orguser_success_tip, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrgEditActivity.this,response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
        useritemList.remove(item);
        useradapter.notifyDataSetChanged();

    }

    //添加组织的事件
    private View.OnClickListener addOrgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (edit == false) {
                String orgname = neworg.getText().toString();
                if (AssertValue.isNotNullAndNotEmpty(orgname)) {
                    if (AssertValue.isNotNullAndNotEmpty(command.getOrgid()))
                        updateOrgName(orgname);
                    else
                        addOrg(orgname);
                } else
                    Toast.makeText(OrgEditActivity.this, R.string.add_org_tip, Toast.LENGTH_SHORT).show();
            }
            setEdit(edit);
        }
    };


    private void setEdit(boolean edit) {
        this.edit = edit;
        if (this.edit) {
            this.edit = false;
            neworg.setEnabled(true);
            titleBarLayout.getTitleRightTv().setText(R.string.app_complete);
        } else {
            this.edit = true;
            neworg.setEnabled(false);
            titleBarLayout.getTitleRightTv().setText(R.string.app_edit);
        }
    }


    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (requestcode == OrgEditCommand.RESULT_CODE_OK)
                setResult(requestcode);
            finish();
        }
    };
}
