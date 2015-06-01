package com.yun9.wservice.view.org;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/5/29.
 */
public class OrgListActivity extends JupiterFragmentActivity {

    private OrgListCommand command;

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.orglist)
    private ListView orgListView;

    private List<OrgListBean> orgs;

    private OrgListAdapter orgListAdapter;

    private boolean edit = false;

    public static void startByHr(Activity activity, OrgListCommand command) {
        String title = activity.getResources().getString(R.string.org_list_title_hr);
        command.setDimType("hr");
        command.setTitle(title);
        start(activity, command);
    }

    public static void startByGroup(Activity activity, OrgListCommand command) {
        String title = activity.getResources().getString(R.string.org_list_title_group);
        command.setTitle(title);
        command.setDimType("group");
        start(activity, command);
    }

    public static void start(Activity activity, OrgListCommand command) {
        Intent intent = new Intent(activity, OrgListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_org_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        command = (OrgListCommand) this.getIntent().getSerializableExtra("command");

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getTitle())) {
            this.titleBarLayout.getTitleTv().setText(command.getTitle());
        }


        //初始化界面
        this.initViews();

        //刷新数据
        this.refresh();

        //设置是否编辑模式参数
        if (AssertValue.isNotNull(command)) {
            this.setEdit(command.isEdit());
        }

    }

    private void initViews(){
        titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgListActivity.this.setEdit(!edit);
            }
        });
    }

    private void refresh() {
        this.builderData();
        if (!AssertValue.isNotNull(this.orgListAdapter)) {
            this.orgListAdapter = new OrgListAdapter(this, this.orgs);
            this.orgListView.setAdapter(orgListAdapter);
        } else {
            this.orgListAdapter.notifyDataSetChanged();
        }
    }

    private void setEdit(boolean edit) {
        this.edit = edit;

        if (this.edit) {
            String cancel = this.getResources().getString(R.string.app_cancel);
            titleBarLayout.getTitleRightTv().setText(cancel);
        } else {
            String editStr = this.getResources().getString(R.string.app_select);
            titleBarLayout.getTitleRightTv().setText(editStr);
        }
        this.selectMode(edit);
    }

    private void selectMode(boolean mode) {
        this.orgListAdapter.setSelectMode(mode);
        this.orgListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        setResult(OrgListCommand.RESULT_CODE_CANCEL);
        super.onBackPressed();
    }

    private void builderData() {
        if (!AssertValue.isNotNull(orgs)) {
            orgs = new ArrayList<>();
        }

        for (int i = 0; i < 20; i++) {
            OrgListBean orgListBean = new OrgListBean();
            orgListBean.setSelected(false);
            Org org = new Org();
            org.setId(i + "");
            org.setName("测试组织" + i);
            org.setNo("00" + i);
            orgListBean.setOrg(org);

            orgs.add(orgListBean);
        }
    }
}
