package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by huangbinglong on 15/6/3.
 */
public class JupiterTextIcoWithCorner extends JupiterTextIco{

    public JupiterTextIcoWithCorner(Context context) {
        super(context);
    }

    public JupiterTextIcoWithCorner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterTextIcoWithCorner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void edit(boolean edit) {
        this.showCorner();
    }
}
