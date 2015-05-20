package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.view.msgcard.model.MsgCardDetailToolbarActionItem;

import java.util.List;

/**
 * Created by huangbinglong on 15/5/19.
 */
public class MsgCardDetailToolbarPanelsPageWidget extends JupiterRelativeLayout{

    /**
     * 每行最多个数
     * 该数值不能随便改，程序并不会根据这个值计算每个item的宽高
     * 每项的宽高在styles.xml中的msg_card_action_btn_ico决定了
     * 修改此值将使布局出错
     */
    public static final int MAX_NUMS_EACH_LINE = 4;

    /**
     * 最大行数
     * 该数值不能随便改，程序并不会根据这个值计算每个item的宽高
     * 每项的宽高在styles.xml中的msg_card_action_btn_ico决定了
     * 修改此值将使布局出错
     */
    public static final int MAX_LINE_NUMS = 2;

    // 界面引用对象,动作容器GridView
    GridView actionGridView;

    public MsgCardDetailToolbarPanelsPageWidget(Context context) {
        super(context);
    }

    public MsgCardDetailToolbarPanelsPageWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MsgCardDetailToolbarPanelsPageWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 刚创建的对象里面不包含任何item的，需要外部通过这个方法
     * 传递items实体列表进来，对象通过实体数据穿件界面item
     * 每个item的模板为：widget_msg_card_detail_toolbar_panels_page_item.xml
     * @param items 实体数据列表
     */
    public void buildView(final List<MsgCardDetailToolbarActionItem> items) {
        // 设置动作监听
        actionGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items.get(position).getOnClickListener() != null) {
                    items.get(position).getOnClickListener().onClick(view);
                }
            }
        });
        // 设置GridViewAdapter
        actionGridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                MsgCardDetailToolbarPanelsPageItemWidget itemWidget = new MsgCardDetailToolbarPanelsPageItemWidget(getContext());
                itemWidget.buildWithData(items.get(position));
                return itemWidget;
            }
        });
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_panels_page;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        actionGridView = (GridView) this.findViewById(R.id.grid);
    }

}
