package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun9.wservice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbinglong on 15/5/18.
 */
public class MsgCardDetailToolbarPanelsAdapter extends PagerAdapter {

    private List<View> mListViews;

    public MsgCardDetailToolbarPanelsAdapter(Context ctx) {
        mListViews = new ArrayList<>();
        View pageWrapper = LayoutInflater.from(ctx)
                                                        .inflate(R.layout.widget_msg_card_detail_toolbar_panels_page_wrapper, null);
        MsgCardDetailToolbarPanelsPageWidget page = (MsgCardDetailToolbarPanelsPageWidget) pageWrapper.findViewById(R.id.panelspage);
        mListViews.add(page);
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//官方提示这样写
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListViews.get(position));//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }
}
