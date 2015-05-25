package com.yun9.jupiter.view;

import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * Created by huangbinglong on 15/5/25.
 */
public  abstract class JupiterPagerAdapter extends PagerAdapter{

    /**
     * 获取指定页的具体内容高度
     * 子类在需要时可以覆盖
     * 默认是ViewGroup.LayoutParams.WRAP_CONTENT
     * @param position 指定页下标
     * @return dip数值的高度
     */
    public int getDipHeight(int position) {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
