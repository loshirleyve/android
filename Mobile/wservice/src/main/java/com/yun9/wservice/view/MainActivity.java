package com.yun9.wservice.view;

import android.os.Bundle;

import com.yun9.jupiter.view.JupiterActivity;
import com.yun9.jupiter.util.Logger;
import com.yun9.wservice.R;
import com.yun9.wservice.layout.TitleRelativeLayout;


public class MainActivity extends JupiterActivity {

    private static final Logger logger = Logger.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initView();
    }

    private void initView(){
        TitleRelativeLayout titleRelativeLayout = (TitleRelativeLayout) this.findViewById(R.id.main_title);

        logger.d("初始化MainActivity");
    }
}
