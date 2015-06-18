package com.yun9.jupiter.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yun9.jupiter.R;

/**
 * Created by Leon on 15/6/16.
 */
public class JupiterSearchInputLayout extends JupiterRelativeLayout {

    private LinearLayout showLL;

    private LinearLayout editLL;

    private EditText searchET;


    public JupiterSearchInputLayout(Context context) {
        super(context);
    }

    public JupiterSearchInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JupiterSearchInputLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getContextView() {
        return R.layout.widget_search_input;
    }

    @Override
    protected void initViews(Context context, AttributeSet attrs, int defStyle) {
        showLL = (LinearLayout)findViewById(R.id.search_show_ll);
        editLL = (LinearLayout)findViewById(R.id.search_input_ll);
        searchET = (EditText)findViewById(R.id.search_et);

        showLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showLL.setVisibility(GONE);
                editLL.setVisibility(VISIBLE);

                searchET.setFocusableInTouchMode(true);
                searchET.requestFocus();
            }
        });


    }

    public LinearLayout getShowLL() {
        return showLL;
    }

    public LinearLayout getEditLL() {
        return editLL;
    }

    public EditText getSearchET() {
        return searchET;
    }
}
