package com.yun9.jupiter.manager;

import com.yun9.jupiter.model.Inst;
import com.yun9.jupiter.model.User;

public interface SessionManager {

    public static final String FIRST_STATE = "first";

    public static final String LOGIN_STATE = "login";

    public static final String USER_INFO = "userinfo";

    public static final String INST_INFO = "instinfo";

    public void clean();

    public boolean isLogin();

    public User getUser();

    public Inst getInst();

    public Inst getInst(String userid);

    public void loginIn(User user);

    public void logout(User user);

    public void changeInst(Inst newInst);

    public void regOnLoginListener(OnLoginListener onLoginListener);

    public void regOnLogoutListener(OnLogoutListener onLogoutListener);

    public void regOnChangeInstListener(OnChangeInstListener onChangeInstListener);

    public interface OnLoginListener {
        public void login(User user);
    }

    public interface OnLogoutListener {
        public void logout(User user);
    }

    public interface OnChangeInstListener {
        public void changeInst(Inst inst);
    }
}
