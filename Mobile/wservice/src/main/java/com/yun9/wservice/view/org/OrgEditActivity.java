package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.yun9.jupiter.widget.JupiterTextIco;
import com.yun9.jupiter.widget.JupiterTextIcoWithoutCorner;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrgDetailInfoBean;

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

    @ViewInject(id=R.id.parentorgname)
    private EditText parentorgname;

    @ViewInject(id=R.id.neworg)
    private EditText neworg;

    @ViewInject(id=R.id.orgtips)
    private TextView orgtips;


    @ViewInject(id=R.id.edit_ico_users)
    private JupiterEditIco jupiterEdituserIco;

    @ViewInject(id=R.id.edit_ico_orgs)
    private JupiterEditIco jupiterEditorgIco;

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private OrgDetailInfoBean bean;

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

        jupiterEdituserIco.getRowStyleSutitleLayout().getTitleTV().setText("成员列表");
        jupiterEditorgIco.getRowStyleSutitleLayout().getTitleTV().setText("下级部门");
        titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgEditActivity.this.setEdit(!edit);
            }
        });

        titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

        initWeight();

        parentorgname.setText(command.getParentorgname() == null ? sessionManager.getInst().getName() : command.getParentorgname());
        //检查是否进入编辑状态
        if (AssertValue.isNotNull(command) && command.isEdit()){

        }
        //如果没有orgid则进入新增状态
        if(!AssertValue.isNotNull(command) ||  !AssertValue.isNotNullAndNotEmpty(command.getOrgid()))
        {
            neworg.setEnabled(true);
            titleBarLayout.getTitleRightTv().setText("保存");
            titleBarLayout.getTitleTv().setText("自定义部门");
            titleBarLayout.getTitleRight().setOnClickListener(addOrgClickListener);
            jupiterEdituserIco.setVisibility(View.GONE);
            jupiterEditorgIco.setVisibility(View.GONE);
        }
        else {
            bulieInfo();
            neworg.setEnabled(false);
            titleBarLayout.getTitleRightTv().setVisibility(View.GONE);
            orgtips.setText("[向左滑动可编辑]");
        }
    }

    public void initWeight()
    {
        useritemList = new ArrayList<>();
        orgitemList = new ArrayList<>();
        setupEditIco();
    }



    private void setupEditIco() {
        JupiterTextIco useritem = new JupiterTextIcoWithoutCorner(getApplicationContext());
        useritem.setTitle(" ");
        useritem.setImage("drawable://" + com.yun9.jupiter.R.drawable.add_user);
        useritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewUserOne("drawable://" + com.yun9.jupiter.R.drawable.upload_icon);
            }
        });
        useritemList.add(useritem);
        useradapter = new BasicJupiterEditAdapter(useritemList);
        useradapter.edit(true);
        jupiterEdituserIco.setAdapter(useradapter);


        JupiterTextIco orgitem=new JupiterTextIcoWithoutCorner(getApplicationContext());
        orgitem.setTitle(" ");
        orgitem.setImage("drawable://" + com.yun9.jupiter.R.drawable.add_user);
        orgitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOrgOne("drawable://" + com.yun9.jupiter.R.drawable.upload_icon);
            }
        });
        orgitemList.add(orgitem);
        orgadapter = new BasicJupiterEditAdapter(orgitemList);
        orgadapter.edit(true);
        jupiterEditorgIco.setAdapter(orgadapter);
    }


    private void addNewUserOne(String id) {
        final JupiterTextIco useritem = new JupiterTextIco(getApplicationContext());
        useritem.setTitle(null);
        useritem.setImage(id);
        useritem.hideCorner();
        useritem.setTag(id);
        useritem.setCornerImage(com.yun9.jupiter.R.drawable.icn_delete);

        useritem.getBadgeView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserItem(useritem);
            }
        });
        useritem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        useritemList.add(useritem);
        useradapter.notifyDataSetChanged();
    }


    private void addNewOrgOne(String id) {

        final JupiterTextIcoWithoutCorner orgitem = new JupiterTextIcoWithoutCorner(getApplicationContext());
        orgitem.setTitle(null);
        orgitem.setImage(id);
        orgitem.hideCorner();
        orgitem.setTag(id);

        orgitem.getBadgeView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        orgitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        orgitemList.add(orgitem);
        orgadapter.notifyDataSetChanged();
    }

    private void deleteUserItem(JupiterEditableView item) {
        String userid=(String)item.getTag();
        RemoveOrgCardByUserId(userid,command.getOrgid());
        useritemList.remove(item);
        useradapter.notifyDataSetChanged();

    }



    private void setEdit(boolean edit){
        this.edit = edit;
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

    private View.OnClickListener addOrgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String orgname=neworg.getText().toString();
            if(AssertValue.isNotNullAndNotEmpty(orgname))
               addOrg(orgname);
               // Toast.makeText(OrgEditActivity.this, "添加组织", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(OrgEditActivity.this, "请输入组织名称", Toast.LENGTH_SHORT).show();
        }
    };

    private void bulieInfo()
    {
        Resource resource = resourceFactory.create("QueryOrgDetailsByOrgid");
        resource.param("orgid", command.getOrgid());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                bean = (OrgDetailInfoBean) response.getPayload();
                if(bean!=null)
                    builder(bean);
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


    private void builder(OrgDetailInfoBean bean)
    {
        neworg.setText(bean.getName());
        titleBarLayout.getTitleTv().setText(bean.getName());
        if(AssertValue.isNotNullAndNotEmpty(bean.getUsers()))
        {
            for (User user:bean.getUsers())
            {
                final JupiterTextIco useritem = new JupiterTextIco(getApplicationContext());
                useritem.setTitle(user.getName());
                useritem.hideCorner();
                useritem.setImage(user.getHeaderfileid());
                useritem.setTag(user.getId());
                useritem.setCornerImage(com.yun9.jupiter.R.drawable.icn_delete);
                useritem.getBadgeView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteUserItem(useritem);
                    }
                });
                useritemList.add(useritem);
                useradapter.notifyDataSetChanged();
            }
        }
        if(AssertValue.isNotNullAndNotEmpty(bean.getChildren()))
        {
            for (Org org:bean.getChildren())
            {
                final JupiterTextIcoWithoutCorner orgitem = new JupiterTextIcoWithoutCorner(getApplicationContext());
                orgitem.setTitle(org.getName());
                orgitem.hideCorner();
                orgitem.setImage("24130000000032768");
                orgitem.setTag(org.getId());
                orgitemList.add(orgitem);
                orgadapter.notifyDataSetChanged();
            }
        }
    }

    private void addOrg(String orgname)
    {
        Resource resource = resourceFactory.create("AddOrg");
        resource.param("instid", sessionManager.getInst().getId());
        resource.param("userid", sessionManager.getUser().getId());
        resource.param("name", orgname);
        resource.param("type", command.getParentorgtype());
        resource.param("parentid", command.getParentorgid() == null ? "0" : command.getParentorgid());
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(OrgEditActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
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


    private void RemoveOrgCardByUserId(String userid,String orgid)
    {
        Resource resource = resourceFactory.create("RemoveOrgCardByUserId");
        resource.param("userid", sessionManager.getUser().getId());
        resource.param("resourceid", userid);
        resource.param("orgid", orgid);
        resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(OrgEditActivity.this, "删除用户成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Response response) {
                Toast.makeText(OrgEditActivity.this, "删除用户失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinally(Response response) {

            }
        });
    }


}
