package com.yun9.wservice.view;

import android.os.Bundle;
import android.view.View;

import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.util.Logger;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.widget.TitleBar;


public class MainActivity extends JupiterActivity {

    private static final Logger logger = Logger.getLogger(MainActivity.class);

    @ViewInject(id=R.id.main_title)
    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initView();
    }

    private void initView(){
        logger.d("初始化MainActivity");

        titleBar.getTitleLeft().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MainActivity.this.showToast("点击了返回");
            }
        });

        titleBar.getTitleRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.showToast("点击了右边按钮");
            }
        });

        titleBar.getTitleCenter().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.showToast("点击了标题。");
            }
        });

        titleBar.setTitleText(R.string.app_store);
        titleBar.getTitleLeft().setVisibility(View.VISIBLE);
        titleBar.getTitleRight().setVisibility(View.VISIBLE);
        titleBar.setRightBtnText(R.string.app_cancel);

    }
}
