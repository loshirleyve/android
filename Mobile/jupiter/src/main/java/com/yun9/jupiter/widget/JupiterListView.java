package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by huangbinglong on 15/6/6.
 */
public class JupiterListView extends ListView{


    public JupiterListView(Context context) {
        super(context);
    }

    public JupiterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
