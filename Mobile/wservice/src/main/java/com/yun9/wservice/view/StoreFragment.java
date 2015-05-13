package com.yun9.wservice.view;

import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.wservice.R;

/**
 *
 */
public class StoreFragment extends JupiterFragment {


    public static StoreFragment newInstance(Bundle args) {
        StoreFragment fragment = new StoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_store;
    }

    @Override
    protected void initViews(View view) {

    }
}
