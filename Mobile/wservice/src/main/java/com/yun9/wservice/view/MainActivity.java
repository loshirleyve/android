package com.yun9.wservice.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;

import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.func.dynamic.DynamicFragment;


public class MainActivity extends JupiterFragmentActivity {

    private static final Logger logger = Logger.getLogger(MainActivity.class);



    @ViewInject(id=R.id.button_bar_main_btn_store)
    private ImageButton storeBtn;

    @ViewInject(id=R.id.button_bar_main_btn_dynamic)
    private ImageButton dynamicBtn;

    @ViewInject(id=R.id.button_bar_main_btn_microapp)
    private ImageButton microappBtn;

    @ViewInject(id=R.id.button_bar_main_btn_user)
    private ImageButton userBtn;

    private View currentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initView();
    }

    private void initView(){
        logger.d("初始化MainActivity");

        this.storeBtn.setOnClickListener(storeOnClickListener);
        this.dynamicBtn.setOnClickListener(dynamicOnClickListener);
        this.microappBtn.setOnClickListener(microAppOnClickListener);
        this.userBtn.setOnClickListener(userOnClickListener);
    }

    private View.OnClickListener storeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("商店被点击！");
            setButton(v);
        }
    };

    private View.OnClickListener dynamicOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("动态按钮点击！");

            DynamicFragment dynamicFragment = DynamicFragment.newInstance(null);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_fl_content, dynamicFragment,
                    MainActivity.class.getName());
            ft.commit();
            setButton(v);
        }
    };

    private View.OnClickListener microAppOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("应用被点击！");
            setButton(v);
        }
    };

    private View.OnClickListener userOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("我被点击！");
            setButton(v);
        }
    };

    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);
        currentButton = v;
    }
}
