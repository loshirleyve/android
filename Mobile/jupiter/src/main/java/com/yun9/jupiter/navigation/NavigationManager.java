package com.yun9.jupiter.navigation;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Leon on 15/6/6.
 */
public interface NavigationManager {
    public void navigation(Activity activity,Bundle bundle,NavigationBean navigationBean);
    public void regHandler(NavigationHandler navigationHandler);
}
