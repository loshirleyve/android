package com.yun9.wservice.func.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.jupiter.widget.TitleBar;

/**
 */
public class DynamicFragment extends JupiterFragment {

    private static final Logger logger = Logger.getLogger(DynamicFragment.class);

    //动态页面需要传递参数的Key
    private static final String ARG_PARAM1 = "param1";

    private String mParam1 ;

    @BeanInject
    private SessionManager sessionManager;

    @ViewInject(id=R.id.dynamic_title)
    private TitleBar titleBar;

    /**
     * 使用工厂方法创建一个新的动态实例，
     * 这个动态的使用必须使用此方法创建实例
     *
     */
    public static DynamicFragment newInstance(Bundle args) {
        DynamicFragment fragment = new DynamicFragment();
        if (AssertValue.isNotNull(args)) {
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递的参数
        if (AssertValue.isNotNull(this.getArguments())) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("点击了返回");
            }
        });

        titleBar.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("点击了右边按钮");
            }
        });

        titleBar.getTitleCenter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.d("点击了标题。");
            }
        });

        //titleBar.setTitleText(R.string.app_dynamic);
        titleBar.getTitleLeft().setVisibility(View.VISIBLE);
        titleBar.getTitleRight().setVisibility(View.VISIBLE);
        titleBar.setRightBtnText(R.string.app_cancel);

        return view;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_dynamic;
    }
}
