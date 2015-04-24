package com.yun9.wservice.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.R;

/**
 */
public class MicroAppFragment extends JupiterFragment {


    public static MicroAppFragment newInstance(Bundle args) {
        MicroAppFragment fragment = new MicroAppFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_micro_app;
    }


}
