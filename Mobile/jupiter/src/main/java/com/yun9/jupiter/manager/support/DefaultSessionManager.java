package com.yun9.jupiter.manager.support;

import com.yun9.jupiter.bean.Bean;
import com.yun9.jupiter.bean.BeanManager;
import com.yun9.jupiter.bean.Initialization;
import com.yun9.jupiter.cache.AppCache;
import com.yun9.jupiter.cache.UserDataCache;
import com.yun9.jupiter.conf.PropertiesManager;
import com.yun9.jupiter.manager.SessionManager;
import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.model.User;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.mobile.annotation.BeanInject;

import java.util.ArrayList;
import java.util.List;


public class DefaultSessionManager implements SessionManager, Bean,
        Initialization {

    private static final Logger logger = Logger
            .getLogger(DefaultSessionManager.class);

    private List<OnLoginListener> onLoginListenerList;

    private List<OnLogoutListener> onLogoutListenerList;

    private List<OnChangeInstListener> onChangeInstListenerList;

    @Override
    public Class<?> getType() {
        return SessionManager.class;
    }

    @Override
    public void clean() {

    }

    @Override
    public void regOnLoginListener(OnLoginListener onLoginListener) {
        if (!AssertValue.isNotNull(onLoginListenerList)) {
            this.onLoginListenerList = new ArrayList<>();
        }
        this.onLoginListenerList.add(onLoginListener);
    }

    @Override
    public void regOnLogoutListener(OnLogoutListener onLogoutListener) {
        if (!AssertValue.isNotNull(onLogoutListenerList)) {
            this.onLogoutListenerList = new ArrayList<>();
        }
        this.onLogoutListenerList.add(onLogoutListener);
    }

    @Override
    public void regOnChangeInstListener(OnChangeInstListener onChangeInstListener) {
        if (!AssertValue.isNotNull(onChangeInstListenerList)) {
            this.onChangeInstListenerList = new ArrayList<>();
        }
        this.onChangeInstListenerList.add(onChangeInstListener);
    }

    @Override
    public boolean isLogin() {
        boolean loginState = AppCache.getInstance().getAsBoolean(SessionManager.LOGIN_STATE);
        return loginState;
    }

    @Override
    public User getUser() {
        User user = AppCache.getInstance().get(SessionManager.USER_INFO, User.class);
        if (!AssertValue.isNotNull(user)) {
            user = new User();
        }
        return user;
    }

    @Override
    public Inst getInst() {
        return this.getInst(null);
    }

    public Inst getInst(String userid) {
        UserDataCache userDataCache = UserDataCache.getInstance(userid);

        if (AssertValue.isNotNull(userDataCache)) {
            Inst inst = userDataCache.get(SessionManager.INST_INFO, Inst.class);
            return inst;
        } else {
            return new Inst();
        }
    }

    private void setLogin(boolean login) {
        AppCache.getInstance().put(SessionManager.LOGIN_STATE, login);
    }

    public void setUser(User user) {
        AppCache.getInstance().put(SessionManager.USER_INFO, user);
    }

    private void setInst(Inst inst) {
        UserDataCache.getInstance().put(SessionManager.INST_INFO, inst);
    }

    @Override
    public void loginIn(User user) {

        if (AssertValue.isNotNull(user) && AssertValue.isNotNullAndNotEmpty(user.getId())) {
            //记录登录状态
            this.setLogin(true);
            //记录用户信息
            this.setUser(user);

            //执行后续动作
            if (AssertValue.isNotNullAndNotEmpty(this.onLoginListenerList)) {
                for (OnLoginListener onLoginListener : this.onLoginListenerList) {
                    onLoginListener.login(user);
                }
            }
        }
    }

    @Override
    public void logout(User user) {
        //解除用户登录状态
        this.setLogin(false);
        //清理本地用户信息
        this.setUser(new User());

        //执行用户注销动作服务等相关后续动作
        if (AssertValue.isNotNullAndNotEmpty(this.onLogoutListenerList)) {
            for (OnLogoutListener onLogoutListener : this.onLogoutListenerList) {
                onLogoutListener.logout(user);
            }
        }
    }


    @Override
    public void init(BeanManager beanManager) {
    }


    @Override
    public void changeInst(Inst newInst) {

        if (AssertValue.isNotNull(newInst)) {
            Inst oldInst = this.getInst();

            //新旧机构相同无需切换
            if (AssertValue.isNotNull(oldInst) && oldInst.getId().equals(newInst.getId())) {
                return;
            }

            //执行机构的后续动作
            if (AssertValue.isNotNullAndNotEmpty(this.onChangeInstListenerList)) {
                for (OnChangeInstListener onChangeInstListener : this.onChangeInstListenerList) {
                    onChangeInstListener.changeInst(newInst);
                }
            }
            //记录新机构信息
            this.setInst(newInst);

        }

    }

}
