package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.yun9.jupiter.R;
import com.yun9.jupiter.bean.injected.BeanInjectedUtil;
import com.yun9.jupiter.view.injected.ViewInjectedUtil;

/**
 * Created by Leon on 15/4/16.
 */
public abstract class JupiterRelativeLayout extends RelativeLayout{

    private Context mContext;

    public JupiterRelativeLayout(Context context) {
        super(context);
        mContext = context;
        this.inflate();
        if (isInEditMode()) { return; }
        this.beanInitInjected();
        this.initViews(context,null,-1);

    }

    public JupiterRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.inflate();
        if (isInEditMode()) { return; }
        this.beanInitInjected();
        this.initViews(context,attrs, -1);
    }

    public JupiterRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        this.inflate();
        if (isInEditMode()) { return; }
        this.beanInitInjected();
        this.initViews(context,attrs,defStyle);
    }



    private void beanInitInjected(){
        try {
            BeanInjectedUtil.initInjected(mContext, this);
            ViewInjectedUtil.initInjected(this, mContext, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void inflate(){
        LayoutInflater.from(mContext).inflate(this.getContextView(), this);
    }

    protected abstract int getContextView();

    protected abstract void initViews(Context context, AttributeSet attrs, int defStyle);
}

