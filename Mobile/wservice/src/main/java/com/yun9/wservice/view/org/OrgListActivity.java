package com.yun9.wservice.view.org;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
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

    @ViewInject(id = R.id.complete)
    private JupiterImageButtonLayout completeButton;

    @ViewInject(id = R.id.neworg)
    private JupiterRowStyleTitleLayout newOrg;

    @ViewInject(id = R.id.orglist)
    private ListView orgListView;

    @ViewInject(id = R.id.buttonbar)
    private LinearLayout buttonBar;

    @BeanInject
    private ResourceFactory resourceFactory;

    private List<OrgListBean> orgs;

    private OrgListAdapter orgListAdapter;

    private boolean edit = false;

    public static void start(Activity activity, OrgListCommand command) {
        Intent intent = new Intent(activity, OrgListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        intent.putExtras(bundle);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, OrgListCommand.REQUEST_CODE);
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


    }

    private void initViews() {
        titleBarLayout.getTitleRightTv().setVisibility(View.VISIBLE);
        titleBarLayout.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgListActivity.this.setEdit(!edit);
            }
        });

        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(OrgListCommand.RESULT_CODE_CANCEL);
                finish();
            }
        });

        //检查是否显示新增按钮
        if (AssertValue.isNotNull(command) && command.isNewAction()) {
            newOrg.setVisibility(View.VISIBLE);
            newOrg.setOnClickListener(onClickNewOrgListener);
        } else {
            newOrg.setVisibility(View.GONE);
        }

        this.completeButton.setOnClickListener(onClickCompletionListener);
    }

    private void refresh() {

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getDimid())) {

            final ProgressDialog registerDialog = ProgressDialog.show(OrgListActivity.this, null, getResources().getString(R.string.app_wating), true);

            Resource resource = resourceFactory.create("QueryOrgsByOrgList");
            resource.param("demid", command.getDimid());

            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    List<OrgListBean> tempOrgs = (List<OrgListBean>) response.getPayload();
                    builderData(tempOrgs);
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(OrgListActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    registerDialog.dismiss();
                }
            });
        }
    }

    private void setEdit(boolean edit) {
        this.edit = edit;

        if (this.edit) {
            String cancel = this.getResources().getString(R.string.app_cancel);
            titleBarLayout.getTitleRightTv().setText(cancel);
            this.buttonBar.setVisibility(View.VISIBLE);
        } else {
            String editStr = this.getResources().getString(R.string.app_select);
            this.buttonBar.setVisibility(View.GONE);
            titleBarLayout.getTitleRightTv().setText(editStr);
        }
        this.selectMode(edit);
    }

    private void selectMode(boolean mode) {

        if(AssertValue.isNotNull(this.orgListAdapter)) {

            this.orgListAdapter.setSelectMode(mode);
            this.orgListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed() {
        setResult(OrgListCommand.RESULT_CODE_CANCEL);
        super.onBackPressed();
    }

    private void builderData(List<OrgListBean> tempOrgs) {

        if (!AssertValue.isNotNull(orgs)) {
            orgs = new ArrayList<>();
        }

        if (AssertValue.isNotNullAndNotEmpty(tempOrgs)) {
            for (OrgListBean tempOrg : tempOrgs) {
                tempOrg.setSelected(false);

                //根据代入参数设置已经选中的项目
                if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectOrgs())) {
                    for (String orgid : command.getSelectOrgs()) {
                        if (AssertValue.isNotNull(orgid) && orgid.equals(tempOrg.getId())) {
                            tempOrg.setSelected(true);
                        }
                    }
                }
                orgs.add(tempOrg);
            }
        }

        if (!AssertValue.isNotNull(this.orgListAdapter)) {
            this.orgListAdapter = new OrgListAdapter(this, this.orgs,command.getDimType());
            this.orgListView.setAdapter(orgListAdapter);
        } else {
            this.orgListAdapter.notifyDataSetChanged();
        }

        //设置是否编辑模式参数
        if (AssertValue.isNotNull(command)) {
            this.setEdit(command.isEdit());
        }

    }

    //TODO 待服务完善参数后，完成缺少字段的补充处理
    private View.OnClickListener onClickCompletionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<Org> selectOrg = new ArrayList<>();

            if (AssertValue.isNotNullAndNotEmpty(orgs)) {
                for (OrgListBean orgListBean : orgs) {
                    if (orgListBean.isSelected()) {
                        Org org = new Org();
                        org.setId(orgListBean.getId());
                        org.setNo(orgListBean.getNo());
                        org.setName(orgListBean.getName());
                        //org.setDimType();
                        //org.setDimid(orgListBean.getd);
                        org.setCreateby(orgListBean.getCreateby());
                        org.setDesc(orgListBean.getDesc_());
                        //org.setDimno(orgListBean.getd);
                        org.setParentid(orgListBean.getParentid());
                        //org.setRemark(orgListBean.getR);
                        org.setSort(orgListBean.getSort());
                        selectOrg.add(org);
                    }
                }
            }
            Intent intent = new Intent();
            intent.putExtra(OrgListCommand.PARAM_ORG, selectOrg);
            if (AssertValue.isNotNull(command)) {
                intent.putExtra(OrgListCommand.PARAM_DIMTYPE, command.getDimType());
            }
            setResult(OrgListCommand.RESULT_CODE_OK, intent);
            finish();
        }
    };


    private View.OnClickListener onClickNewOrgListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgEditActivity.start(OrgListActivity.this, new OrgEditCommand().setEdit(edit));
        }
    };
}
