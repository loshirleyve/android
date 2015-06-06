package com.yun9.jupiter.navigation;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by Leon on 15/6/6.
 */
public interface NavigationManager {
    public void navigation(Context context,Bundle bundle,NavigationBean navigationBean);
    public void regHandler(NavigationHandler navigationHandler);
}
