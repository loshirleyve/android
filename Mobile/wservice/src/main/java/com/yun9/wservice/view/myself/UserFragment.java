package com.yun9.wservice.view.myself;


import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterRowStyleTitleLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.other.SettingActivity;
import com.yun9.wservice.view.doc.DocCompositeActivity;
import com.yun9.wservice.view.doc.DocCompositeCommand;
import com.yun9.wservice.view.inst.SelectInstActivity;
import com.yun9.wservice.view.inst.SelectInstCommand;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;

/**
 *
 */
public class UserFragment extends JupiterFragment {

    private UserHeadWidget userHeadWidget;

    @BeanInject
    private SessionManager sessionManager;

    @ViewInject(id = R.id.setting)
    private JupiterRowStyleTitleLayout settingLayout;

    @ViewInject(id = R.id.switch_inst)
    private JupiterRowStyleTitleLayout switchInstLayout;

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
                OrgCompositeActivity.start(UserFragment.this.getActivity(), new OrgCompositeCommand().setEdit(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK).putSelectUser("186").putSelectUser("2").putSelectOrgs("25970000000010006").putSelectOrgs("5"));
            }
        });

        userHeadWidget.getDocLL().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocCompositeActivity.start(UserFragment.this.getActivity(), new DocCompositeCommand().setEdit(true).setCompleteType(DocCompositeCommand.COMPLETE_TYPE_CALLBACK));
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
                SelectInstActivity.start(getActivity(),new SelectInstCommand().setUser(sessionManager.getUser()));
            }
        });
    }


}