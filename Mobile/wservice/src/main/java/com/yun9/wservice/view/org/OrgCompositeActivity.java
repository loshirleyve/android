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
import com.yun9.jupiter.listener.OnSelectListener;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Dim;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.widget.JupiterImageButtonLayout;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.OrgCompositeInfoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 15/5/29.
 * 组织结构综合显示界面。
 */

public class OrgCompositeActivity extends JupiterFragmentActivity {

    private static final Logger logger = Logger.getLogger(OrgCompositeActivity.class);

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

    @BeanInject
    private ResourceFactory resourceFactory;

    @BeanInject
    private SessionManager sessionManager;

    private List<OrgCompositeUserListBean> orgCompositeUserListBeans;

    private Map<String, ArrayList<Org>> onSelectOrgMaps = new HashMap<>();

    private OrgCompositeListAdapter orgCompositeListAdapter;

    private OrgCompositeTopWidget orgCompositeTopWidget;

    private boolean edit;

    private boolean onlyUsers;

    public static void start(Activity activity, OrgCompositeCommand command) {
        Intent intent = new Intent(activity, OrgCompositeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, OrgCompositeCommand.REQUEST_CODE);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_org_composite;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builderTopView();

        //获取参数
        command = (OrgCompositeCommand) this.getIntent().getSerializableExtra("command");

        //检查参数，如果command没有设置用户或者机构则用当前机构补充上去
        if (AssertValue.isNotNull(command) && !AssertValue.isNotNullAndNotEmpty(command.getUserid())) {
            command.setUserid(sessionManager.getUser().getId());
        }

        if (AssertValue.isNotNull(command) && !AssertValue.isNotNullAndNotEmpty(command.getInstid())) {
            command.setInstid(sessionManager.getInst().getId());
        }

        //刷新界面数据
        this.refresh();

        //处理完成模式
        if (OrgCompositeCommand.COMPLETE_TYPE_SENDMSGCARD.equals(command.getCompleteType())) {
            completeButton.setVisibility(View.GONE);
            sendMsgCardButton.setVisibility(View.VISIBLE);

        } else if (OrgCompositeCommand.COMPLETE_TYPE_CALLBACK.equals(command.getCompleteType())) {
            completeButton.setVisibility(View.VISIBLE);
            sendMsgCardButton.setVisibility(View.GONE);
            completeButton.setOnClickListener(onClickCompletionListener);
        }

        //处理返回事件
        titleBarLayout.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(OrgCompositeCommand.RESULT_CODE_CANCEL);
                finish();
            }
        });
    }

    private void builderTopView() {
        orgCompositeTopWidget = new OrgCompositeTopWidget(OrgCompositeActivity.this);
        orgCompositeTopWidget.getOrgGroupLL().setOnClickListener(groupOnClickListener);
        orgCompositeTopWidget.getOrgHrLL().setOnClickListener(hrOnClickListener);
        //orgCompositeTopWidget.getMyselfLL().setOnClickListener(myselfOnClickListener);
        userListView.addHeaderView(orgCompositeTopWidget);
    }

    private void refresh() {
        userListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                builderUserInfo();
                builderOrgs();
            }
        }, 100);

    }

    private void builderUserInfo() {

        if (!AssertValue.isNotNull(orgCompositeUserListBeans)) {
            orgCompositeUserListBeans = new ArrayList<>();
        }

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getInstid()) && AssertValue.isNotNullAndNotEmpty(command.getUserid())) {
            Resource resource = resourceFactory.create("QueryOrgCompositeInfo");

            resource.param("userid", command.getUserid());
            resource.param("instid", command.getInstid());

            final ProgressDialog registerDialog = ProgressDialog.show(OrgCompositeActivity.this, null, getResources().getString(R.string.app_wating), true);

            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    OrgCompositeInfoBean orgCompositeInfoBean = (OrgCompositeInfoBean) response.getPayload();
                    onRefreshComplete(orgCompositeInfoBean);
                }

                @Override
                public void onFailure(Response response) {
                    Toast.makeText(OrgCompositeActivity.this, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    registerDialog.dismiss();
                }
            });
        }
    }

    private void onRefreshComplete(OrgCompositeInfoBean orgCompositeInfoBean) {
        if (AssertValue.isNotNull(orgCompositeInfoBean) && AssertValue.isNotNull(orgCompositeInfoBean.getMyself())) {
            orgCompositeTopWidget.getMyselfLL().getSutitleTv().setText(orgCompositeInfoBean.getMyself().getSignature());
        }

        if (AssertValue.isNotNull(orgCompositeInfoBean) && AssertValue.isNotNullAndNotEmpty(orgCompositeInfoBean.getDim())) {
            for (Dim dim : orgCompositeInfoBean.getDim()) {
                if (OrgCompositeCommand.DIM_TYPE_GROUP.equals(dim.getType())) {
                    orgCompositeTopWidget.getOrgGroupLL().setTag(dim.getId());
                }
                if (OrgCompositeCommand.DIM_TYPE_HR.equals(dim.getType())) {
                    orgCompositeTopWidget.getOrgHrLL().setTag(dim.getId());
                }
            }
        }


        if (AssertValue.isNotNull(orgCompositeInfoBean) && AssertValue.isNotNullAndNotEmpty(orgCompositeInfoBean.getUserMaps())) {
            for (User user : orgCompositeInfoBean.getUserMaps()) {
                OrgCompositeUserListBean orgCompositeUserListBean = new OrgCompositeUserListBean();
                orgCompositeUserListBean.setUser(user);
                orgCompositeUserListBean.setSelected(false);

                //处理参数中已经选择的Userid
                if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectUsers())) {
                    for (String userid : command.getSelectUsers()) {
                        if (AssertValue.isNotNullAndNotEmpty(userid) && userid.equals(orgCompositeUserListBean.getUser().getId())) {
                            orgCompositeUserListBean.setSelected(true);
                        }
                    }
                }
                orgCompositeUserListBeans.add(orgCompositeUserListBean);
            }
        }

        if (!AssertValue.isNotNull(orgCompositeListAdapter)) {
            orgCompositeListAdapter = new OrgCompositeListAdapter(OrgCompositeActivity.this, orgCompositeUserListBeans, false);
            orgCompositeListAdapter.setOnSelectListener(onSelectListener);
            userListView.setAdapter(orgCompositeListAdapter);
        } else {
            orgCompositeListAdapter.notifyDataSetChanged();
        }

        //设置子标题
        this.setSutitle();

        //检查是否进入编辑模式
        if (AssertValue.isNotNull(command)) {
            this.edit(command.isEdit());
            this.onlyUsers(command.isOnlyUsers());
        }
    }

    private void builderOrgs() {
        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectOrgs())) {
            final Resource resource = resourceFactory.create("QueryOrgsByOrgids");
            resource.param("orgids", command.getSelectOrgs());
            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {

                    ArrayList<Org> selectOrgs = (ArrayList<Org>) response.getPayload();

                    if (AssertValue.isNotNullAndNotEmpty(selectOrgs)) {
                        ArrayList<Org> hrOrgs = new ArrayList<Org>();
                        ArrayList<Org> groupOrgs = new ArrayList<Org>();

                        for (Org org : selectOrgs) {
                            if (OrgCompositeCommand.DIM_TYPE_HR.equals(org.getType())) {
                                hrOrgs.add(org);
                            }

                            if (OrgCompositeCommand.DIM_TYPE_GROUP.equals(org.getType())) {
                                groupOrgs.add(org);
                            }
                        }

                        onSelectOrgMaps.put(OrgCompositeCommand.DIM_TYPE_HR, hrOrgs);
                        onSelectOrgMaps.put(OrgCompositeCommand.DIM_TYPE_GROUP, groupOrgs);

                    }
                    //设置子标题
                    setSutitle();
                    //设置item子标题
                    setItemSutitle();
                }

                @Override
                public void onFailure(Response response) {

                }

                @Override
                public void onFinally(Response response) {

                }
            });
        }
    }

    private View.OnClickListener hrOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String dimid = (String) v.getTag();
            String title = OrgCompositeActivity.this.getResources().getString(R.string.org_list_title_hr);
            OrgListActivity.start(OrgCompositeActivity.this, builderListCommand(OrgCompositeCommand.DIM_TYPE_HR).setDimid(dimid).setTitle(title).setNewAction(!edit));
        }
    };

    private View.OnClickListener groupOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String dimid = (String) v.getTag();
            String title = OrgCompositeActivity.this.getResources().getString(R.string.org_list_title_group);
            OrgListActivity.start(OrgCompositeActivity.this, builderListCommand(OrgCompositeCommand.DIM_TYPE_GROUP).setDimid(dimid).setTitle(title).setNewAction(!edit));
        }
    };

    private OnSelectListener onSelectListener = new OnSelectListener() {
        @Override
        public void onSelect(View view, boolean mode) {
            OrgCompositeUserListBean tempOrgCompositeUserListBean = (OrgCompositeUserListBean) view.getTag();
            tempOrgCompositeUserListBean.setSelected(mode);
            setSutitle();
        }
    };

    private OrgListCommand builderListCommand(String dimType) {
        OrgListCommand orgListCommand = new OrgListCommand();
        orgListCommand.setEdit(edit);
        orgListCommand.setDimType(dimType);
        if (AssertValue.isNotNull(command)) {
            orgListCommand.setInstid(command.getInstid());
        }

        //找到需要打开类型的已经选择组织放入传递对象
        if (AssertValue.isNotNullAndNotEmpty(onSelectOrgMaps.get(dimType))) {
            for (Org org : onSelectOrgMaps.get(dimType)) {
                orgListCommand.putSelectOrgs(org.getId());
            }
        }
        return orgListCommand;
    }

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
            intent.putExtra(OrgCompositeCommand.PARAM_USER, selectUsers);

            ArrayList<Org> onSelectOrgs = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Org>> entity : onSelectOrgMaps.entrySet()) {
                if (AssertValue.isNotNullAndNotEmpty(entity.getValue())) {
                    onSelectOrgs.addAll(entity.getValue());
                }
            }

            intent.putExtra(OrgCompositeCommand.PARAM_ORG, onSelectOrgs);
            setResult(OrgCompositeCommand.RESULT_CODE_OK, intent);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        setResult(OrgCompositeCommand.RESULT_CODE_CANCEL);
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OrgListCommand.REQUEST_CODE && resultCode == OrgListCommand.RESULT_CODE_OK) {

            ArrayList<Org> orgs = (ArrayList<Org>) data.getSerializableExtra(OrgListCommand.PARAM_ORG);
            String dimType = data.getStringExtra(OrgListCommand.PARAM_DIMTYPE);

            if (AssertValue.isNotNullAndNotEmpty(orgs) && AssertValue.isNotNullAndNotEmpty(dimType)) {
                onSelectOrgMaps.put(dimType, orgs);
            } else {
                //如果没有选择则设置此处选择为空
                onSelectOrgMaps.put(dimType, new ArrayList<Org>());
            }

            //设置Item sutitle
            this.setItemSutitle();
            //设置sutitle
            this.setSutitle();

            logger.d("选择组织数量：" + orgs.size() + ",类型：" + dimType);
        }
    }

    private void setItemSutitle() {

        Map<String, String> sutitles = new HashMap<>();

        for (Map.Entry<String, ArrayList<Org>> entity : onSelectOrgMaps.entrySet()) {
            if (AssertValue.isNotNullAndNotEmpty(entity.getValue())) {

                if (edit) {
                    if (!AssertValue.isNotNullAndNotEmpty(sutitles.get(entity.getKey()))) {
                        sutitles.put(entity.getKey(), "已选择");
                    } else {
                        sutitles.put(entity.getKey(), "");
                    }

                    for (Org org : entity.getValue()) {
                        String temptitle = sutitles.get(entity.getKey());
                        temptitle = temptitle + " " + org.getName();
                        sutitles.put(entity.getKey(), temptitle);
                    }
                } else {
                    sutitles.put(entity.getKey(), "");
                }

            }

        }

        if (AssertValue.isNotNull(orgCompositeTopWidget)) {
            orgCompositeTopWidget.getOrgGroupLL().getSutitleTv().setText(sutitles.get(OrgCompositeCommand.DIM_TYPE_GROUP));
            orgCompositeTopWidget.getOrgHrLL().getSutitleTv().setText(sutitles.get(OrgCompositeCommand.DIM_TYPE_HR));
        }
    }

    private void setSutitle() {
        int selectUserNum = 0;
        int selectOrgNum = 0;

        for (Map.Entry<String, ArrayList<Org>> entity : onSelectOrgMaps.entrySet()) {
            if (AssertValue.isNotNullAndNotEmpty(entity.getValue())) {
                selectOrgNum = selectOrgNum + entity.getValue().size();
            }
        }

        if (AssertValue.isNotNullAndNotEmpty(orgCompositeUserListBeans)) {
            for (OrgCompositeUserListBean orgCompositeUserListBean : orgCompositeUserListBeans) {
                if (orgCompositeUserListBean.isSelected()) {
                    selectUserNum++;
                }
            }
        }

        String sutitle = "已经选择" + selectUserNum + "个用户" + selectOrgNum + "个部门";

        this.titleBarLayout.getTitleSutitleTv().setText(sutitle);
    }

    private void edit(boolean edit) {
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
            titleBarLayout.getTitleSutitleTv().setVisibility(View.VISIBLE);
            this.setSutitle();
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
            titleBarLayout.getTitleSutitleTv().setVisibility(View.GONE);
            this.setSutitle();
        }

        //设置Item sutitle
        this.setItemSutitle();
        //设置sutitle
        this.setSutitle();
    }


    private void onlyUsers(boolean onlyUsers) {
        this.onlyUsers = onlyUsers;
        if (this.onlyUsers) {
            orgCompositeTopWidget.getOrgGroupLL().setVisibility(View.GONE);
            orgCompositeTopWidget.getOrgHrLL().setVisibility(View.GONE);
            orgCompositeTopWidget.getMyselfLL().setVisibility(View.GONE);
        } else {
            orgCompositeTopWidget.getOrgGroupLL().setVisibility(View.VISIBLE);
            orgCompositeTopWidget.getOrgHrLL().setVisibility(View.VISIBLE);
            orgCompositeTopWidget.getMyselfLL().setVisibility(View.VISIBLE);
        }

    }
    private void selectMode(boolean mode) {
        if (AssertValue.isNotNull(this.orgCompositeListAdapter)) {
            this.orgCompositeListAdapter.setSelectMode(mode);
            this.orgCompositeListAdapter.notifyDataSetChanged();
        }

    }
}
