package com.yun9.wservice.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.yun9.jupiter.actvity.ActivityUtil;
import com.yun9.wservice.R;

/**
 * Created by Leon on 15/4/16.
 */
public class TitleRelativeLayout extends RelativeLayout {
    private Context mContext;

    public TitleRelativeLayout(Context context) {
        super(context);
        mContext = context;
        this.initView();
    }

    public TitleRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.title_bar, this);
        ActivityUtil.initInjectedView(this, mContext, this);
    }
}
