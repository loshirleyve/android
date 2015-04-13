package com.yun9.jupiter.context;

import android.content.Context;

/**
 * Created by Leon on 15/4/13.
 */

public interface AppContextFactory {
    public AppContext create(Context context);
}
