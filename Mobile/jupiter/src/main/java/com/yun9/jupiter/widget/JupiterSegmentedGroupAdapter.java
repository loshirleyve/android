package com.yun9.jupiter.widget;

import android.view.View;

/**
 * Created by Leon on 15/5/4.
 */
public interface JupiterSegmentedGroupAdapter {
    public int getCount();

    public JupiterSegmentedItemModel getTabInfo(int position);

}
