package com.yun9.jupiter.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.app.JupiterApplication;
import com.yun9.jupiter.bean.injected.BeanInjectedUtil;
import com.yun9.jupiter.view.injected.ViewInjectedUtil;

/**
 * Created by Leon on 15/4/20.
 */
public abstract class JupiterFragment extends Fragment {

    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(this.getContentView(), container, false);

        try {
            BeanInjectedUtil.initInjected( this.getActivity().getApplicationContext(), this);
            ViewInjectedUtil.initInjected(this,this.getActivity(),view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        this.initViews(view);
        return view;
    }

    protected abstract int getContentView();

    protected abstract void initViews(View view);
}
