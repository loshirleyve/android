package com.yun9.wservice.view;


import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.R;
import com.yun9.wservice.func.login.LoginMainActivity;
import com.yun9.wservice.view.org.OrgCompositeActivity;

/**
 *
 */
public class UserFragment extends JupiterFragment  {

    private UserHeadWidget userHeadWidget;

    public static UserFragment newInstance( Bundle args ) {
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
                OrgCompositeActivity.start(UserFragment.this.getActivity(),null);
            }
        });
    }
}
