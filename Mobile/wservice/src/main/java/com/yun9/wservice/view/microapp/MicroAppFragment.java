package com.yun9.wservice.view.microapp;


import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MicroAppBean;
import com.yun9.wservice.view.demo.LocationActivity;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 */
public class MicroAppFragment extends JupiterFragment {
    private static final Logger logger = Logger.getLogger(MicroAppFragment.class);

    @ViewInject(id = R.id.microapp_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.gridview)
    private GridView gridView;

    @ViewInject(id = R.id.rotate_header_list_view_frame)
    private PtrClassicFrameLayout mPtrFrame;

    private List<MicroAppBean> microAppBeans;

    private MicroAppGridViewAdapter microAppGridViewAdapter;


    public static MicroAppFragment newInstance(Bundle args) {
        MicroAppFragment fragment = new MicroAppFragment();
        if (AssertValue.isNotNull(args)) {
            fragment.setArguments(args);//传递参数
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_micro_app;
    }

    @Override
    protected void initViews(View view) {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    private void refresh() {
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                completeRefresh();
            }
        }, 1000);
    }

    private void completeRefresh() {
        if (!AssertValue.isNotNull(microAppBeans)) {
            microAppBeans = new ArrayList<>();
        }

        microAppBeans.removeAll(microAppBeans);

        for (int i = 0; i < 30; i++) {
            MicroAppBean microAppBean = new MicroAppBean();
            microAppBean.setName("应用" + (i + 1));
            microAppBeans.add(microAppBean);
        }

        if (!AssertValue.isNotNull(microAppGridViewAdapter)) {
            microAppGridViewAdapter = new MicroAppGridViewAdapter(this.mContext, microAppBeans);
            microAppGridViewAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocationActivity.start(MicroAppFragment.this.getActivity(), null);
                }
            });

            gridView.setAdapter(microAppGridViewAdapter);
        }else{
            microAppGridViewAdapter.notifyDataSetChanged();
        }
        mPtrFrame.refreshComplete();
    }
}
