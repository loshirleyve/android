package com.yun9.wservice.func.microapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.widget.JupiterTitleBarLayout;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.model.MicroAppBean;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class MicroAppFragment extends JupiterFragment {
    private static final Logger logger = Logger.getLogger(MicroAppFragment.class);

    private static final int PAGE_NUM = 9;
    private static final int ROW_NUM = 3;

    @ViewInject(id = R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(id = R.id.microapp_title)
    private JupiterTitleBarLayout titleBar;

    @ViewInject(id = R.id.gridview)
    private GridView gridView;

    @ViewInject(id = R.id.textView)
    private TextView textView;

    public static MicroAppFragment newInstance(Bundle args) {
        MicroAppFragment fragment = new MicroAppFragment();
        if(AssertValue.isNotNull(args)) {
            fragment.setArguments(args);//传递参数
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_micro_app;
    }

      @Override
    protected void initViews(View view) {
        List<MicroAppBean> microAppBeans= this.builderData();
        List<View> viewList= new ArrayList<View>();
         // List<GridView> gridViewList = new ArrayList<GridView>();
        // TODO 加载页面
        if (AssertValue.isNotNullAndNotEmpty(microAppBeans)){
            //microAppBeans.size() / PAGE_NUM;
            int pageSize = ((microAppBeans.size() % PAGE_NUM == 0) ? (microAppBeans.size() / PAGE_NUM) : (microAppBeans.size() / PAGE_NUM + 1));
            /*int pageSize = 3;*/
            for(int i=0;i<pageSize;i++){
                View tempView = this.builderView(i,microAppBeans);
                viewList.add(tempView);
            }
        }
        MicroAppAdapter microAppAdapter = new MicroAppAdapter(viewList);
        viewPager.setAdapter(microAppAdapter);
        logger.d("进入应用界面！aaa");
    }

     private View builderView(int pageNum,List<MicroAppBean> microAppBeans){

        int beginIndex = pageNum * PAGE_NUM;

        List<View> viewList=  new ArrayList<View>();

        //循环显示
        for(int i= beginIndex;i<beginIndex+PAGE_NUM && i < microAppBeans.size();i++){
            MicroAppBean microAppBean = microAppBeans.get(i);
            View view = this.builderItemView(microAppBean);
            viewList.add(view);
        }

        View view = LayoutInflater.from(this.getActivity()).inflate(R.layout.widget_micro_app_gridview,null);
        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new MicroAppGridViewAdapter(viewList));

        return view;
    }

    private View builderItemView(MicroAppBean microAppBean){
        View view = LayoutInflater.from(this.getActivity())
                .inflate(R.layout.widget_micro_app_gridview_item, null);
        TextView textView1 = (TextView) view.findViewById(R.id.textView);
        textView1.setText(microAppBean.getName());
        return view;
    }

    private List<MicroAppBean> builderData(){
        List<MicroAppBean> microAppBeanList = new ArrayList<MicroAppBean>();
        for (int i=0;i<30;i++){
            MicroAppBean microAppBean =  new MicroAppBean();
            microAppBean.setName("应用" + (i+1));
            microAppBeanList.add(microAppBean);
        }

        return microAppBeanList;
    }
}
