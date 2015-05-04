package com.yun9.jupiter.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.yun9.jupiter.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/4/30.
 */
public class JupiterSegmentedGroup extends LinearLayout {

    private JupiterSegmentedItem currItem;

    private List<JupiterSegmentedItem> items;

    private OnClickListener onClickListener;

    private static final Logger logger = Logger.getLogger(JupiterSegmentedGroup.class);

    public JupiterSegmentedGroup(Context context) {
        super(context);
    }

    public JupiterSegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        logger.d("onFinishInflate!");

        items = new ArrayList<>();

        //完成后记录所有子对象
        int childCount = this.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof JupiterSegmentedItem){
                JupiterSegmentedItem item = (JupiterSegmentedItem) child;
                this.items.add((JupiterSegmentedItem) item);
                item.setClicked(false);
            }
        }

        if (items.size()>0){
            items.get(0).setClicked(true);
        }
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
