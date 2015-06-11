package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.util.PublicHelp;

/**
 * Created by huangbinglong on 15/6/6.
 */
public class JupiterEditText extends JupiterEditableView{

    private TextView titleTV;
    private EditText editText;

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
        editText = (EditText) this.findViewById(R.id.m_edit_text);
    }

    @Override
    public void edit(final boolean edit) {
        this.edit = edit;
        editText.setEnabled(edit);
        editText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JupiterEditText.this.edit) {
                    titleTV.setVisibility(VISIBLE);
                    editText.getLayoutParams().height = PublicHelp.dip2px(JupiterEditText.this.getContext(),25);
                } else {
                    titleTV.setVisibility(GONE);
                    editText.getLayoutParams().height = PublicHelp.dip2px(JupiterEditText.this.getContext(),50);
                    hideInputMethodManager();
                }
            }
        });
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && JupiterEditText.this.edit) {
                    titleTV.setVisibility(VISIBLE);
                    editText.getLayoutParams().height = PublicHelp.dip2px(JupiterEditText.this.getContext(),25);
                } else {
                    titleTV.setVisibility(GONE);
                    editText.getLayoutParams().height = PublicHelp.dip2px(JupiterEditText.this.getContext(),50);
                    hideInputMethodManager();
                }
            }
        });
        if (!edit) {
            hideInputMethodManager();
        }
    }

    public TextView getTitleTV() {
        return titleTV;
    }

    public EditText getEditText() {
        return editText;
    }

    /**
     * 隐藏键盘
     */
    private void hideInputMethodManager() {
        InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
