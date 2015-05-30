package com.yun9.wservice.view.org;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterRowStyleSutitleLayout;
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

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    private List<OrgUserBean> orgUserBeans;

    private OrgUserListAdapter orgUserListAdapter;

    private boolean edit;

    public static void start(Context context, OrgCompositeCommand command) {
        Intent intent = new Intent(context, OrgCompositeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        context.startActivity(intent);
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
    }

    private void refresh() {
        this.builderUserInfo();
        if (!AssertValue.isNotNull(this.orgUserListAdapter)){
            this.orgUserListAdapter = new OrgUserListAdapter(this, orgUserBeans,false);
            orgUserListAdapter.setGroupOnClickListener(groupOnClickListener);
            orgUserListAdapter.setHrOnClickListener(hrOnClickListener);
            userListView.setAdapter(orgUserListAdapter);
        }else{
            this.orgUserListAdapter.notifyDataSetChanged();
        }
    }

    private List<OrgUserBean> builderUserInfo() {
        orgUserBeans = new ArrayList<OrgUserBean>();
        OrgUserBean orgUserBeanTop = new OrgUserBean();
        orgUserBeanTop.setTop(true);
        orgUserBeans.add(orgUserBeanTop);

        for (int i = 0; i < 20; i++) {
            OrgUserBean orgUserBean = new OrgUserBean();
            orgUserBean.setId(i + "");
            orgUserBean.setName("测试用户" + i);
            orgUserBean.setNo("00" + i);
            orgUserBean.setSelected(false);
            orgUserBean.setTop(false);
            orgUserBeans.add(orgUserBean);
        }

        return orgUserBeans;
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

    private void selectMode(boolean mode){
        this.orgUserListAdapter.setSelectMode(mode);
        this.orgUserListAdapter.notifyDataSetChanged();

    }
}
