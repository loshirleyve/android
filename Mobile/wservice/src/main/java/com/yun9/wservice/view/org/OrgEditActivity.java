package com.yun9.wservice.view.org;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
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
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.util.StringUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.BasicJupiterEditAdapter;
import com.yun9.jupiter.widget.JupiterEditIco;
import com.yun9.jupiter.widget.JupiterEditableView;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterSearchInputLayout;
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithCorner;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrgDetailInfoBean;
import com.yun9.wservice.view.dynamic.NewDynamicActivity;
import com.yun9.wservice.view.dynamic.NewDynamicCommand;
import com.yun9.wservice.view.store.SelectCityLayout;

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

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardButton;

    @ViewInject(id = R.id.edit_ico_users)
    private JupiterEditIco jupiterEdituserIco;

    @ViewInject(id = R.id.edit_ico_orgs)
    private JupiterEditIco jupiterEditorgIco;

    private OrgUserOperateLayout orgUserOperateLayout;

    private PopupWindow orgUserOperatePopupW;
    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private String userid;

    private String instid;

    private OrgDetailInfoBean bean;

    private ProgressDialog registerDialog;

    private int requestcode;

    private int orgUserRole = 2;

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
        registerDialog = ProgressDialog.show(OrgEditActivity.this, null, getResources().getString(R.string.app_wating), true);
        jupiterEdituserIco.getRowStyleSutitleLayout().getTitleTV().setText(R.string.org_user_list);
        jupiterEditorgIco.getRowStyleSutitleLayout().getTitleTV().setText(R.string.children_org);
        titleBarLayout.getTitleRightTv().setVisibility(View.GONE);
        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);
        sendMsgCardButton.setOnClickListener(onSendMsgClickListener);
        parentorgname.setText(command.getParentorgname() == null ? sessionManager.getInst().getName() : command.getParentorgname());
        useritemList = new ArrayList<JupiterEditableView>();
        orgitemList = new ArrayList<JupiterEditableView>();
        textwatchorgitemList = new ArrayList<JupiterEditableView>();
        useradapter = new BasicJupiterEditAdapter(useritemList);
        orgadapter = new BasicJupiterEditAdapter(orgitemList);
        jupiterEdituserIco.setAdapter(useradapter);
        jupiterEditorgIco.setAdapter(orgadapter);
        //如果没有orgid则进入新增状态
        if (!AssertValue.isNotNull(command) || !AssertValue.isNotNullAndNotEmpty(command.getOrgid())) {
            titleBarLayout.getTitleTv().setText(R.string.custom_org);
            jupiterEdituserIco.setVisibility(View.GONE);
            jupiterEditorgIco.setVisibility(View.GONE);
            setEdit(command.isEdit());
        } else {
            jupiterEdituserIco.setVisibility(View.VISIBLE);
            jupiterEditorgIco.setVisibility(View.VISIBLE);
            getOrgDetails();
        }
    }

    public void buildInfo() {
        neworg.setText(bean.getName());
        titleBarLayout.getTitleTv().setText(bean.getName());
        String sutitle = "";
        getCurrentUserOrgRole(bean.getUsers());
        sutitle = "成员" + bean.getUsers().size() + "管理员" + bean.getOwnerName();
        titleBarLayout.getTitleSutitleTv().setVisibility(View.VISIBLE);
        this.titleBarLayout.getTitleSutitleTv().setText(sutitle);
        setEdit(command.isEdit());
    }

    private void setEdit(boolean edit) {
        this.edit = edit;
        if (orgUserRole != 2)
            titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        if (this.edit) {
            neworg.setEnabled(true);
            sendMsgCardButton.setVisibility(View.GONE);
            titleBarLayout.getTitleRightTv().setText(R.string.app_complete);
            if (AssertValue.isNotNull(bean) && orgUserRole != 2) {
                useritemList.clear();
                orgitemList.clear();
                setupEditIco();
                builderUserOrg();
            }
            titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String orgname = neworg.getText().toString();
                    if (AssertValue.isNotNullAndNotEmpty(orgname)) {
                        if (AssertValue.isNotNull(bean) && !orgname.equals(bean.getName()))
                            updateOrgName(orgname);
                        else if (!AssertValue.isNotNull(bean))
                            addOrg(orgname);
                        OrgEditActivity.this.setEdit(false);
                    } else
                        Toast.makeText(OrgEditActivity.this, R.string.new_org_tip, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            neworg.setEnabled(false);
            if (orgUserRole != 2)
                sendMsgCardButton.setVisibility(View.VISIBLE);
            titleBarLayout.getTitleRightTv().setText(R.string.app_edit);
            if (AssertValue.isNotNull(bean) && orgUserRole != 2) {
                useritemList.clear();
                orgitemList.clear();
                builderUserOrg();
            }
            titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrgEditActivity.this.setEdit(true);
                }
            });
        }
        if (registerDialog != null)
            registerDialog.dismiss();
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
        useradapter.edit(true);
        useradapter.notifyDataSetChanged();
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
        orgadapter.edit(true);
        orgadapter.notifyDataSetChanged();
    }

    //把查询的组织信息，构建到用户和组织控件显示
    private void builderUserOrg() {
        if (AssertValue.isNotNullAndNotEmpty(bean.getUsers())) {
            JupiterTextIco useritem;
            for (User user : bean.getUsers()) {
                if (user.getRelationrole().equals(User.OWNER)) {
                    useritem = new JupiterTextIcoWithCorner(this);
                    useritem.setCornerImage(R.drawable.groupadmin);
                } else if (user.getRelationrole().equals(User.ASSISANT)) {
                    useritem = new JupiterTextIcoWithCorner(this);
                    useritem.setCornerImage(R.drawable.groupassisant);
                    useritem.setOnLongClickListener(onLongOrgUserClick);
                } else {
                    useritem = new JupiterTextIcoWithoutCorner(this);
                    useritem.setOnLongClickListener(onLongOrgUserClick);
                }
                useritem.setTag(user);
                useritem.setTitle(user.getName());
                useritem.setImage("drawable://" + R.drawable.user_head);
                CacheUser cacheUser = UserCache.getInstance().getUser(user.getId());
                if (AssertValue.isNotNull(cacheUser) && AssertValue.isNotNullAndNotEmpty(cacheUser.getUrl())) {
                    useritem.setImage(cacheUser.getUrl());
                }
                useritemList.add(useritem);
                useradapter.edit(true);
                useradapter.notifyDataSetChanged();
            }
        }
        if (AssertValue.isNotNullAndNotEmpty(bean.getChildren())) {
            for (Org org : bean.getChildren()) {
                JupiterTextIcoWithoutCorner orgitem = new JupiterTextIcoWithoutCorner(getApplicationContext());
                orgitem.setTitle(org.getName());
                orgitem.setImage("drawable://" + R.drawable.user_group);
                orgitemList.add(orgitem);
                orgadapter.edit(true);
                orgadapter.notifyDataSetChanged();
            }
        }
    }


    View.OnLongClickListener onLongOrgUserClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (edit) {
                JupiterTextIco item = (JupiterTextIco) v;
                User user = (User) item.getTag();
                if (orgUserRole == 0)
                    ininPupup(item);
                else if (orgUserRole == 1 && user.getRelationrole().equals(User.USER))
                    ininPupup(item);
            }
            return false;
        }
    };

    public void ininPupup(final JupiterTextIco item) {
        User user = (User) item.getTag();
        orgUserOperateLayout = new OrgUserOperateLayout(mContext);
        if (orgUserRole == 1)
            orgUserOperateLayout.getAdd_orghelper().setVisibility(View.GONE);
        if (user.getRelationrole().equals(User.USER))
            orgUserOperateLayout.getAdd_orghelper().setText("添加" + user.getName() + "为群助手");
        else if (user.getRelationrole().equals(User.ASSISANT))
            orgUserOperateLayout.getAdd_orghelper().setText("删除" + user.getName() + "为群助手");

        orgUserOperateLayout.getDelete_orguser().setText("删除成员" + user.getName());
        orgUserOperateLayout.getCancle().setOnClickListener(onOrgUserCancelClickListener);
        orgUserOperatePopupW = new PopupWindow(orgUserOperateLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        orgUserOperatePopupW.setOutsideTouchable(true);
        orgUserOperatePopupW.setFocusable(true);
        orgUserOperatePopupW.setTouchable(true);
        orgUserOperatePopupW.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#b0000000")));
        orgUserOperatePopupW.showAtLocation(sendMsgCardButton, Gravity.BOTTOM, 0,
                0);
        orgUserOperatePopupW.setAnimationStyle(R.style.bottom2top_top2bottom);
        orgUserOperateLayout.getDelete_orguser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserItem(item);
            }
        });
        orgUserOperateLayout.getAdd_orghelper().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrgUserRole(item);
            }
        });
        orgUserOperatePopupW.update();
    }

    //获取组织的详细信息

    private void getOrgDetails() {
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getOrgid())) {
            Resource resource = resourceFactory.create("QueryOrgDetailsByOrgid");
            resource.param("orgid", command.getOrgid());
            resource.param("dimid", command.getDimid());
            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    bean = (OrgDetailInfoBean) response.getPayload();
                    if (bean != null) {
                        buildInfo();
                    }
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
                command.setEdit(edit);
                initView();
                setResult(OrgEditCommand.RESULT_CODE_OK);
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


    //删除用户的一项的方法
    private void deleteUserItem(final JupiterTextIco item) {
        User user = (User) item.getTag();
        Resource resource = resourceFactory.create("RemoveOrgCardByUserId");
        resource.param("userid", userid);
        resource.param("resourceid", user.getId());
        resource.param("orgid", command.getOrgid());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(OrgEditActivity.this, R.string.delete_orguser_success_tip, Toast.LENGTH_SHORT).show();
                useritemList.remove(item);
                useradapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrgEditActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                orgUserOperatePopupW.dismiss();
            }
        });
    }

    //修改组织名称的方法
    private void updateOrgName(final String orgname) {
        Resource resource = resourceFactory.create("UpdateOrg");
        resource.param("orgid", command.getOrgid());
        resource.param("userid", userid);
        resource.param("name", orgname);
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                titleBarLayout.getTitleTv().setText(orgname);
                bean.setName(orgname);
                setResult(OrgEditCommand.RESULT_CODE_OK);
                Toast.makeText(OrgEditActivity.this, R.string.update_org_success_tip, Toast.LENGTH_SHORT).show();
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

    //修改成员在组织内的角色
    private void updateOrgUserRole(final JupiterTextIco item) {
        final User user = (User) item.getTag();
        Resource resource = resourceFactory.create("UpdateUserOrgRole");
        resource.param("orgid", command.getOrgid());
        resource.param("resourceid", user.getId());
        resource.param("userid", userid);
        if (user.getRelationrole().equals(User.USER))
            resource.param("relationrole", "assisant");
        else if (user.getRelationrole().equals(User.ASSISANT))
            resource.param("relationrole", "user");
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                String tip = user.getRelationrole().equals(User.USER) ? "添加" : "删除";
                Toast.makeText(OrgEditActivity.this, tip + user.getName() + "为群助手成功！", Toast.LENGTH_SHORT).show();
                command.setEdit(edit);
                initView();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrgEditActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {
                orgUserOperatePopupW.dismiss();
            }
        });
    }


    private void getCurrentUserOrgRole(List<User> users) {
        String userid = sessionManager.getUser().getId();
        for (User user : users) {
            if (userid.equals(user.getId())) {
                if (user.getRelationrole().equals(User.OWNER))
                    orgUserRole = 0;
                if (user.getRelationrole().equals(User.ASSISANT))
                    orgUserRole = 1;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == OrgChooseAddUserCommand.REQUEST_CODE && resultCode == OrgChooseAddUserCommand.RESULT_CODE_OK) || (requestCode == OrgEditCommand.REQUEST_CODE && resultCode == OrgEditCommand.RESULT_CODE_OK)) {
            requestcode = OrgEditCommand.RESULT_CODE_OK;
            command.setEdit(edit);
            initView();
        }
    }

    private View.OnClickListener onSendMsgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (AssertValue.isNotNull(bean))
                NewDynamicActivity.start(OrgEditActivity.this, new NewDynamicCommand().setSelectUsers(bean.getUsers()));
        }
    };


    private View.OnClickListener onOrgUserCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            orgUserOperatePopupW.dismiss();
        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (requestcode == OrgEditCommand.RESULT_CODE_OK)
                setResult(requestcode);
            finish();
        }
    };
}
