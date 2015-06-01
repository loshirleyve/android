package com.yun9.wservice.view;


import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.R;
import com.yun9.wservice.view.doc.DocCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeActivity;
import com.yun9.wservice.view.org.OrgCompositeCommand;

/**
 *
 */
public class UserFragment extends JupiterFragment {

    private UserHeadWidget userHeadWidget;

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
                OrgCompositeActivity.start(UserFragment.this.getActivity(), new OrgCompositeCommand().setEdit(true).setCompleteType(OrgCompositeCommand.COMPLETE_TYPE_CALLBACK).putSelectUser("1").putSelectUser("2").putSelectOrgs("1").putSelectOrgs("5"));
            }
        });

        userHeadWidget.getDocLL().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DocCompositeActivity.start(UserFragment.this.getActivity());
            }
        });
    }



}