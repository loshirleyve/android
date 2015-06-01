package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.model.User;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/29.
 * 组织结构综合显示界面。
 */

public class OrgCompositeActivity extends JupiterFragmentActivity {


    private OrgCompositeCommand command;

    @ViewInject(id = R.id.userlist)
    private ListView userListView;

    @ViewInject(id = R.id.buttonbar)
    private LinearLayout buttonbarLL;

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;

    @ViewInject(id = R.id.sendmsgcard)
    private JupiterImageButtonLayout sendMsgCardButton;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    private List<OrgCompositeUserListBean> orgCompositeUserListBeans;

    private OrgCompositeListAdapter orgCompositeListAdapter;

    private boolean edit;

    public static void start(Activity activity, OrgCompositeCommand command) {
        Intent intent = new Intent(activity, OrgCompositeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_org_composite;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取参数
        command = (OrgCompositeCommand) this.getIntent().getSerializableExtra("command");

        //刷新界面数据
        this.refresh();

        //检查是否进入编辑模式
        if (AssertValue.isNotNull(command)) {
            this.edit(command.isEdit());
        }

        //处理完成模式
        if (OrgCompositeCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
            completeButton.setVisibility(View.GONE);
            sendMsgCardButton.setVisibility(View.VISIBLE);

        } else if (OrgCompositeCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
            completeButton.setVisibility(View.VISIBLE);
            sendMsgCardButton.setVisibility(View.GONE);
            completeButton.setOnClickListener(onClickCompletionListener);
        }
    }

    private void refresh() {
        this.builderUserInfo();
        if (!AssertValue.isNotNull(this.orgCompositeListAdapter)) {
            this.orgCompositeListAdapter = new OrgCompositeListAdapter(this, orgCompositeUserListBeans, false);
            orgCompositeListAdapter.setGroupOnClickListener(groupOnClickListener);
            orgCompositeListAdapter.setHrOnClickListener(hrOnClickListener);
            userListView.setAdapter(orgCompositeListAdapter);
        } else {
            this.orgCompositeListAdapter.notifyDataSetChanged();
        }
    }

    private List<OrgCompositeUserListBean> builderUserInfo() {
        orgCompositeUserListBeans = new ArrayList<>();
        OrgCompositeUserListBean topOrgCompositeUserListBean = new OrgCompositeUserListBean();
        topOrgCompositeUserListBean.setTop(true);
        orgCompositeUserListBeans.add(topOrgCompositeUserListBean);


        for (int i = 0; i < 20; i++) {
            OrgCompositeUserListBean orgCompositeUserListBean = new OrgCompositeUserListBean();
            User user = new User();
            user.setNo("00" + i);
            user.setName("测试用户" + i);
            user.setId(i + "");

            orgCompositeUserListBean.setUser(user);
            orgCompositeUserListBean.setSelected(false);

            //处理参数中已经选择的Userid
            if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectUsers())){
                for(String userid : command.getSelectUsers()){
                    if (AssertValue.isNotNullAndNotEmpty(userid) && userid.equals(orgCompositeUserListBean.getUser().getId())){
                        orgCompositeUserListBean.setSelected(true);
                    }
                }
            }
            orgCompositeUserListBeans.add(orgCompositeUserListBean);
        }


        return orgCompositeUserListBeans;
    }

    private View.OnClickListener hrOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgListActivity.startByHr(OrgCompositeActivity.this, new OrgListCommand());
        }
    };

    private View.OnClickListener groupOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgListActivity.startByGroup(OrgCompositeActivity.this, new OrgListCommand());
        }
    };

    private View.OnClickListener onClickCompletionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<User> selectUsers = new ArrayList<>();

            if (AssertValue.isNotNullAndNotEmpty(orgCompositeUserListBeans)) {
                for (OrgCompositeUserListBean orgCompositeUserListBean : orgCompositeUserListBeans) {
                    if (orgCompositeUserListBean.isSelected()) {
                        selectUsers.add(orgCompositeUserListBean.getUser());
                    }
                }
            }
            Intent intent = new Intent();
            intent.putExtra(OrgCompositeCommand.PARAM_ORG, selectUsers);
            setResult(OrgCompositeCommand.RESULT_CODE_OK, intent);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        setResult(OrgCompositeCommand.RESULT_CODE_CANCEL);
        super.onBackPressed();

    }

    public void edit(boolean edit) {
        this.edit = edit;

        if (this.edit) {
            buttonbarLL.setVisibility(View.VISIBLE);
            String cancel = OrgCompositeActivity.this.getResources().getString(R.string.app_cancel);
            titleBarLayout.getTitleRightTv().setText(cancel);
            titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
            this.selectMode(true);
            titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrgCompositeActivity.this.edit(false);
                }
            });
        } else {
            buttonbarLL.setVisibility(View.GONE);
            String editStr = OrgCompositeActivity.this.getResources().getString(R.string.app_select);
            titleBarLayout.getTitleRightTv().setText(editStr);
            titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
            this.selectMode(false);
            titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrgCompositeActivity.this.edit(true);
                }
            });
        }
    }

    private void selectMode(boolean mode) {
        this.orgCompositeListAdapter.setSelectMode(mode);
        this.orgCompositeListAdapter.notifyDataSetChanged();

    }
}
