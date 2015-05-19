package com.yun9.wservice.view.msgcard;

import android.content.Context;
import android.util.AttributeSet;

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

    private MsgCardDetailToolbarWidget toolbarWidget;

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
    public void buildView(List<MsgCardDetailToolbarActionItem> items) {

    }

    @Override
    protected int getContextView() {
        return R.layout.widget_msg_card_detail_toolbar_panels_page;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }

    /**
     * 改方法用户设置联通上下文的MsgCardDetailToolbarWidget对象，即工具条对象本身；
     * 用户每个item点击后对工具条的回调操作
     * 改方法必须被调用，并传入正确的对象
     * @param toolbarWidget 工具条对象
     */
    public void setToolbarWidget(MsgCardDetailToolbarWidget toolbarWidget) {
        this.toolbarWidget = toolbarWidget;
    }
}
