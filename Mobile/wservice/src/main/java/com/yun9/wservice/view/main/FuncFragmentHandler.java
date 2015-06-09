package com.yun9.wservice.view.main;

/**
 * Created by Leon on 15/6/9.
 */
public interface FuncFragmentHandler {
    public static final String FUNC_STORE = "store";

    public static final String FUNC_DYNAMIC = "dynamic";

    public static final String FUNC_MICROAPP = "microapp";

    public static final String FUNC_USER = "user";

    public boolean needLogin();

    public boolean support(String type);

    public void setRefresh(boolean refresh);

    public void switchFragment();
}
