package com.yun9.wservice.view.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.location.LocationBean;
import com.yun9.jupiter.location.LocationFactory;
import com.yun9.jupiter.location.OnGetPoiInfoListener;
import com.yun9.jupiter.location.OnLocationListener;
import com.yun9.jupiter.location.PoiInfoBean;
import com.yun9.jupiter.model.FileBean;
import com.yun9.jupiter.model.Org;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.DateUtil;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.jupiter.view.JupiterGridView;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.location.LocationSelectActivity;
import com.yun9.wservice.view.location.LocationSelectCommand;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;
import com.yun9.wservice.view.topic.TopicActivity;
import com.yun9.wservice.view.topic.TopicCommand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Leon on 15/6/11.
 */
public class NewDynamicActivity extends JupiterFragmentActivity {

    @ViewInject(id = R.id.titlebar)
    private JupiterTitleBarLayout titleBarLayout;

    @ViewInject(id = R.id.dynamic_content_et)
    private EditText dynamicContentET;

    @ViewInject(id = R.id.location_tv)
    private TextView locationTV;

    @ViewInject(id = R.id.time_tv)
    private TextView timeTV;

    @ViewInject(id = R.id.images_gv)
    private JupiterGridView imageGV;

    @ViewInject(id = R.id.share_to_gv)
    private JupiterGridView shareToGV;

    @ViewInject(id = R.id.select_image_rl)
    private RelativeLayout selectImageRL;

    @ViewInject(id = R.id.select_user_rl)
    private RelativeLayout selectUserRL;

//    @ViewInject(id = R.id.select_org_rl)
//    private RelativeLayout selectOrgRL;

    @ViewInject(id = R.id.select_topoc_rl)
    private RelativeLayout selectTopocRL;

    @ViewInject(id = R.id.keyborad_rl)
    private RelativeLayout keyboradRL;

    @ViewInject(id = R.id.location_ll)
    private LinearLayout locationLL;

    private NewDynamicCommand command;

    private List<OrgAndUserBean> selectOrgAndUsers = new ArrayList<>();

    private List<FileBean> selectFiles;

    private NewDynamicShareInfoAdapter newDynamicShareInfoAdapter;

    private LocationBean lastLocationBean;

    private PoiInfoBean lastPoiInfoBean;

    private Date sendDate;

    private boolean selectLocation = false;

    private LocationSelectCommand locationSelectCommand = new LocationSelectCommand();

    private TopicCommand topicCommand = new TopicCommand();

    @Override
    protected int getContentView() {
        return R.layout.activity_new_dynamic;
    }

    public static void start(Activity activity, NewDynamicCommand command) {
        Intent intent = new Intent(activity, NewDynamicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("command", command);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, command.getRequestCode());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.command = (NewDynamicCommand) this.getIntent().getSerializableExtra("command");

        this.selectImageRL.setOnClickListener(onSelectImageClickListener);
        this.selectUserRL.setOnClickListener(onSelectUserClickListener);
//        this.selectOrgRL.setOnClickListener(onSelectOrgClickListener);
        this.selectTopocRL.setOnClickListener(onSelectTopocClickListener);
        this.keyboradRL.setOnClickListener(onKeyboradClickListener);

        this.locationLL.setOnClickListener(onLocationClickListener);

        this.titleBarLayout.getTitleRight().setOnClickListener(onSendClickListener);
        this.titleBarLayout.getTitleLeft().setOnClickListener(onCancelClickListener);

        sendDate = DateUtil.getNowDate();
        this.timeTV.setText(DateUtil.getStringDate());

        //载入参数代入的发送对象
        this.loadCommandSelectInfo();

        //初始化分享范围
        this.builderShareInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
        locationFactory.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.shareToGV.postDelayed(new Runnable() {
            @Override
            public void run() {
                LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
                locationFactory.setOnLocationListener(onLocationListener);
                locationFactory.setOnGetPoiInfoListener(onGetPoiInfoListener);
                locationFactory.start();
                locationFactory.requestLocation();
            }
        }, 100);

    }

