package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by huangbinglong on 15/6/3.
 */
public class JupiterTextIcoWithoutCorner extends JupiterTextIco{

    public JupiterTextIcoWithoutCorner(Context context) {
        super(context);
    }

    public JupiterTextIcoWithoutCorner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterTextIcoWithoutCorner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void edit(boolean edit) {
        // do nothing
    }
}
