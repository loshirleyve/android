package com.yun9.jupiter.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.util.AssertValue;
import com.yun9.jupiter.util.Logger;
import com.yun9.jupiter.util.PublicHelp;
import com.yun9.jupiter.view.JupiterPagerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15/4/30.
 */
public class JupiterSegmentedGroup extends LinearLayout {

    private Context mContext;

    private List<JupiterSegmentedItem> items = new ArrayList<>();

    private JupiterSegmentedItem currItem;

    private OnItemClickListener onItemClickListener;

    public JupiterSegmentedGroup(Context context) {
        super(context);
        this.init(context, null, -1);
    }

    public JupiterSegmentedGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, -1);
    }

    public JupiterSegmentedGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int count = this.getChildCount();

        for (int i = 0; i < count; i++) {
            View view = this.getChildAt(i);
            if (view instanceof JupiterSegmentedItem) {
                items.add((JupiterSegmentedItem) view);
                ((JupiterSegmentedItem)view).setPostion(i);
                view.setOnClickListener(onClickListener);
            }
        }
    }

    public void addItem(JupiterSegmentedItem item) {
        if (AssertValue.isNotNull(item)) {
            items.add(item);
            this.addView(item);
        }
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof JupiterSegmentedItem && v != currItem) {
                selectItem((JupiterSegmentedItem) v);

                if (AssertValue.isNotNull(onItemClickListener)) {
                    onItemClickListener.onItemClick((JupiterSegmentedItem) v,((JupiterSegmentedItem) v).getPostion());
                }
            }
        }
    };


    public void selectItem(JupiterSegmentedItem view) {
        if (AssertValue.isNotNull(view)) {
            //将所有对象设置为未点击
            if (this.items != null) {
                for (JupiterSegmentedItem item : this.items) {
                    item.setClicked(false);
                }
            }
            this.currItem = view;
            //将当前项目设置为已经点击
            view.setClicked(true);
        }
    }

    public void selectItem(int postion) {
        if (postion < items.size()) {
            selectItem(items.get(postion));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(JupiterSegmentedItem view, int position);
    }

}