    private void loadCommandSelectInfo() {

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectUsers())) {
            for (User user : command.getSelectUsers()) {
                if (AssertValue.isNotNull(user)) {
                    this.selectOrgAndUsers.add(new OrgAndUserBean(user));
                }
            }
        }

        if (AssertValue.isNotNull(command) && AssertValue.isNotNullAndNotEmpty(command.getSelectOrgs())) {
            for (Org org : command.getSelectOrgs()) {
                if (AssertValue.isNotNull(org)) {
                    this.selectOrgAndUsers.add(new OrgAndUserBean(org));
                }
            }
        }
    }

    private void builderShareInfo() {
        if (!AssertValue.isNotNull(newDynamicShareInfoAdapter)) {
            newDynamicShareInfoAdapter = new NewDynamicShareInfoAdapter(this, this.selectOrgAndUsers);
            this.shareToGV.setAdapter(newDynamicShareInfoAdapter);
            this.newDynamicShareInfoAdapter.notifyDataSetChanged();
        } else {
            this.newDynamicShareInfoAdapter.notifyDataSetChanged();
        }
    }

    private void putOrg(Org org) {
        if (!AssertValue.isNotNull(this.selectOrgAndUsers)) {
            this.selectOrgAndUsers = new ArrayList<>();
        }
        this.selectOrgAndUsers.add(new OrgAndUserBean(org));
    }

    private void putUser(User user) {
        if (!AssertValue.isNotNull(this.selectOrgAndUsers)) {
            this.selectOrgAndUsers = new ArrayList<>();
        }
        this.selectOrgAndUsers.add(new OrgAndUserBean(user));
    }

    private void cleanOrgAndUser(String type) {
        if (AssertValue.isNotNull(this.selectOrgAndUsers)) {
            int size = this.selectOrgAndUsers.size();

            for (int i = size; i > 0; i--) {
                OrgAndUserBean orgAndUserBean = this.selectOrgAndUsers.get(i - 1);
                if (orgAndUserBean.getType().equals(type)) {
                    this.selectOrgAndUsers.remove(orgAndUserBean);
                }
            }
        }
    }

    private void putFile(FileBean fileBean) {
        if (!AssertValue.isNotNull(this.selectFiles)) {
            this.selectFiles = new ArrayList<>();
        }
        this.selectFiles.add(fileBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OrgCompositeCommand.REQUEST_CODE && resultCode == OrgCompositeCommand.RESULT_CODE_OK) {
            List<User> users = (List<User>) data.getSerializableExtra(OrgCompositeCommand.PARAM_USER);
            List<Org> orgs = (List<Org>) data.getSerializableExtra(OrgCompositeCommand.PARAM_ORG);
            cleanOrgAndUser(OrgAndUserBean.TYPE_USER);
            cleanOrgAndUser(OrgAndUserBean.TYPE_ORG);

            if (AssertValue.isNotNullAndNotEmpty(users)) {
                for (User user : users) {
                    putUser(user);
                }
            }

            if (AssertValue.isNotNullAndNotEmpty(orgs)) {
                for (Org org : orgs) {
                    putOrg(org);
                }
            }

            builderShareInfo();
        }

        if (requestCode == locationSelectCommand.getRequestCode() && resultCode == LocationSelectCommand.RESULT_CODE_OK) {
            lastPoiInfoBean = (PoiInfoBean) data.getSerializableExtra(LocationSelectCommand.PARAM_POIINFO);
            if (AssertValue.isNotNull(lastPoiInfoBean) && AssertValue.isNotNullAndNotEmpty(lastPoiInfoBean.getName())) {
                locationTV.setText(lastPoiInfoBean.getName());
                selectLocation = true;
            }
        }

        if (requestCode == topicCommand.getRequestCode() && resultCode == TopicCommand.RESULT_CODE_OK) {
            String topic = data.getStringExtra(TopicCommand.PARAM_TOPIC);

            if (AssertValue.isNotNullAndNotEmpty(topic)) {
                dynamicContentET.setText(dynamicContentET.getText() + topic);
                dynamicContentET.setSelection(dynamicContentET.getText().length());
            }
        }
    }

    private View.OnClickListener onSelectImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };

    private View.OnClickListener onSelectUserClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OrgCompositeCommand orgCompositeCommand = new OrgCompositeCommand().setEdit(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK);

            if (AssertValue.isNotNullAndNotEmpty(selectOrgAndUsers)) {
                for (OrgAndUserBean orgAndUserBean : selectOrgAndUsers) {
                    if (OrgAndUserBean.TYPE_USER.equals(orgAndUserBean.getType())) {
                        orgCompositeCommand.putSelectUser(orgAndUserBean.getUser().getId());
                    }
                    if (OrgAndUserBean.TYPE_ORG.equals(orgAndUserBean.getType())) {
                        orgCompositeCommand.putSelectOrgs(orgAndUserBean.getOrg().getId());
                    }
                }
            }
            OrgCompositeActivity.start(NewDynamicActivity.this, orgCompositeCommand);
        }
    };

//    private View.OnClickListener onSelectOrgClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            OrgListCommand orgListCommand = new OrgListCommand().setEdit(true);
//
//            OrgListActivity.start(NewDynamicActivity.this, orgListCommand);
//        }
//    };

    private View.OnClickListener onSelectTopocClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TopicActivity.start(NewDynamicActivity.this, topicCommand);
        }
    };

    private View.OnClickListener onKeyboradClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    private View.OnClickListener onSendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LocationSelectActivity.start(NewDynamicActivity.this, locationSelectCommand);
        }
    };

    private OnLocationListener onLocationListener = new OnLocationListener() {
        @Override
        public void onReceiveLocation(LocationBean locationBean) {
            lastLocationBean = locationBean;
            LocationFactory locationFactory = JupiterApplication.getBeanManager().get(LocationFactory.class);
            locationFactory.poiSearch(lastLocationBean, "写字楼", 0, 500);
        }
    };


    private OnGetPoiInfoListener onGetPoiInfoListener = new OnGetPoiInfoListener() {
        @Override
        public void onGetPoiInfo(List<PoiInfoBean> poiInfoBeans) {
            if (AssertValue.isNotNullAndNotEmpty(poiInfoBeans) && !selectLocation) {
                lastPoiInfoBean = poiInfoBeans.get(0);
                locationTV.setText(lastPoiInfoBean.getName());
            } else {
                //没有结果
            }
        }
    };
}
