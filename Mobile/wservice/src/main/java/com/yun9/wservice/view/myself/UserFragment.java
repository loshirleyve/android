package com.yun9.wservice.view.myself;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yun9.jupiter.http.AsyncHttpResponseCallback;
import com.yun9.jupiter.http.Response;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.repository.Resource;
import com.yun9.jupiter.repository.ResourceFactory;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.client.ClientActivity;
import com.yun9.wservice.view.order.OrderManagerActivity;
import com.yun9.wservice.view.other.SettingActivity;
import com.yun9.wservice.view.doc.DocCompositeActivity;
import com.yun9.wservice.view.doc.DocCompositeCommand;
import com.yun9.wservice.view.inst.SelectInstActivity;
import com.yun9.wservice.view.inst.SelectInstCommand;
import com.yun9.wservice.view.order.OrderManagerActivity;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;
import com.yun9.wservice.view.other.SettingActivity;

/**
 *
 */
public class UserFragment extends JupiterFragment {

    private UserHeadWidget userHeadWidget;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private ResourceFactory resourceFactory;

    @ViewInject(id = R.id.setting)
    private JupiterRowStyleTitleLayout settingLayout;

    @ViewInject(id = R.id.switch_inst)
    private JupiterRowStyleTitleLayout switchInstLayout;

    @ViewInject(id = R.id.about2)
    private JupiterRowStyleTitleLayout client;

    private final static Logger logger = Logger.getLogger(UserFragment.class);

    public static UserFragment newInstance(Bundle args) {
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    protected int getContentView() {

        return R.layout.fragment_user;
    }

    @Override
    protected void initViews(View view) {
        userHeadWidget = (UserHeadWidget) view.findViewById(R.id.userhead);

        userHeadWidget.getOrgLL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrgCompositeActivity.start(UserFragment.this.getActivity(), new OrgCompositeCommand().setEdit(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK).putSelectUser("186").putSelectOrgs("25970000000010006"));
            }
        });

        userHeadWidget.getDocLL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocCompositeActivity.start(UserFragment.this.getActivity(), new DocCompositeCommand().setEdit(true).setCompleteType(DocCompositeCommand.COMPLETE_TYPE_CALLBACK));
            }
        });

        userHeadWidget.getUserBuyer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderManagerActivity.start(UserFragment.this.getActivity());
            }
        });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.start(getActivity());
            }
        });

        switchInstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectInstActivity.start(getActivity(), new SelectInstCommand().setUser(sessionManager.getUser()));
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ClientActivity.class);
                startActivity(intent);
            }
        });

        userHeadWidget.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        },100);
    }


    private void refresh(){
        if (AssertValue.isNotNull(sessionManager.getInst()) && AssertValue.isNotNull(sessionManager.getUser())){
            Resource resource = resourceFactory.create("QueryUserInfoByIdService");
            resource.param("userid",sessionManager.getUser().getId());
            resource.param("instid",sessionManager.getInst().getId());

            final ProgressDialog registerDialog = ProgressDialog.show(getActivity(), null, getResources().getString(R.string.app_wating), true);


            resourceFactory.invok(resource, new AsyncHttpResponseCallback() {
                @Override
                public void onSuccess(Response response) {
                    User user = (User) response.getPayload();

                    if (AssertValue.isNotNull(user)){
                        userHeadWidget.getUserNameTV().setText(user.getName());
                        //TODO 等待服务完善返回机构信息后增加
                        userHeadWidget.getCompanyTV().setText(sessionManager.getInst().getName());
                        userHeadWidget.getOrgTV().setText(user.getOrgNames());
                        userHeadWidget.getSignTV().setText(user.getSignature());
                        //TODO 头像待服务完善
                    }
                }

                @Override
                public void onFailure(Response response) {
                    userHeadWidget.getUserNameTV().setText("");
                    userHeadWidget.getCompanyTV().setText("");
                    userHeadWidget.getOrgTV().setText("");
                    userHeadWidget.getSignTV().setText("");
                    Toast.makeText(mContext, response.getCause(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinally(Response response) {
                    registerDialog.dismiss();
                }
            });
        }
    }
}