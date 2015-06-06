package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by huangbinglong on 15/6/6.
 */
public abstract class JupiterEditableView extends JupiterRelativeLayout{

    public JupiterEditableView(Context context) {
        super(context);
    }

    public JupiterEditableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterEditableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public abstract void edit(boolean edit);
}
