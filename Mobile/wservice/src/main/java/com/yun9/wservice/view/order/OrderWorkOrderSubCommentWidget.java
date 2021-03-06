package com.yun9.wservice.view.order;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yun9.jupiter.util.DateFormatUtil;
import com.yun9.jupiter.util.StringPool;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;
import com.yun9.wservice.model.WorkOrderComment;

/**
 * Created by huangbinglong on 15/6/18.
 */
public class OrderWorkOrderSubCommentWidget extends JupiterRelativeLayout{

    private TextView subCommentTimeTV;

    private TextView subCommentTV;

    public OrderWorkOrderSubCommentWidget(Context context) {
        super(context);
    }

    public OrderWorkOrderSubCommentWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderWorkOrderSubCommentWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void buildWithData(WorkOrderComment.SubComment subComment) {
        subCommentTimeTV.setText(DateFormatUtil.format(subComment.getCreatedate(),
                StringPool.DATE_FORMAT_DATE));
        subCommentTV.setText(subComment.getCommenttext());
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_order_work_order_sub_comment;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        subCommentTimeTV = (TextView) this.findViewById(R.id.sub_comment_time_tv);
        subCommentTV = (TextView) this.findViewById(R.id.sub_comment_tv);
    }
}
