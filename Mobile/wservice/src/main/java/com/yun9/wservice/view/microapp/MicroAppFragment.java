package com.yun9.wservice.view.microapp;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yun9.jupiter.navigation.NavigationBean;
import com.yun9.jupiter.navigation.NavigationManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MicroAppBean;

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

    @BeanInject
    private NavigationManager navigationManager;

    private List<NavigationBean> microAppBeans;

    private MicroAppGridViewAdapter microAppGridViewAdapter;

    private List<NavigationBean> childMicroAppBeans;

    private MicroAppGridViewAdapter childMicroAppGridViewAdapter;

    private PopupWindow popupWindow;

    private View popView;


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
        iniPopMicroGroupWindow();
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

        microAppBeans.clear();
        microAppBeans.add(new NavigationBean(System.currentTimeMillis() + "", "locationdemo", "地理位置测试", NavigationBean.TYPE_ITEM));

        NavigationBean microAppBeanGroup1 = new NavigationBean(System.currentTimeMillis() + "","11", "申请", NavigationBean.TYPE_GROUP);

        microAppBeanGroup1.putChildren(new NavigationBean(System.currentTimeMillis() + "", "请假","1", NavigationBean.TYPE_ITEM));
        microAppBeanGroup1.putChildren(new NavigationBean(System.currentTimeMillis() + "", "报销","2", NavigationBean.TYPE_ITEM));
        microAppBeanGroup1.putChildren(new NavigationBean(System.currentTimeMillis() + "", "出差","3", NavigationBean.TYPE_ITEM));
        microAppBeanGroup1.putChildren(new NavigationBean(System.currentTimeMillis() + "", "加班","4" ,NavigationBean.TYPE_ITEM));

        microAppBeans.add(microAppBeanGroup1);

        for (int i = 0; i < 30; i++) {
            NavigationBean microAppBean = new NavigationBean(System.currentTimeMillis() + "",""+i, "应用" + (i + 1), NavigationBean.TYPE_ITEM);
            microAppBeans.add(microAppBean);
        }

        if (!AssertValue.isNotNull(microAppGridViewAdapter)) {
            microAppGridViewAdapter = new MicroAppGridViewAdapter(this.mContext, microAppBeans);
            microAppGridViewAdapter.setOnClickListener(onMicroAppClickListener);
            gridView.setAdapter(microAppGridViewAdapter);
        } else {
            microAppGridViewAdapter.notifyDataSetChanged();
        }
        mPtrFrame.refreshComplete();
    }

    private void iniPopMicroGroupWindow() {

        childMicroAppBeans = new ArrayList<>();


        childMicroAppGridViewAdapter = new MicroAppGridViewAdapter(mContext, childMicroAppBeans);
        childMicroAppGridViewAdapter.setOnClickListener(onMicroAppClickListener);

        popView = View.inflate(mContext, R.layout.widget_micro_app_popwindow_group, null);
        GridView gridView = (GridView) popView.findViewById(R.id.gridview);
        gridView.setAdapter(childMicroAppGridViewAdapter);
        popupWindow = new PopupWindow(popView,500,500);
    }

    private void showPopWin(NavigationBean microAppBean,View parent) {
        if (AssertValue.isNotNull(microAppBean) && AssertValue.isNotNullAndNotEmpty(microAppBean.getChildren())) {
            TextView textView = (TextView) popView.findViewById(R.id.title_tv);
            textView.setText(microAppBean.getName());
            childMicroAppBeans.clear();

            for(NavigationBean children : microAppBean.getChildren()){
                childMicroAppBeans.add(children);
            }

            childMicroAppGridViewAdapter.notifyDataSetChanged();

            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
            //获取popwindow焦点
            popupWindow.setFocusable(true);
            //设置popwindow如果点击外面区域，便关闭。
            popupWindow.setTouchable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

            //popupWindow.update();
        }
    }

    private View.OnClickListener onMicroAppClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NavigationBean microAppBean = (NavigationBean) view.getTag();

            if (MicroAppBean.TYPE_GROUP.equals(microAppBean.getType())) {
                showPopWin(microAppBean,view);
            } else if (MicroAppBean.TYPE_ITEM.equals(microAppBean.getType())) {
                navigationManager.navigation(mContext,null,microAppBean);
                popupWindow.dismiss();
            }
        }
    };
}
