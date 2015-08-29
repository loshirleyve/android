package com.yun9.wservice.view.analysis;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun9.jupiter.widget.JupiterListView;
import com.yun9.jupiter.widget.JupiterRelativeLayout;
import com.yun9.wservice.R;

/**
 * Created by huangbinglong on 15/8/28.
 */
public class SaleAnalysisWidget extends JupiterRelativeLayout {

    private ImageView userHeadIv;
    private TextView userNameTv;
    private TextView sortNoTv;
    private TextView numsTv;
    private TextView amountTv;
    private JupiterListView classifyListView;

    public SaleAnalysisWidget(Context context) {
        super(context);
    }

    public SaleAnalysisWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SaleAnalysisWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_sale_analysis;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        userHeadIv = (ImageView) this.findViewById(R.id.user_head_iv);
        userNameTv = (TextView) this.findViewById(R.id.user_name_tv);
        sortNoTv = (TextView) this.findViewById(R.id.sortno_tv);
        numsTv = (TextView) this.findViewById(R.id.nums_tv);
        amountTv = (TextView) this.findViewById(R.id.amount_tv);
        classifyListView = (JupiterListView) this.findViewById(R.id.classify_lv);
    }

    public ImageView getUserHeadIv() {
        return userHeadIv;
    }

    public TextView getUserNameTv() {
        return userNameTv;
    }

    public TextView getSortNoTv() {
        return sortNoTv;
    }

    public TextView getNumsTv() {
        return numsTv;
    }

    public TextView getAmountTv() {
        return amountTv;
    }

    public JupiterListView getClassifyListView() {
        return classifyListView;
    }
}
