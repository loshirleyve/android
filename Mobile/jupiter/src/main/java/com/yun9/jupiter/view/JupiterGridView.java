package com.yun9.jupiter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * 重写这个，主要是因为List里面嵌套GridView会导致GridView显示不全
 * 使用这个类代替原生的GridView可以规避这个问题
 * Created by huangbinglong on 15/5/21.
 */
public class JupiterGridView extends GridView{
    public JupiterGridView(Context context) {
        super(context);
    }

    public JupiterGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
