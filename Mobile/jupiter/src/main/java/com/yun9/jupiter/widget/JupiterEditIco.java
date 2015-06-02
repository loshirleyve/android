package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by Leon on 15/6/2.
 */
public class JupiterEditIco extends JupiterRelativeLayout {

    private JupiterRowStyleSutitleLayout rowStyleSutitleLayout;

    public JupiterEditIco(Context context) {
        super(context);
    }

    public JupiterEditIco(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterEditIco(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return 0;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {

    }

    public void edit(boolean edit){

    }

    public void add(JupiterEditIcoBean jupiterEditIcoBean){

    }



    public void clean(){

    }

    public List<JupiterEditIcoBean> getItems(){
        return null;
    }
}
