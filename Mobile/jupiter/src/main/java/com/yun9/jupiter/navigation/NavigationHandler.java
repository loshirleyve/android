package com.yun9.jupiter.navigation;

import android.content.Context;

/**
 * Created by Leon on 15/6/6.
 */
public interface NavigationHandler {

    public void navigation(Context context, NavigationBean navigationBean);

    public boolean support(NavigationBean navigationBean);

}
