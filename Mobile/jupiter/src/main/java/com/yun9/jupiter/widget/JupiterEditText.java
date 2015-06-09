package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.yun9.jupiter.R;

/**
 * Created by huangbinglong on 15/6/6.
 */
public class JupiterEditText extends JupiterEditableView{

    private TextView titleTV;
    private MaterialEditText editText;

    private boolean edit;

    public JupiterEditText(Context context) {
        super(context);
    }

    public JupiterEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_edit_text;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        titleTV = (TextView) this.findViewById(R.id.title_tv);
        editText = (MaterialEditText) this.findViewById(R.id.m_edit_text);
    }

    @Override
    public void edit(boolean edit) {
        this.edit = edit;
        editText.setEnabled(edit);
        if (edit) {
            titleTV.setVisibility(VISIBLE);
        } else {
            titleTV.setVisibility(GONE);
        }
    }

    public TextView getTitleTV() {
        return titleTV;
    }

    public MaterialEditText getEditText() {
        return editText;
    }

}
