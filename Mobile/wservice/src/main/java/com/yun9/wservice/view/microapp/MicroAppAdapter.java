package com.yun9.wservice.view.microapp;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.jupiter.util.AssertValue;

import java.util.List;

/**
 * Created by xia on 2015/5/22.
 */
public class MicroAppAdapter extends PagerAdapter {
    private List<View> mListViews;

    public MicroAppAdapter(List<View> listViews){
        this.mListViews = listViews;
    }

    @Override
    public int getCount() {
        if (AssertValue.isNotNull(mListViews)){
            return this.mListViews.size();
        }else{
            return  0;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        ((ViewPager)container).removeView(mListViews.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(mListViews.get(position));
        return mListViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
