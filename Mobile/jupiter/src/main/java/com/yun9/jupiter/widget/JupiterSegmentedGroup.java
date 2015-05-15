package com.yun9.jupiter.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/4/30.
 */
public class JupiterSegmentedGroup extends JupiterRelativeLayout {

    // tab容器
    private LinearLayout tabContainer;
    // ViewPager
    private ViewPager viewPager;

    private JupiterSegmentedGroupAdapter adapter;

    private JupiterSegmentedItem currItem;

    private List<JupiterSegmentedItem> items;

    private OnClickListener onClickListener;

    // 内置的基本tab点击事件监听器
    private OnClickListener basicOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            JupiterSegmentedGroup.this.onItemClick(v);
        }
    };

    private static final Logger logger = Logger.getLogger(JupiterSegmentedGroup.class);

    public JupiterSegmentedGroup(Context context) {
        super(context);
    }

    public JupiterSegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getContextView() {
        return R.layout.segmented_group;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        items = new ArrayList<>();
        this.tabContainer = (LinearLayout) this.findViewById(R.id.segmented_tab);
        this.viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        bindViewPagerListener();
    }

    private void bindViewPagerListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                JupiterSegmentedGroup.this.selectItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setAdapter(JupiterSegmentedGroupAdapter adapter){
        this.adapter = adapter;
        this.builderView();
    }

    public  void setTabItemAdapter(PagerAdapter pagerAdapter) {
        if (viewPager != null) {
            viewPager.setAdapter(pagerAdapter);
        }
    }

    private void builderView(){
        if (adapter == null)
            return;

        for(int i = 0;i<adapter.getCount();i++){
            JupiterSegmentedItem item = adapter.getTab(i);
            item.setOnClickListener(this.basicOnClickListener);
            tabContainer.addView(item);
            items.add(item);
        }
    }

    private JupiterSegmentedItem createItem() {

        View itemWrapperView = LayoutInflater.from(this.getContext()).inflate(R.layout.segmented_item_wrapper,null);
        JupiterSegmentedItem item = (JupiterSegmentedItem) itemWrapperView.findViewById(R.id.segmented_item);

        return item;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setOnTabClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public void selectItem(int position){
        this.updateState(position);
    }

    public void onItemClick(View view){
        if (view != null && view instanceof JupiterSegmentedItem){
            JupiterSegmentedItem tempItemView = (JupiterSegmentedItem) view;
            logger.d("分段选择Item被点击");

            //更新界面状态
            this.updateState(tempItemView);
            //执行事件监听
            if (this.onClickListener !=null){
                this.onClickListener.onClick(view);
            }

        }
    }

    private void updateState(JupiterSegmentedItem view){
        //将所有对象设置为未点击
        if(this.items !=null){
            for(JupiterSegmentedItem item :this.items){
                item.setClicked(false);
            }
        }

        this.currItem = view;
        //将当前项目设置为已经点击
        view.setClicked(true);
    }

    private void updateState(int viewPosition){
        if (viewPosition > this.items.size() - 1 || viewPosition < 0){
            return;
        }

        for(JupiterSegmentedItem item :this.items){
            item.setClicked(false);
        }

        JupiterSegmentedItem tempView = this.items.get(viewPosition);


        this.currItem = tempView;
        //将当前项目设置为已经点击
        tempView.setClicked(true);
    }

}
