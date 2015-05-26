package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by huangbinglong on 15/5/26.
 * 解决ScrollView嵌套ViewPager，Viewpager不显示问题
 */
public class JupiterScrollView extends ScrollView{

    private GestureDetector mGestureDetector;

    public JupiterScrollView(Context context) {
        super(context);
        customInit(context);
    }

    public JupiterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        customInit(context);
    }

    public JupiterScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customInit(context);
    }

    private void customInit(Context context) {
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return (Math.abs(distanceY) > Math.abs(distanceX));
        }
    }

}
