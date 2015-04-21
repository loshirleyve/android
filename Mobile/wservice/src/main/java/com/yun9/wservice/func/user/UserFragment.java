package com.yun9.wservice.func.user;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.R;

/**
 *
 */
public class UserFragment extends JupiterFragment {



    public static UserFragment newInstance( Bundle args ) {
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getContentView() {

        return R.layout.fragment_user;
    }
}
