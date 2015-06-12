package com.yun9.wservice.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.push.PushFactory;
import com.yun9.jupiter.repository.RepositoryManager;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.view.JupiterFragmentActivity;
import com.yun9.mobile.annotation.BeanInject;
import com.yun9.mobile.annotation.ViewInject;
import com.yun9.wservice.R;
import com.yun9.wservice.view.inst.SelectInstCommand;
import com.yun9.wservice.view.login.LoginCommand;
import com.yun9.wservice.view.login.LoginMainActivity;
import com.yun9.wservice.view.main.support.DynamicFuncFragmentHandler;
import com.yun9.wservice.view.main.support.MicroAppFuncFragmentHandler;
import com.yun9.wservice.view.main.support.StoreFuncFragmentHandler;
import com.yun9.wservice.view.main.support.UserFuncFragmentHandler;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends JupiterFragmentActivity {

    private static final Logger logger = Logger.getLogger(MainActivity.class);


    @ViewInject(id = R.id.button_bar_main_btn_store)
    private ImageButton storeBtn;

    @ViewInject(id = R.id.button_bar_main_btn_dynamic)
    private ImageButton dynamicBtn;

    @ViewInject(id = R.id.button_bar_main_btn_microapp)
    private ImageButton microappBtn;

    @ViewInject(id = R.id.button_bar_main_btn_user)
    private ImageButton userBtn;

    @BeanInject
    private RepositoryManager repositoryManager;

    @BeanInject
    private SessionManager sessionManager;

    @BeanInject
    private PushFactory pushFactory;

    private View currentButton;

    private View preButton;

    private String currType;

    private List<FuncFragmentHandler> funcFragmentHandlerList;

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MainActivity.class);

        if (AssertValue.isNotNull(bundle)) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initView();
        storeBtn.performClick();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    private void initView() {
        funcFragmentHandlerList = new ArrayList<>();
        funcFragmentHandlerList.add(new StoreFuncFragmentHandler(this.getSupportFragmentManager()));
        funcFragmentHandlerList.add(new DynamicFuncFragmentHandler(this.getSupportFragmentManager()));
        funcFragmentHandlerList.add(new MicroAppFuncFragmentHandler(this.getSupportFragmentManager()));
        funcFragmentHandlerList.add(new UserFuncFragmentHandler(this.getSupportFragmentManager()));

        this.storeBtn.setTag(FuncFragmentHandler.FUNC_STORE);
        this.dynamicBtn.setTag(FuncFragmentHandler.FUNC_DYNAMIC);
        this.microappBtn.setTag(FuncFragmentHandler.FUNC_MICROAPP);
        this.userBtn.setTag(FuncFragmentHandler.FUNC_USER);

        this.storeBtn.setOnClickListener(onClickListener);
        this.dynamicBtn.setOnClickListener(onClickListener);
        this.microappBtn.setOnClickListener(onClickListener);
        this.userBtn.setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!sessionManager.isLogin()) {
            switchFragment(FuncFragmentHandler.FUNC_STORE, storeBtn);
        }
    }

    private FuncFragmentHandler findHandler(String type) {
        FuncFragmentHandler funcFragmentHandler = null;
        if (AssertValue.isNotNullAndNotEmpty(this.funcFragmentHandlerList)) {
            for (FuncFragmentHandler handler : this.funcFragmentHandlerList) {
                if (handler.support(type)) {
                    funcFragmentHandler = handler;
                    break;
                }
            }
        }
        return funcFragmentHandler;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            currType = v.getTag().toString();
            preButton = v;
            switchFragment(currType, v);
        }
    };

    private void switchFragment(String type, View v) {
        FuncFragmentHandler funcFragmentHandler = findHandler(type);

        if (AssertValue.isNotNull(funcFragmentHandler)) {
           /* if (funcFragmentHandler.needLogin() && !sessionManager.isLogin()) {
                //还没有登陆系统，需要先登陆
                LoginMainActivity.start(MainActivity.this, new LoginCommand());
            } else {
                funcFragmentHandler.switchFragment();
                setButton(v);
            }*/

            funcFragmentHandler.switchFragment();
            setButton(v);
        }
    }

    private void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);
        currentButton = v;
    }

    private void setRefresh(boolean refresh){
        if (AssertValue.isNotNullAndNotEmpty(this.funcFragmentHandlerList)){
            for(FuncFragmentHandler funcFragmentHandler:this.funcFragmentHandlerList){
                funcFragmentHandler.setRefresh(refresh);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LoginCommand.REQUEST_CODE && resultCode == LoginCommand.RESULT_CODE_OK) {
            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();

            //设置为必须刷新
            setRefresh(true);

            //切换到之前准备打开的页面
            this.switchFragment(currType, preButton);
        }

        if (requestCode == SelectInstCommand.REQUEST_CODE && resultCode == SelectInstCommand.RESULT_CODE_OK) {
            Inst inst = (Inst) data.getSerializableExtra(SelectInstCommand.PARAM_INST);

            if (AssertValue.isNotNull(inst)) {
                sessionManager.changeInst(inst);
            }
            //设置为必须刷新
            setRefresh(true);

            //切换到商店页面
            this.switchFragment(FuncFragmentHandler.FUNC_STORE, storeBtn);
        }

    }
}
