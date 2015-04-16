package com.yun9.jupiter.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.yun9.jupiter.bean.injected.BeanInjectedUtil;
import com.yun9.jupiter.view.injected.ViewInjectedUtil;

/**
 * Created by Leon on 15/4/16.
 */
public class JupiterRelativeLayout extends RelativeLayout{

    private Context mContext;

    public JupiterRelativeLayout(Context context) {
        super(context);
        mContext = context;
        if (isInEditMode()) { return; }

    }

    public JupiterRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        if (isInEditMode()) { return; }
    }

    public JupiterRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        if (isInEditMode()) { return; }
    }

    public void inflate(int resource){
        LayoutInflater.from(mContext).inflate(resource, this);
        
        try {
            BeanInjectedUtil.initInjected(mContext, this);
            ViewInjectedUtil.initInjectedView(this, mContext, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
