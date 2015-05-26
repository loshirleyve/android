package com.yun9.wservice.func.user;


import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.R;

/**
 *
 */
public class UserFragment extends JupiterFragment  {



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

    }
}
