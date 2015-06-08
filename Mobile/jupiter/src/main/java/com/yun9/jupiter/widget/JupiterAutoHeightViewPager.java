package com.yun9.jupiter.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.yun9.jupiter.util.PublicHelp;

/**
 * Created by huangbinglong on 15/5/26.
 * 自动改变高度的ViewPager；
 * 由于Viewpager嵌套在ScrollView中时，原生的Viewpager不能自适应高度，
 * 所以采用这个定制的ViewPager，重写onMeasure方法，重写计算高度
 */
public class JupiterAutoHeightViewPager extends ViewPager {

    // TODO 额外工具条的高度，如果ViewPager下面有其他元素，Viewpager内容会被挡到
    private int extraHeight = 70;// 下面toolbar的高度

    private int height;

    private int measureHeight;

    public JupiterAutoHeightViewPager(Context context) {
        super(context);
    }

    public JupiterAutoHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (height <= getExtraHeight()) {
            // 下面遍历所有child的高度
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec,
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                // 采用最大的view的高度
                if (h > height) {
                    height = h;
                }
            }
            height += getExtraHeight();
            measureHeight = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, measureHeight);
    }*/


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    private int getWindowsHeight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    public int getExtraHeight() {
        return PublicHelp.dip2px(getContext(), extraHeight);
    }

    public void setExtraHeight(int extraHeight) {
        this.extraHeight = extraHeight;
    }
}
