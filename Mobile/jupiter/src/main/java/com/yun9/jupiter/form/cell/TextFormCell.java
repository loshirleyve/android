package com.yun9.jupiter.form.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun9.jupiter.R;
import com.yun9.jupiter.form.FormCell;
import com.yun9.jupiter.util.AssertValue;

/**
 * Created by Leon on 15/5/25.
 */
public class TextFormCell extends FormCell {

    private String defaultValue;

    private TextView labelTV;

    private EditText inputTextET;

    @Override
    public void init() {

    }

    @Override
    public View getCellView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.form_cell_textinput,null);
        this.labelTV = (TextView) view.findViewById(R.id.label);
        this.inputTextET = (EditText) view.findViewById(R.id.textInput);

        //检查是否存在默认值
        if (AssertValue.isNotNullAndNotEmpty(defaultValue)){
            inputTextET.setText(defaultValue);
        }

        //设置label
        labelTV.setText(this.getLabel());

        //默认为非编辑状态
        this.edit(false);

        return view;
    }

    @Override
    public void edit(boolean edit) {
        inputTextET.setEnabled(edit);
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
