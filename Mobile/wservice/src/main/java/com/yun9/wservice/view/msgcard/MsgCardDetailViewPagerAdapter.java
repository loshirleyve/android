package com.yun9.wservice.view.msgcard;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Leon on 15/5/4.
 */
public class MsgCardDetailViewPagerAdapter extends PagerAdapter {

    private List<View> mListViews;

    public MsgCardDetailViewPagerAdapter(List<View> viewList){
        this.mListViews = viewList;
    }

    @Override
    public int getCount() {
        if (mListViews !=null){
            return mListViews.size();
        }else{
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;//官方提示这样写
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(mListViews.get(position));//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }

}
