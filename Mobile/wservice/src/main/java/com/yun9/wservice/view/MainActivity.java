package com.yun9.wservice.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;

import com.yun9.jupiter.repository.RepositoryManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragment;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.func.microapp.MicroAppFragment;


public class MainActivity extends JupiterFragmentActivity  {

    private static final Logger logger = Logger.getLogger(MainActivity.class);



    @ViewInject(id=R.id.button_bar_main_btn_store)
    private ImageButton storeBtn;

    @ViewInject(id=R.id.button_bar_main_btn_dynamic)
    private ImageButton dynamicBtn;

    @ViewInject(id=R.id.button_bar_main_btn_microapp)
    private ImageButton microappBtn;

    @ViewInject(id=R.id.button_bar_main_btn_user)
    private ImageButton userBtn;

    @BeanInject
    private RepositoryManager repositoryManager;

    private View currentButton;

    public static void start(Context context,Bundle bundle){
        Intent intent = new Intent(context,MainActivity.class);
        if (AssertValue.isNotNull(bundle)){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initView();
        storeBtn.performClick();
        DemoFormActivity.start(this,null);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
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

            StoreFragment storeFragment = StoreFragment.newInstance(null);
            pushFragment(storeFragment);
            setButton(v);
        }
    };

    private View.OnClickListener dynamicOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("动态按钮点击！");

            Bundle bundle = new Bundle();
            DynamicSessionFragment dynamicSessionFragment = DynamicSessionFragment.newInstance(bundle);
            pushFragment(dynamicSessionFragment);
            setButton(v);
        }
    };

    private View.OnClickListener microAppOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("应用被点击！");
            Bundle bundle = new Bundle();
            com.yun9.wservice.func.microapp.MicroAppFragment microAppFragment = MicroAppFragment.newInstance(bundle);
            //pushFragment(MicroAppFragment.newInstance(null));
            pushFragment(microAppFragment);
            setButton(v);
        }
    };

    private View.OnClickListener userOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logger.d("我被点击！");
            pushFragment(UserFragment.newInstance(null));
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

    private void pushFragment(JupiterFragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_fl_content, fragment,
                MainActivity.class.getName());
        ft.commit();
    }
}
