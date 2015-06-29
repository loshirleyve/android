package com.yun9.wservice.view.msgcard.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.CirclePageIndicator;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

import java.util.List;

/**
 * Created by huangbinglong on 15/5/16.
 */
public class MsgCardDetailToolbarPanelWidget extends JupiterRelativeLayout{
    private List<MsgCardDetailToolbarPanelPageWidget> mPages;

    private ViewPager viewPager;

    private CirclePageIndicator pageIndicator;

    public MsgCardDetailToolbarPanelWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarPanelWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarPanelWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_panels;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        pageIndicator = (CirclePageIndicator) this.findViewById(R.id.pageindicator);

    }

    public void builder(List<MsgCardDetailToolbarPanelPageWidget> pages){
        this.mPages = pages;
        viewPager.setAdapter(toolbarPanelsAdapter);
        pageIndicator.setViewPager(viewPager);
    }

    private PagerAdapter toolbarPanelsAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mPages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方提示这样写
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPages.get(position));//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(mPages.get(position), 0);//添加页卡
            return mPages.get(position);
        }
    };
}
